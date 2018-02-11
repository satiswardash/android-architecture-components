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



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateNotesViewModel.class))
            return (T) new CreateNotesViewModel(ArchitectureComponents.getRepository());

        else if (modelClass.isAssignableFrom(NoteDetailsViewModel.class))
            return (T) new NoteDetailsViewModel(ArchitectureComponents.getRepository());

        else if (modelClass.isAssignableFrom(NoteListViewModel.class))
            return (T) new NoteListViewModel(ArchitectureComponents.getRepository());

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
