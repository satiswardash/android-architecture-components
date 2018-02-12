package com.incture.mobility.architecturecomponents.activities;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.BuildConfig;
import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.Utils.BaseActivity;
import com.incture.mobility.architecturecomponents.Utils.Constants;
import com.incture.mobility.architecturecomponents.Utils.FileUtility;
import com.incture.mobility.architecturecomponents.dagger.ApplicationComponent;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.viewmodels.CreateNotesViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by satiswardash on 11/02/18.
 */
public class CreateNoteActivity extends BaseActivity {

    @Inject
    public ViewModelFactory mViewModelFactory;
    private CreateNotesViewModel mCreateNotesViewModel;

    private static final String TAG = CreateNoteActivity.class.toString();
    private static final int CAMERA_INTENT_REQUEST = 1;
    public final int CAMERA_PERMISSION_REQUEST = 11;
    public int mCameraPermission;
    public int mExtRStoragePermission;
    public int mExtWStoragePermission;
    private Uri mCurrentPhotoPath = Uri.EMPTY;

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Button mSaveButton;
    private Button mOptionButton;
    private Toolbar mToolbar;
    private ImageView mImageView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inject the current activity inside Dagger 2 dependency graph
        ApplicationComponent applicationComponent = ((ArchitectureComponents) getApplication()).getApplicationComponent();
        applicationComponent.inject(this);

        init();
        mCreateNotesViewModel =
                ViewModelProviders
                        .of(this, mViewModelFactory)
                        .get(CreateNotesViewModel.class);

        Bundle bundle = getIntent().getBundleExtra(Constants.BUNDLE_KEY);
        if (bundle != null && bundle.containsKey(Constants.NOTES_KEY)) {
            initUpdate((Notes) bundle.getParcelable(Constants.NOTES_KEY));
        } else {

            mOptionButton.setText(R.string.attach);
            mOptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        askPermission(CAMERA_PERMISSION_REQUEST);
                    } catch (IOException e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                }
            });

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
                                        new Date(),
                                        mCurrentPhotoPath.toString()));
                        onBackPressed();
                    }
                }
            });
        }

    }

    /**
     * Checking the permissions(Camera and File IO - R/W) on activity starts
     */
    @Override
    protected void onStart() {
        super.onStart();
        mCameraPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        mExtRStoragePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        mExtWStoragePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * Initialize activity layout for view, update and delete selected note
     *
     * @param notes
     */
    private void initUpdate(final Notes notes) {
        mTitleEditText.setText(notes.getTitle());
        mDescriptionEditText.setText(notes.getDescription());
        mToolbar.setTitle(R.string.title_update_note);
        mSaveButton.setText(R.string.update);
        mOptionButton.setText(R.string.delete);

        if (!notes.getImageUri().isEmpty()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Uri uri = Uri.parse(notes.getImageUri());
                    mImageView.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext()).load(uri).into(mImageView);
                }
            }, 100);
        }

        mOptionButton.setOnClickListener(new View.OnClickListener() {
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
        mOptionButton = findViewById(R.id.delete_note_button);
        mImageView = findViewById(R.id.note_imageView);
        mImageView.setVisibility(View.INVISIBLE);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_INTENT_REQUEST: {
                    mImageView.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext()).load(mCurrentPhotoPath).into(mImageView);
                    break;
                }
            }
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST: {
                handleCameraEvent();
                break;
            }
        }
    }

    /**
     * Camera event handler on pressing Attach button to attach image
     */
    public void handleCameraEvent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = new FileUtility().createExternalImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "onCameraOptionSelected: ", ex.getCause());
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mCurrentPhotoPath = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoPath);
                startActivityForResult(takePictureIntent, CAMERA_INTENT_REQUEST);
            }
        }
    }

    /**
     * Ask user's approval for Dangerous permissions
     */
    private void askPermission(int requestCode) throws IOException {
        if (mCameraPermission == PackageManager.PERMISSION_GRANTED
                && mExtWStoragePermission == PackageManager.PERMISSION_GRANTED
                && mExtRStoragePermission == PackageManager.PERMISSION_GRANTED) {


            if (requestCode == CAMERA_PERMISSION_REQUEST) {
                handleCameraEvent();
            }
        } else {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    requestCode);
        }
    }
}
