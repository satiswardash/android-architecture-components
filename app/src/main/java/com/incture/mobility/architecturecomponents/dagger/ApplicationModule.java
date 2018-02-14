package com.incture.mobility.architecturecomponents.dagger;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.utils.Constants;
import com.incture.mobility.architecturecomponents.db.NotesDao;
import com.incture.mobility.architecturecomponents.db.NotesDatabase;
import com.incture.mobility.architecturecomponents.db.NotesRepository;
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
        this.application = application;

        database = Room
                .databaseBuilder(application, NotesDatabase.class, Constants.DATABASE_NAME)
                .build();
    }

    @Provides
    FirebaseFirestore provideFirebaseFirestore(Context context) {

        FirebaseApp.initializeApp(context);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        return firestore;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    NotesDatabase provideNotesDatabase() {
        return database;
    }

    @Provides
    NotesDao provideNotesDao(NotesDatabase database) {
        return database.getNotesDao();
    }

    @Provides
    NotesRepository provideNotesRepository(NotesDao notesDao, Context context, FirebaseFirestore network) {
        return new NotesRepository(context, notesDao, network);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(Context context, NotesRepository repository) {
        return new ViewModelFactory(context, repository);
    }
}
