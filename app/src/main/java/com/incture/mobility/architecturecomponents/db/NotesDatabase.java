package com.incture.mobility.architecturecomponents.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.incture.mobility.architecturecomponents.utils.Constants;

/**
 * Created by satiswardash on 11/02/18.
 */
@Database(entities = {Notes.class}, version = Constants.DATABASE_VERSION)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao getNotesDao();
}
