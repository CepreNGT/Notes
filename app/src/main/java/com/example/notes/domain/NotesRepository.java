package com.example.notes.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Notes>> callback);
}
