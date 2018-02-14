package com.incture.mobility.architecturecomponents;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.incture.mobility.architecturecomponents.dagger.ApplicationComponent;
import com.incture.mobility.architecturecomponents.dagger.ApplicationModule;
import com.incture.mobility.architecturecomponents.dagger.DaggerApplicationComponent;
import com.incture.mobility.architecturecomponents.services.ConnectivityChangedReceiver;

/**
 * Created by satiswardash on 10/02/18.
 */

public class ArchitectureComponents extends Application {

    //private static NotesRepository repository;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //NotesDao dao = Room.databaseBuilder(this, NotesDatabase.class, Constants.DATABASE_NAME).build().getNotesDao();
        //repository = new NotesRepository(dao);
        applicationComponent =
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(this))
                        .build();

        registerReceiver(new ConnectivityChangedReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /*public static NotesRepository getRepository() {
        return repository;
    }*/
}
