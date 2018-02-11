package com.incture.mobility.architecturecomponents.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;

import java.util.List;

/**
 * Created by satiswardash on 11/02/18.
 */

public class NotesRepository {

    private NotesDao mNotesDao;

    public NotesRepository(NotesDao mNotesDao) {
        this.mNotesDao = mNotesDao;
    }

    public LiveData<List<Notes>> getListOfNotes() {
        return mNotesDao.getAllNotes();
    }

    public void createNewNotes(final Notes notes) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.createNewNote(notes);
            }
        });
        thread.start();

    }

    public void updateNotes(Notes notes) {
        mNotesDao.updateExistingNote(notes);
    }

    public void deleteNotes(Notes notes) {
        mNotesDao.deleteExistingNote(notes);
    }
}
