package com.incture.mobility.architecturecomponents.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.incture.mobility.architecturecomponents.utils.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.init(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (Preferences.getAccessToken().equalsIgnoreCase(Preferences.EMPTY_STRING_DEFAULT_VALUE)) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    finishAffinity();
                }
                else {
                    Intent homeIntent = new Intent(getApplicationContext(), NoteListActivity.class);
                    startActivity(homeIntent);
                    finishAffinity();
                }
            }
        });
        thread.start();
    }
}
