package com.incture.mobility.architecturecomponents.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by satiswardash on 10/02/18.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     * @param tag
     */
    public static void addFragmentToActivity (FragmentManager fragmentManager,
                                              Fragment fragment,
                                              int frameId,
                                              String tag) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }
}
