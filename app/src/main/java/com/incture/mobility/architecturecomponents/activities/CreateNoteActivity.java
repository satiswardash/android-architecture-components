package com.incture.mobility.architecturecomponents.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.Utils.BaseActivity;
import com.incture.mobility.architecturecomponents.Utils.Constants;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.viewmodels.CreateNotesViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import java.util.Date;
import java.util.UUID;

public class CreateNoteActivity extends BaseActivity {

    private CreateNotesViewModel mCreateNotesViewModel;

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Button mSaveButton;
    private Button mDeleteButton;
    private Toolbar mToolbar;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mCreateNotesViewModel =
                ViewModelProviders
                        .of(this, new ViewModelFactory())
                        .get(CreateNotesViewModel.class);

        Bundle bundle = getIntent().getBundleExtra(Constants.BUNDLE_KEY);
        if (bundle != null && bundle.containsKey(Constants.NOTES_KEY)) {
            initUpdate((Notes) bundle.getParcelable(Constants.NOTES_KEY));
        } else {
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCreateNotesViewModel != null) {
                        if (mTitleEditText.getText().toString().isEmpty()) {
                            Toast.makeText(CreateNoteActivity.this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mCreateNotesViewModel.createNewNote(
                                new Notes(UUID.randomUUID().toString(),
                                        mTitleEditText.getText().toString(),
                                        mDescriptionEditText.getText().toString(),
                                        new Date()));
                        onBackPressed();
                    }
                }
            });
        }

    }

    /**
     * Initialize activity layout for view, update and delete selected note
     * @param notes
     */
    private void initUpdate(final Notes notes) {
        mTitleEditText.setText(notes.getTitle());
        mDescriptionEditText.setText(notes.getDescription());
        mSaveButton.setText(R.string.update);
        mDeleteButton = findViewById(R.id.delete_note_button);
        mToolbar.setTitle(R.string.title_update_note);
        mDeleteButton.setVisibility(View.VISIBLE);


        mDeleteButton.setOnClickListener(new View.OnClickListener() {
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
                    notes.setTitle(mTitleEditText.getText().toString());
                    notes.setDescription(mDescriptionEditText.getText().toString());
                    notes.setTimestamp(new Date());
                    mCreateNotesViewModel.updateNote(notes);
                    onBackPressed();
                }
            }
        });
    }

    /**
     * Initialize activity layout for create new notes
     */
    private void init() {
        setContentView(R.layout.activity_create_note);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTitleEditText = findViewById(R.id.note_title_editText);
        mDescriptionEditText = findViewById(R.id.note_description_edit_text);
        mSaveButton = findViewById(R.id.save_note_button);
    }

}
