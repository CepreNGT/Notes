package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.domain.Notes;
import com.example.notes.ui.notedetails.NoteDetailsFragment;
import com.example.notes.ui.notedetails.NotesDetailsActivity;
import com.example.notes.ui.notelist.NotesListFragment;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNoteClicked {

    private static final String ARG_NOTE = "ARG_NOTE";
    private Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNoteClicked(Notes note) {
        selectedNote = note;
        if (getResources().getBoolean(R.bool.isLandscape)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, NoteDetailsFragment.newInstance(note), null)
                    .commit();
        } else {
            Intent intent = new Intent(this, NotesDetailsActivity.class);
            intent.putExtra(NotesDetailsActivity.ARG_NOTE, note);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
        super.onSaveInstanceState(outState);
    }
}