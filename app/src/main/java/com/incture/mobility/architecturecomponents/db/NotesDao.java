package com.incture.mobility.architecturecomponents.db;

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

    @Query("SELECT * FROM NOTES ORDER BY timestamp desc")
    LiveData<List<Notes>> fetchLiveData();

    @Query("SELECT * FROM NOTES ORDER BY timestamp desc")
    List<Notes> fetch();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Notes notes);

    @Delete
    void delete(Notes notes);

    @Update
    void update(Notes notes);
}
