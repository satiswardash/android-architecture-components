package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.room.NotesRepository;

/**
 * Created by satiswardash on 10/02/18.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private NotesRepository mNotesRepository;

    public ViewModelFactory() {
        mNotesRepository = ArchitectureComponents.getRepository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateNotesViewModel.class))
            return (T) new CreateNotesViewModel(mNotesRepository);

        else if (modelClass.isAssignableFrom(NoteListViewModel.class))
            return (T) new NoteListViewModel(mNotesRepository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
