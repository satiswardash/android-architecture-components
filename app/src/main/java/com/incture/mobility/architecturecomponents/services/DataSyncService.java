package com.incture.mobility.architecturecomponents.services;

import android.app.IntentService;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;
import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.dagger.ApplicationComponent;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.room.NotesDao;
import com.incture.mobility.architecturecomponents.room.NotesDatabase;
import com.incture.mobility.architecturecomponents.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.HasServiceInjector;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class DataSyncService extends Service {

    private NotesDao mDao;

    FirebaseFirestore mFirestore;

    public DataSyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDao = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, Constants.DATABASE_NAME)
                .build()
                .getNotesDao();
        sync();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * Sync the current local persistence data with the network
     */
    public void sync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Notes> offlineData = mDao.fetch();
                for (Notes notes :
                        offlineData) {
                    Map<String, Object> note = new HashMap<>();
                    note.put(Constants.NOTE_TITLE, notes.getTitle());
                    note.put(Constants.NOTE_DESCRIPTION, notes.getDescription());
                    note.put(Constants.NOTE_TIMESTAMP, notes.getTimestamp());
                    note.put(Constants.NOTE_IMAGE_URL, notes.getImageUri());

                    FirebaseApp.initializeApp(getApplicationContext());
                    mFirestore = FirebaseFirestore.getInstance();
                    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            .setPersistenceEnabled(true)
                            .build();
                    mFirestore.setFirestoreSettings(settings);

                    mFirestore.collection(Constants.NOTES_COLLECTION).document(notes.getId()).set(note, SetOptions.merge());
                }
            }
        });
        thread.start();
    }
}
