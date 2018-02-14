package com.incture.mobility.architecturecomponents.db;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.incture.mobility.architecturecomponents.utils.Constants;
import com.incture.mobility.architecturecomponents.utils.NetworkUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by satiswardash on 11/02/18.
 */

/**
 *  //1. Check for network availability
    //2. If network is available then fetchLiveData data from network
    //3. Once data is fetched from the network store the data to local persistence storage
    //4. Refresh/Reload the persistence data and bind with adapter
    //5. Show the progress bar in the mean time while data is getting fetched from the network
    //6. If network is not available then fetchLiveData the data from local storage and bind with adapter
 */
public class NotesRepository {

    private static final String TAG = NotesRepository.class.toString();
    private NotesDao mNotesDao;
    private Context mContext;
    private FirebaseFirestore mNetwork;

    private boolean SYNC_FLAG = true;

    /**
     * {@link NotesDao} object dependency injection to Constructor
     *
     * @param mNotesDao
     */
    @Inject
    public NotesRepository(Context context, NotesDao mNotesDao, FirebaseFirestore network) {
        this.mNotesDao = mNotesDao;
        this.mContext = context;
        this.mNetwork = network;
    }

    /**
     * Fetch all notes
     *
     * @return
     */
    public LiveData<List<Notes>> getListOfNotes() {
        if (NetworkUtility.hasNetworkAccess(mContext)) {
            sync();
        }
        return mNotesDao.fetchLiveData();
    }

    /**
     * Create new note
     *
     * @param notes
     */
    public void createNewNotes(final Notes notes) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.save(notes);
                if (NetworkUtility.hasNetworkAccess(mContext) && SYNC_FLAG) {
                    sync();
                    SYNC_FLAG = true;
                }
            }
        });
        thread.start();

    }

    /**
     * Update existing note
     *
     * @param notes
     */
    public void updateNotes(final Notes notes) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.update(notes);
                if (NetworkUtility.hasNetworkAccess(mContext) && SYNC_FLAG)
                    sync();
            }
        });
        thread.start();
    }

    /**
     * Delete existing note
     *
     * @param notes
     */
    public void deleteNotes(final Notes notes) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.delete(notes);
                if (NetworkUtility.hasNetworkAccess(mContext) && SYNC_FLAG) {
                    mNetwork.collection(Constants.NOTES_COLLECTION).document(notes.getId()).delete();
                }
            }
        });
        thread.start();
    }

    /**
     * Refresh the notes list from network
     */
    public void refresh() {
        mNetwork
                .collection(Constants.NOTES_COLLECTION)
                .orderBy(Constants.NOTE_IMAGE_URL)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> snapshots = task.getResult().getDocuments();
                            for (DocumentSnapshot documentSnapshot : snapshots) {
                                Notes notes = new Notes(
                                        documentSnapshot.getId(),
                                        documentSnapshot.getString(Constants.NOTE_TITLE),
                                        documentSnapshot.getString(Constants.NOTE_DESCRIPTION),
                                        documentSnapshot.getDate(Constants.NOTE_TIMESTAMP),
                                        documentSnapshot.getString(Constants.NOTE_IMAGE_URL));
                                SYNC_FLAG = false;
                                createNewNotes(notes);
                            }
                        }
                    }
                });
    }

    /**
     * Sync the current local persistence data with the network
     */
    public void sync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Notes> offlineData = mNotesDao.fetch();
                for (Notes notes :
                        offlineData) {
                    Map<String, Object> note = new HashMap<>();
                    note.put(Constants.NOTE_TITLE, notes.getTitle());
                    note.put(Constants.NOTE_DESCRIPTION, notes.getDescription());
                    note.put(Constants.NOTE_TIMESTAMP, notes.getTimestamp());
                    note.put(Constants.NOTE_IMAGE_URL, notes.getImageUri());

                    mNetwork.collection(Constants.NOTES_COLLECTION).document(notes.getId()).set(note, SetOptions.merge());
                }
                refresh();
            }
        });
        thread.start();
    }
}
