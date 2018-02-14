package com.incture.mobility.architecturecomponents.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

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
