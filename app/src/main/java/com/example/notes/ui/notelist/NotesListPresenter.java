package com.example.notes.ui.notelist;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Notes;
import com.example.notes.domain.NotesRepository;

import java.util.ArrayList;

public class NotesListPresenter {

    private final ArrayList<Notes> notes = new ArrayList<>();
    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();
        repository.getNotes(data -> {
            notes.clear();
            notes.addAll(data);

            view.showNotes(new ArrayList<>(notes));
            view.hideProgress();
        });
    }

    public void addNote(String name, String details) {
        view.showProgress();
        repository.addNote(name, details, data -> {
            view.hideProgress();
            notes.add(data);

            view.showNotes(new ArrayList<>(notes));
        });
    }

    public void removeNote(Notes selectedNote) {
        view.showProgress();
        repository.removeNote(selectedNote, data -> {
            view.hideProgress();
            notes.remove(selectedNote);
            view.showNotes(new ArrayList<>(notes));
        });
    }

    public void editNote(Notes note) {
        view.showNotes(new ArrayList<>(notes));
    }
}
