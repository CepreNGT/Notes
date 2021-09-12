package com.example.notes.ui.notelist;

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
}
