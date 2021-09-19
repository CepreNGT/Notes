package com.example.notes.ui.notelist;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Notes;
import com.example.notes.domain.NotesRepository;

public class NotesListPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();
        repository.getNotes(data -> {
            view.showNotes(data);
            view.hideProgress();
        });
    }

    public void addNote(String name, String details) {
        view.showProgress();
        repository.addNote(name, details, data -> {
            view.hideProgress();
            view.onNoteAdded(data);
        });
    }

    public void removeNote(Notes selectedNote) {
        view.showProgress();
        repository.removeNote(selectedNote, data -> {
            view.hideProgress();
            view.onNoteRemoved(selectedNote);
        });
    }
}
