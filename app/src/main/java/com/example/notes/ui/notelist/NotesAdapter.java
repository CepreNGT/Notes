package com.example.notes.ui.notelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final ArrayList<Notes> data = new ArrayList<>();
    private OnNoteClickedListener listener;

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(itemView);
    }

    public void setNotes(List<Notes> toSet) {
        data.clear();
        data.addAll(toSet);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = data.get(position);

        holder.getTitle().setText(note.getName());
        holder.getDate().setText(note.getDate().toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoteClickedListener {
        void onNoteClicked(Notes note);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                if (getListener() != null) {
                    getListener().onNoteClicked(data.get(getAdapterPosition()));
                }

            });
            title = itemView.findViewById(R.id.note_title);
            date = itemView.findViewById(R.id.note_date);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }
    }
}
