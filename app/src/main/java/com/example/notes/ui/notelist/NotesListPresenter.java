package com.example.notes.ui.notelist;

import com.example.notes.domain.Notes;
import com.example.notes.domain.NotesRepository;

import java.util.List;

public class NotesListPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        List<Notes> list = repository.getNotes();
        view.showNotes(list);
    }
}
