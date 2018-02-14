package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.incture.mobility.architecturecomponents.db.Notes;
import com.incture.mobility.architecturecomponents.db.NotesRepository;
import com.incture.mobility.architecturecomponents.utils.Preferences;

import java.util.List;

/**
 * Created by satiswardash on 10/02/18.
 */

public class NoteListViewModel extends ViewModel {

    private NotesRepository mNotesRepository;
    private Context mContext;

    public NoteListViewModel(Context context, NotesRepository mNotesRepository) {
        this.mNotesRepository = mNotesRepository;
        this.mContext = context;
    }

    public LiveData<List<Notes>> getAllNotes(){
        return mNotesRepository.getListOfNotes();
    }

    public void logout() {
        Preferences.setAccessToken(null);
        Toast.makeText(mContext, "Logout successful.", Toast.LENGTH_SHORT).show();
    }
}
