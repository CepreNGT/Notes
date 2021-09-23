package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockDeviceNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FireStoreNotesRepository();
    private Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<Notes> list = new ArrayList<>();

    public MockDeviceNotesRepository() {
        list.add(new Notes("1", "First", "It's the first note", new Date()));
        list.add(new Notes("2", "Second", "It's the second note", new Date()));
        list.add(new Notes("3", "Third", "It's the third note", new Date()));
        list.add(new Notes("4", "Fourth", "It's the fourth note", new Date()));
        list.add(new Notes("5", "Fifth", "It's the fifth note", new Date()));
    }

    @Override
    public void getNotes(Callback<List<Notes>> callback) {
        new Thread(() -> handler.post(() -> callback.onSuccess(list))).start();
    }

    @Override
    public void addNote(String name, String details, Callback<Notes> callback) {
        new Thread(() -> {
            Notes newNote = new Notes("1", name, details, new Date());
            list.add(newNote);
            handler.post(() -> callback.onSuccess(newNote));
        }).start();
    }

    @Override
    public void removeNote(Notes note, Callback<Void> callback) {
        new Thread(() -> {
            list.remove(note);
            handler.post(() -> callback.onSuccess(null));
        }).start();
    }

    @Override
    public void editNote(Notes note, Callback<Notes> callback) {

    }
}
