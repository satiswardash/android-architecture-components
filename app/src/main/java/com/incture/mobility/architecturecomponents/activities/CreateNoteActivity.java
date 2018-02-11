package com.incture.mobility.architecturecomponents.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.Utils.BaseActivity;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.viewmodels.CreateNotesViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import java.util.Date;
import java.util.UUID;

public class CreateNoteActivity extends BaseActivity {

    private CreateNotesViewModel mCreateNotesViewModel;

    Button mSaveButton;
    EditText mTitle;
    EditText mDescription;
    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCreateNotesViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(CreateNotesViewModel.class);

        mTitle = findViewById(R.id.note_title_editText);
        mDescription = findViewById(R.id.note_description_edit_text);
        mSaveButton = findViewById(R.id.save_note_button);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null && bundle.containsKey("note")) {
            final Notes notes = (Notes) bundle.get("note");
            mTitle.setText(notes.getTitle());
            mDescription.setText(notes.getDescription());
            mSaveButton.setText("Update");
            mDelete = findViewById(R.id.delete_note_button);
            toolbar.setTitle("Update note");
            mDelete.setVisibility(View.VISIBLE);


            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCreateNotesViewModel.deleteNote(notes);
                    onBackPressed();
                }
            });

            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCreateNotesViewModel != null) {
                        notes.setTitle(mTitle.getText().toString());
                        notes.setDescription(mDescription.getText().toString());
                        notes.setTimestamp(new Date());
                        mCreateNotesViewModel.updateNote(notes);
                        onBackPressed();
                    }
                }
            });
        }
        else {
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCreateNotesViewModel != null) {
                        mCreateNotesViewModel.createNewNote(new Notes(UUID.randomUUID().toString(), mTitle.getText().toString(), mDescription.getText().toString(), new Date()));
                        onBackPressed();
                    }
                }
            });
        }

    }

}
