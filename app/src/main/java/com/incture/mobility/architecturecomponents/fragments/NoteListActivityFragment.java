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

import com.incture.mobility.architecturecomponents.ArchitectureComponents;
import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.adapters.DisplayNotesRecyclerAdapter;
import com.incture.mobility.architecturecomponents.dagger.ApplicationComponent;
import com.incture.mobility.architecturecomponents.room.Notes;
import com.incture.mobility.architecturecomponents.viewmodels.NoteListViewModel;
import com.incture.mobility.architecturecomponents.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by satiswardash on 11/02/18.
 */
public class NoteListActivityFragment extends Fragment {

    private boolean SCROLL_FLAG = false;

    private NoteListViewModel mNoteListViewModel;
    @Inject
    ViewModelFactory mViewModelFactory;

    private RecyclerView mNotesRecyclerView;
    private DisplayNotesRecyclerAdapter mNotesRecyclerAdapter;

    //Required default constructor
    public NoteListActivityFragment() {
    }

    /**
     *Get the view model instance using factory method
     * Instantiate new recycler view adapter and set empty array list of Notes
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Inject the current activity inside Dagger 2 dependency graph
        ApplicationComponent applicationComponent = ((ArchitectureComponents)getActivity().getApplication()).getApplicationComponent();
        applicationComponent.inject(this);

        mNoteListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(NoteListViewModel.class);
        mNotesRecyclerAdapter = new DisplayNotesRecyclerAdapter(getContext(), new ArrayList<Notes>());
    }

    /**
     *Inflate the fragment layout and its view components
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    /**
     *Initialize the view components and add the listeners once the fragment layout gets inflated
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mNotesRecyclerView = view.findViewById(R.id.note_list_recycler_view);
        bindData();

        mNoteListViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                mNotesRecyclerAdapter.setmLiveData(notes);
                mNotesRecyclerAdapter.notifyDataSetChanged();
                if (SCROLL_FLAG) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (mNotesRecyclerAdapter.getItemCount() > 0) {
                                mNotesRecyclerView.smoothScrollToPosition(mNotesRecyclerAdapter.getItemCount()-1);
                            }
                        }
                    }, 400);
                } else {
                    SCROLL_FLAG = true;
                }
            }
        });
    }

    /**
     * Bind the adapter to recycler view using linear layout manager
     */
    private void bindData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNotesRecyclerView.setLayoutManager(layoutManager);

        //Setting the divider for separating items in recycler view
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mNotesRecyclerView.getContext(),
                layoutManager.getOrientation()
        );
        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        getActivity(),
                        R.drawable.divider
                )
        );

        mNotesRecyclerView.addItemDecoration(
                itemDecoration
        );

        mNotesRecyclerView.setAdapter(mNotesRecyclerAdapter);
    }
}
