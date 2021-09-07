package com.example.notes.ui.notedetails;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notes.R;
import com.example.notes.domain.Notes;
import com.example.notes.ui.notelist.NotesListFragment;

public class NoteDetailsFragment extends Fragment {

    private TextView noteName;
    private TextView noteDescription;
    public static final String ARG_NOTE = "ARG_NOTE";

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    public static NoteDetailsFragment newInstance(Notes note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle arguments = new Bundle();

        arguments.putParcelable(ARG_NOTE, note);

        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteName = view.findViewById(R.id.note_name_view);

        noteDescription = view.findViewById(R.id.note_description);

        getParentFragmentManager().setFragmentResultListener(NotesListFragment.KEY_SELECTED_NOTE, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                Notes note = result.getParcelable(NotesListFragment.ARG_NOTE);

                displayNote(note);
            }
        });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {

            Notes note = getArguments().getParcelable(ARG_NOTE);

            if (note != null) {
                displayNote(note);
            }
        }
    }


    private void displayNote(Notes note) {
        noteName.setText(note.getName());
        noteDescription.setText(note.getDescription());
    }
}
