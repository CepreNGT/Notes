package com.example.notes.ui;

import androidx.fragment.app.FragmentManager;

import com.example.notes.R;
import com.example.notes.domain.Notes;
import com.example.notes.ui.detail.NoteDetailFragment;
import com.example.notes.ui.info.InfoFragment;
import com.example.notes.ui.notelist.NotesListFragment;

public class Router {

    private FragmentManager fragmentManager;

    public Router(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new NotesListFragment())
                .commit();
    }

    public void showInfo() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new InfoFragment())
                .commit();
    }

    public void showNote(Notes note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteDetailFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}
