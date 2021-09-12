package com.example.notes.ui.notelist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.DeviceNotesRepository;
import com.example.notes.domain.Notes;
import com.example.notes.ui.Router;
import com.example.notes.ui.RouterHolder;
import com.example.notes.ui.detail.NoteDetailFragment;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private NotesAdapter adapter;
    private NotesListPresenter presenter;
    private ProgressBar progressBar;
    private RecyclerView container;
    private Router router;
    private boolean wasNotesRequested;

    @Override
    public void showNotes(List<Notes> notes) {
        adapter.setNotes(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof RouterHolder) {
            router = ((RouterHolder) context).getRouter();
        } else if (getParentFragment() instanceof RouterHolder) {
            router = ((RouterHolder) getParentFragment()).getRouter();
        }
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
    public void onNoteAdded(Notes note) {
        adapter.addNote(note);
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
        container.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onNoteRemoved(Notes selectedNote) {
        int position = adapter.removeNote(selectedNote);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter(this);
        presenter = new NotesListPresenter(this, DeviceNotesRepository.INSTANCE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager()
                .setFragmentResultListener(NoteDetailFragment.KEY_NOTE_RESULT, getViewLifecycleOwner(), (requestKey, result) -> {
                    Notes note = result.getParcelable(NoteDetailFragment.RESULT_NOTE_KEY);
                    boolean removed = result.getBoolean(NoteDetailFragment.REMOVED_NOTE_KEY);
                    if (!removed) {
                        int index = adapter.editNote(note);
                        adapter.notifyItemChanged(index);
                    } else {
                        presenter.removeNote(note);
                    }
                });

        container = view.findViewById(R.id.notes_list);
        container.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        container.setAdapter(adapter);
        progressBar = view.findViewById(R.id.progress_bar);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.add_key) {
                presenter.addNote("Unnamed", "No description");
                return true;
            }
            return false;
        });

        adapter.setListener(note -> {
            if (router != null) {
                router.showNote(note);
            }
        });
        if (!wasNotesRequested) {
            presenter.requestNotes();
            wasNotesRequested = true;
        }
    }
}
