package com.incture.mobility.architecturecomponents.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by satiswardash on 11/02/18.
 */

public class DateConverter{

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
