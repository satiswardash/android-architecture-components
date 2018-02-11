package com.incture.mobility.architecturecomponents.room;

import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by satiswardash on 11/02/18.
 */

public class NotesRepository {

    private NotesDao mNotesDao;

    /**
     * {@link NotesDao} object dependency injection to Constructor
     * @param mNotesDao
     */
    public NotesRepository(NotesDao mNotesDao) {
        this.mNotesDao = mNotesDao;
    }

    /**
     * Fetch all notes
     * @return
     */
    public LiveData<List<Notes>> getListOfNotes() {
        return mNotesDao.getAllNotes();
    }

    /**
     *Create new note
     * @param notes
     */
    public void createNewNotes(final Notes notes) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.createNewNote(notes);
            }
        });
        thread.start();
    }

    /**
     *Update existing note
     * @param notes
     */
    public void updateNotes(final Notes notes) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.updateExistingNote(notes);
            }
        });
        thread.start();
    }

    /**
     *Delete existing note
     * @param notes
     */
    public void deleteNotes(final Notes notes) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mNotesDao.deleteExistingNote(notes);
            }
        });
        thread.start();
    }
}
