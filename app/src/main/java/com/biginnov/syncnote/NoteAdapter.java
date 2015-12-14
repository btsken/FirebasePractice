package com.biginnov.syncnote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.biginnov.syncnote.data.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> actors;

    private Context mContext;
    private View.OnClickListener mOnItemClickListener;

    public NoteAdapter(Context context, List<Note> actors, View.OnClickListener listener) {
        this.mContext = context;
        this.actors = actors;
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(mOnItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Note p = actors.get(i);
        viewHolder.mTitle.setText(p.getTitle());
    }

    @Override
    public int getItemCount() {
        return actors == null ? 0 : actors.size();
    }

    public Note getItem(int position) {
        Note device = null;
        if (position < actors.size()) {
            device = actors.get(position);
        }
        return device;
    }

    public void update(ArrayList<Note> devices) {
        actors.clear();
        actors = devices;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mContent;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.name);
            mContent = (TextView) v.findViewById(R.id.content_textview);
        }
    }
}
