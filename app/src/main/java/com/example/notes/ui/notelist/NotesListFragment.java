package com.example.notes.ui.notelist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.DeviceNotesRepository;
import com.example.notes.domain.Notes;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String KEY_SELECTED_NOTE = "KEY_SELECTED_NOTE";
    public static final String ARG_NOTE = "ARG_NOTE";
    private NotesListPresenter presenter;
    private OnNoteClicked onNoteClicked;
    private LinearLayout container;

    @Override
    public void showNotes(List<Notes> notes) {
        for (Notes note : notes) {
            View noteItem = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, container, false);

            noteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoteClicked != null) {
                        onNoteClicked.onNoteClicked(note);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ARG_NOTE, note);

                    getParentFragmentManager().setFragmentResult(KEY_SELECTED_NOTE, bundle);
                }
            });

            TextView noteName = noteItem.findViewById(R.id.text_view_name_note);

            noteName.setText(note.getName());

            TextView noteDate = noteItem.findViewById(R.id.text_view_date_note);

            noteDate.setText(note.getDate().toString());
            container.addView(noteItem);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new DeviceNotesRepository());
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
        container = view.findViewById(R.id.linear_layout);
        presenter.requestNotes();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Notes notes);
    }
}
