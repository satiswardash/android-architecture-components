package com.incture.mobility.architecturecomponents.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.dagger.ApplicationComponent;
import com.incture.mobility.architecturecomponents.utils.BaseActivity;
import com.incture.mobility.architecturecomponents.fragments.NoteListActivityFragment;
import com.incture.mobility.architecturecomponents.viewmodels.NoteListViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satiswardash on 11/02/18.
 */
public class NoteListActivity extends BaseActivity {

    @Inject
    ViewModelFactory factory;

    private NoteListViewModel mNoteListViewModel;

    @BindView(R.id.anl_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.create_new_note_fab)
    FloatingActionButton mCreateNoteFab;
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inject the current activity inside Dagger 2 dependency graph
        ApplicationComponent applicationComponent = ((ArchitectureComponents)getApplication()).getApplicationComponent();
        applicationComponent.inject(this);

        mNoteListViewModel = ViewModelProviders.of(this, factory).get(NoteListViewModel.class);

        init();
    }

    /**
     *Initialize activity layout
     */
    private void init() {
        setContentView(R.layout.activity_note_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mCreateNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        NoteListActivityFragment fragment =
                (NoteListActivityFragment) fragmentManager.findFragmentByTag(NoteListViewModel.class.toString());

        if (fragment == null) {
            fragment = new NoteListActivityFragment();
        }

        addFragmentToActivity(fragmentManager, fragment, R.id.note_list_root, fragment.getClass().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_note, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_logout: {
               doLogout();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void doLogout() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNoteListViewModel.logout();
            }
        }, 500);
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        finishAffinity();
    }
}
