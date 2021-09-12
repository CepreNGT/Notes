package com.example.notes.ui.notelist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.DeviceNotesRepository;
import com.example.notes.domain.Notes;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String KEY_SELECTED_NOTE = "KEY_SELECTED_NOTE";
    public static final String ARG_NOTE = "ARG_NOTE";
    private NotesAdapter adapter;
    private NotesListPresenter presenter;
    private OnNoteClicked onNoteClicked;
    private ProgressBar progressBar;

    @Override
    public void showNotes(List<Notes> notes) {
        adapter.setNotes(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter();
        presenter = new NotesListPresenter(this, new DeviceNotesRepository());

        adapter.setListener(note -> Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView container = view.findViewById(R.id.notes_list);
        container.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        container.setAdapter(adapter);
        progressBar = view.findViewById(R.id.progress_bar);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.info_key) {
                Toast.makeText(requireContext(), "INFO", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (menuItem.getItemId() == R.id.add_key) {
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        presenter.requestNotes();

    }

    public interface OnNoteClicked {
        void onNoteClicked(Notes notes);
    }
}
