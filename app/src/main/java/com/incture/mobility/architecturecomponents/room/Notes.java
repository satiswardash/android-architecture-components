package com.incture.mobility.architecturecomponents.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.incture.mobility.architecturecomponents.Utils.DateConverter;

import java.util.Date;

/**
 * Created by satiswardash on 10/02/18.
 */

@Entity(indices = {@Index("ts"), @Index(value = "timestamp")})
public class Notes {

    @PrimaryKey
    private String id;
    private String title;
    private String description;
    @TypeConverters({DateConverter.class})
    private Date timestamp;

    public Notes(String id, String title, String description, Date timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
