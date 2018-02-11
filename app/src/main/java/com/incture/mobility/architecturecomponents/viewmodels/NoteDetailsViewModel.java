package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.incture.mobility.architecturecomponents.room.NotesRepository;

/**
 * Created by satiswardash on 10/02/18.
 */

public class NoteDetailsViewModel extends ViewModel {

    private NotesRepository mRepository;

    public NoteDetailsViewModel(NotesRepository mRepository) {
        this.mRepository = mRepository;
    }
}
