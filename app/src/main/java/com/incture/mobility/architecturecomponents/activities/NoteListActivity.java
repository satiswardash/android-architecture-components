package com.incture.mobility.architecturecomponents.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.Utils.BaseActivity;
import com.incture.mobility.architecturecomponents.fragments.NoteListActivityFragment;
import com.incture.mobility.architecturecomponents.viewmodels.NoteListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satiswardash on 11/02/18.
 */
public class NoteListActivity extends BaseActivity {

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
}
