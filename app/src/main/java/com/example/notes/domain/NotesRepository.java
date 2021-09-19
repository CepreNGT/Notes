package com.example.notes.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Notes>> callback);

    void addNote(String name, String details, Callback<Notes> callback);

    void removeNote(Notes note, Callback<Void> callback);
}
