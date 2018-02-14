package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.room.NotesRepository;

/**
 * Created by satiswardash on 11/02/18.
 */

public class CreateNotesViewModel extends ViewModel {

    private NotesRepository mNotesRepository;

    public CreateNotesViewModel(NotesRepository mNotesRepository) {
        this.mNotesRepository = mNotesRepository;
    }

    public void createNewNote(Notes notes) {
        mNotesRepository.createNewNotes(notes);
    }

    public void updateNote(Notes notes) {
        mNotesRepository.updateNotes(notes);
    }

    public void deleteNote(Notes notes) {
        mNotesRepository.deleteNotes(notes);
    }
}
