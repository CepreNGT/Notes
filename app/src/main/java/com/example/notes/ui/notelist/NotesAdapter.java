package com.example.notes.ui.notelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ListAdapter<Notes, NotesAdapter.NotesViewHolder> {

    public static final DiffUtil.ItemCallback<Notes> DIFF = new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.equals(newItem);
        }
    };

    private OnNoteClickedListener listener;
    private Fragment fragment;

    protected NotesAdapter(Fragment fragment) {
        super(DIFF);
        this.fragment = fragment;
    }

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

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = getCurrentList().get(position);

        holder.getTitle().setText(note.getName());
        holder.getDate().setText(note.getDate().toString());

    }

    interface OnNoteClickedListener {
        void onNoteClicked(Notes note);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(view -> {
                if (getListener() != null) {
                    getListener().onNoteClicked(getCurrentList().get(getAdapterPosition()));
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
