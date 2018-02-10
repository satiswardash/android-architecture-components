package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.incture.mobility.architecturecomponents.room.NotesRepository;

/**
 * Created by satiswardash on 10/02/18.
 */

public class NoteListViewModel extends ViewModel {

    private NotesRepository mNotesRepository;

    public NoteListViewModel(NotesRepository mNotesRepository) {
        this.mNotesRepository = mNotesRepository;
    }


}
