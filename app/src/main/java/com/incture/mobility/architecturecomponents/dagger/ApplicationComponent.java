package com.incture.mobility.architecturecomponents.dagger;

import android.app.Application;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.Utils.NetworkUtility;
import com.incture.mobility.architecturecomponents.activities.CreateNoteActivity;
import com.incture.mobility.architecturecomponents.fragments.NoteListActivityFragment;

import dagger.Component;

/**
 * Created by satiswardash on 11/02/18.
 */
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(NoteListActivityFragment fragment);
    void inject(CreateNoteActivity activity);
}
