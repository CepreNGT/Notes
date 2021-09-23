package com.example.notes.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.domain.Notes;

import java.util.Date;

public class NoteDetailFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_NOTE_RESULT = "KEY_NOTE_RESULT";
    public static final String RESULT_NOTE_KEY = "RESULT_NOTE_KEY";
    public static final String REMOVED_NOTE_KEY = "REMOVED_NOTE_KEY";

    public NoteDetailFragment() {
    }

    public static NoteDetailFragment newInstance(Notes note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText title = view.findViewById(R.id.note_name_view);
        EditText description = view.findViewById(R.id.note_description);
        Notes note = requireArguments().getParcelable(ARG_NOTE);
        title.setText(note.getName());
        description.setText(note.getDescription());
        view.findViewById(R.id.save_button).setOnClickListener(view1 -> {
            note.setName(title.getText().toString());
            note.setDescription(description.getText().toString());
            note.setDate(new Date());
            Bundle result = new Bundle();
            result.putParcelable(RESULT_NOTE_KEY, note);
            result.putBoolean(REMOVED_NOTE_KEY, false);
            getParentFragmentManager()
                    .setFragmentResult(KEY_NOTE_RESULT, result);
            getParentFragmentManager().popBackStack();
        });
        view.findViewById(R.id.delete_button).setOnClickListener(view12 -> {
            showSimpleAlert(note);
        });
    }

    private void showSimpleAlert(Notes note) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.alert_message)
                .setMessage(R.string.alert_message_2)
                .setCancelable(true)
                .setPositiveButton(R.string.alert_positive_button, (dialogInterface, i) -> {
                    Bundle result = new Bundle();
                    result.putParcelable(RESULT_NOTE_KEY, note);
                    result.putBoolean(REMOVED_NOTE_KEY, true);
                    getParentFragmentManager()
                            .setFragmentResult(KEY_NOTE_RESULT, result);
                    Toast.makeText(requireContext(), "Note is deleted", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                })
                .setNegativeButton(R.string.alert_negative_button, (dialogInterface, i) -> {
                    dialogInterface.cancel();
                }).show();
    }
}