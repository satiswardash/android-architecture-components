package com.incture.mobility.architecturecomponents.dagger;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.Utils.Constants;
import com.incture.mobility.architecturecomponents.room.NotesDao;
import com.incture.mobility.architecturecomponents.room.NotesDatabase;
import com.incture.mobility.architecturecomponents.room.NotesRepository;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by satiswardash on 11/02/18.
 */
@Module
public class ApplicationModule {

    private final ArchitectureComponents application;
    private final NotesDatabase database;

    public ApplicationModule(ArchitectureComponents application) {
        database = Room
                .databaseBuilder(application, NotesDatabase.class, Constants.DATABASE_NAME)
                .build();
        this.application = application;
    }

    @Provides
    ArchitectureComponents provideArchitectureComponentsApplication() {
        return application;
    }

    @Provides
    NotesDao provideNotesDao(NotesDatabase database) {
        return database.getNotesDao();
    }

    @Provides
    NotesDatabase provideNotesDatabase() {
        return database;
    }

    @Provides
    NotesRepository provideNotesRepository(NotesDao notesDao) {
        return new NotesRepository(notesDao);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(NotesRepository repository) {
        return new ViewModelFactory(repository);
    }
}
