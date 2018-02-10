package com.incture.mobility.architecturecomponents.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by satiswardash on 11/02/18.
 */
@Dao
public interface NotesDao {

    @Query("SELECT * FROM NOTES")
    LiveData<List<Notes>> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createNewNote(Notes notes);

    @Delete
    void deleteExistingNote(Notes notes);

    @Update
    void updateExistingNote(Notes notes);
}
