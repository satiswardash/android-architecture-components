package com.incture.mobility.architecturecomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.utils.ApplicationUtility;
import com.incture.mobility.architecturecomponents.utils.Constants;
import com.incture.mobility.architecturecomponents.activities.CreateNoteActivity;
import com.incture.mobility.architecturecomponents.db.Notes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satiswardash on 11/02/18.
 */

public class DisplayNotesRecyclerAdapter extends RecyclerView.Adapter<DisplayNotesRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Notes> mLiveData;

    public DisplayNotesRecyclerAdapter(Context mContext, List<Notes> mLiveData) {
        this.mContext = mContext;
        this.mLiveData = mLiveData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindDataItem(position);
    }

    @Override
    public int getItemCount() {
        return mLiveData.size();
    }

    public void setmLiveData(List<Notes> mLiveData) {
        this.mLiveData = mLiveData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.al_title_color) TextView color;
        @BindView(R.id.al_note_title) TextView title;
        @BindView(R.id.al_note_timestamp) TextView timestamp;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void bindDataItem(int position) {
            final Notes notes = mLiveData.get(position);
            if (notes != null) {
                title.setText(notes.getTitle());
                timestamp.setText(DateUtils.getRelativeTimeSpanString(notes.getTimestamp().getTime()));
                color.setText("" + notes.getTitle().charAt(0));
                color.setAllCaps(true);
                color.setBackgroundColor(ApplicationUtility.getInstance().getColor());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CreateNoteActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.NOTES_KEY, notes);
                        intent.putExtra(Constants.BUNDLE_KEY, bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }
}
