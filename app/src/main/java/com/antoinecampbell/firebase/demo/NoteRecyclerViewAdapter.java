package com.antoinecampbell.firebase.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private List<Note> notes;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView descriptionTextView;
        private Note note;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.note_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.note_description);
            itemView.setOnClickListener(this);
        }

        public void bind(Note note) {
            this.note = note;
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            context.startActivity(NoteActivity.newInstance(context, note));
        }
    }

    public NoteRecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateList(List<Note> notes) {
        // Allow recyclerview animations to complete normally if we already know about data changes
        if (notes.size() != this.notes.size() || !this.notes.containsAll(notes)) {
            this.notes = notes;
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public Note getItem(int position) {
        return notes.get(position);
    }
}
