package com.incture.mobility.architecturecomponents.services;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.room.NotesDao;
import com.incture.mobility.architecturecomponents.room.NotesDatabase;
import com.incture.mobility.architecturecomponents.room.NotesRepository;
import com.incture.mobility.architecturecomponents.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private boolean SYNC_FLAG = false;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (SYNC_FLAG) {
            final ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isAvailable() && wifi.isConnected()) {

                Intent newIntent = new Intent(context, DataSyncService.class);
                mContext.startService(newIntent);
            }
        }
        else
            SYNC_FLAG = true;
    }
}
