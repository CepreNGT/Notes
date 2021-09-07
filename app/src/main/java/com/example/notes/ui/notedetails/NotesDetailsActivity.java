package com.example.notes.ui.notedetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.notes.R;
import com.example.notes.domain.Notes;

public class NotesDetailsActivity extends AppCompatActivity {

    public static final String ARG_NOTE = "ARG_NOTE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        if (getResources().getBoolean(R.bool.isLandscape)) {
            finish();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Notes notes = getIntent().getParcelableExtra(ARG_NOTE);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, NoteDetailsFragment.newInstance(notes), "NoteDetailsFragment")
                    .commit();
        }
    }
}
