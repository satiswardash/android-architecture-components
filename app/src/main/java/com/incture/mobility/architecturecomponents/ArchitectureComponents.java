package com.incture.mobility.architecturecomponents;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.incture.mobility.architecturecomponents.room.NotesDao;
import com.incture.mobility.architecturecomponents.room.NotesDatabase;
import com.incture.mobility.architecturecomponents.room.NotesRepository;

/**
 * Created by satiswardash on 10/02/18.
 */

public class ArchitectureComponents extends Application {

    private static NotesRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        NotesDao dao = Room.databaseBuilder(this, NotesDatabase.class, "Notes_Database").build().getNotesDao();
        repository = new NotesRepository(dao);
    }

    public static NotesRepository getRepository() {
        return repository;
    }
}
