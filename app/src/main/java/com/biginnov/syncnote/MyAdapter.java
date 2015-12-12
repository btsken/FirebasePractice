package com.biginnov.syncnote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.biginnov.syncnote.data.Note;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Note> actors;

    private Context mContext;

    public MyAdapter(Context context, List<Note> actors) {
        this.mContext = context;
        this.actors = actors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Note p = actors.get(i);
        viewHolder.mTextView.setText(p.getTitle());
    }

    @Override
    public int getItemCount() {
        return actors == null ? 0 : actors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
        }
    }
}
