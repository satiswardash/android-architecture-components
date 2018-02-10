package com.incture.mobility.architecturecomponents.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.Utils.BaseActivity;
import com.incture.mobility.architecturecomponents.fragments.NoteDetailsActivityFragment;
import com.incture.mobility.architecturecomponents.fragments.NoteListActivityFragment;
import com.incture.mobility.architecturecomponents.viewmodels.NoteListViewModel;

public class NoteDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     *
     */
    private void init() {

        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.and_toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        NoteDetailsActivityFragment fragment =
                (NoteDetailsActivityFragment) fragmentManager.findFragmentByTag(NoteDetailsActivityFragment.class.toString());

        if (fragment == null) {
            fragment = new NoteDetailsActivityFragment();
        }

        addFragmentToActivity(fragmentManager, fragment, R.id.note_details_root, fragment.getClass().toString());
    }
}
