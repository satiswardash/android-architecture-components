package com.incture.mobility.architecturecomponents.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.adapters.DisplayNotesRecyclerAdapter;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.viewmodels.NoteListViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteListActivityFragment extends Fragment {

    NoteListViewModel mNoteListViewModel;
    RecyclerView recyclerView;
    DisplayNotesRecyclerAdapter adapter;

    boolean flag = false;

    public NoteListActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mNoteListViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(NoteListViewModel.class);
        adapter = new DisplayNotesRecyclerAdapter(getContext(), new ArrayList<Notes>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.note_list_recycler_view);
        bindData();

        mNoteListViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                adapter.setmLiveData(notes);
                //bindData();
                adapter.notifyItemInserted(adapter.getItemCount() -1);
                if (flag) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
                        }
                    }, 400);
                } else {
                    flag = true;
                }
            }
        });
    }

    private void bindData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        getActivity(),
                        R.drawable.divider
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );

        recyclerView.setAdapter(adapter);
    }
}
