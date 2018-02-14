package com.incture.mobility.architecturecomponents.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.incture.mobility.architecturecomponents.db.NotesRepository;

import javax.inject.Inject;

/**
 * Created by satiswardash on 10/02/18.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private NotesRepository mNotesRepository;
    private Context mContext;

    @Inject
    public ViewModelFactory(Context context, NotesRepository repository) {
        mNotesRepository = repository;
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateNotesViewModel.class))
            return (T) new CreateNotesViewModel(mNotesRepository);

        else if (modelClass.isAssignableFrom(NoteListViewModel.class))
            return (T) new NoteListViewModel(mContext, mNotesRepository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
