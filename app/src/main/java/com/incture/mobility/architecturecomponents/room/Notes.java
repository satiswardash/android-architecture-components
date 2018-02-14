package com.incture.mobility.architecturecomponents.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.incture.mobility.architecturecomponents.utils.DateConverter;

import java.util.Date;

/**
 * Created by satiswardash on 10/02/18.
 */

@Entity
public class Notes implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    @TypeConverters({DateConverter.class})
    private Date timestamp;
    private String imageUri;

    public Notes(@NonNull String id, String title, String description, Date timestamp, String imageUri) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.imageUri = imageUri;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.timestamp != null ? this.timestamp.getTime() : -1);
        dest.writeString(this.imageUri);
    }

    protected Notes(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        long tmpTimestamp = in.readLong();
        this.timestamp = tmpTimestamp == -1 ? null : new Date(tmpTimestamp);
        this.imageUri = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes(source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
}
