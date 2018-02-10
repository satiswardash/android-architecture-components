package com.incture.mobility.architecturecomponents.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incture.mobility.architecturecomponents.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteDetailsActivityFragment extends Fragment {

    public NoteDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }
}
