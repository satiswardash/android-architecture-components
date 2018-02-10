package com.incture.mobility.architecturecomponents.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by satiswardash on 11/02/18.
 */
@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao getNotesDao();
}
