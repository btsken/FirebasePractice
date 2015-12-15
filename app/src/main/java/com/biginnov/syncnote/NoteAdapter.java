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

    private List<Note> mNotes = new ArrayList<>();

    private Context mContext;
    private View.OnClickListener mOnItemClickListener;

    public NoteAdapter(Context context, List<Note> actors, View.OnClickListener listener) {
        this.mContext = context;
        mNotes = new ArrayList<>(actors);
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
        Note p = mNotes.get(i);
        viewHolder.mTitle.setText(p.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    public Note getItem(int position) {
        Note device = null;
        if (position < mNotes.size()) {
            device = mNotes.get(position);
        }
        return device;
    }

    public void update(List<Note> notes) {
        mNotes.clear();
        mNotes = notes;
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

    public Note removeItem(int position) {
        final Note note = mNotes.remove(position);
        notifyItemRemoved(position);
        return note;
    }

    public void addItem(int position, Note note) {
        mNotes.add(position, note);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Note note = mNotes.remove(fromPosition);
        mNotes.add(toPosition, note);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Note> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Note> newModels) {
        for (int i = mNotes.size() - 1; i >= 0; i--) {
            final Note model = mNotes.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Note> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Note model = newModels.get(i);
            if (!mNotes.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Note> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Note model = newModels.get(toPosition);
            final int fromPosition = mNotes.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
