package com.incture.mobility.architecturecomponents.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by satiswardash on 11/02/18.
 */

public class ApplicationUtility {

    public static ApplicationUtility getInstance() {
        return new ApplicationUtility();
    }

    /**
     * Get random RGB color codes
     * @return
     */
    public int getColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
