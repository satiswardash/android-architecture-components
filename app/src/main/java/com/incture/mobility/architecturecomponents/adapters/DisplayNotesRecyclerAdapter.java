package com.incture.mobility.architecturecomponents.adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incture.mobility.architecturecomponents.R;
import com.incture.mobility.architecturecomponents.activities.CreateNoteActivity;
import com.incture.mobility.architecturecomponents.room.Notes;

import java.util.List;
import java.util.Random;

/**
 * Created by satiswardash on 11/02/18.
 */

public class DisplayNotesRecyclerAdapter extends RecyclerView.Adapter<DisplayNotesRecyclerAdapter.ViewHolder> {

    int colors[] = {};

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView color;
        TextView title;
        TextView timestamp;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.al_note_title);
            timestamp = itemView.findViewById(R.id.al_note_timestamp);
            color = itemView.findViewById(R.id.al_title_color);
        }

        public void bindDataItem (int position) {

            final Notes notes = mLiveData.get(position);

            if (notes != null) {
                title.setText(notes.getTitle());
                timestamp.setText(DateUtils.getRelativeTimeSpanString(notes.getTimestamp().getTime()));
                color.setText(""+notes.getTitle().charAt(0));
                color.setAllCaps(true);
                Random rnd = new Random();
                int colorCode = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                color.setBackgroundColor(colorCode);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CreateNoteActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("note", notes);
                        intent.putExtra("bundle", bundle);
                        mContext.startActivity(intent);

                    }
                });
            }

        }
    }

    public List<Notes> getmLiveData() {
        return mLiveData;
    }

    public void setmLiveData(List<Notes> mLiveData) {
        this.mLiveData = mLiveData;
    }
}
