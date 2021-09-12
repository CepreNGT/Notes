package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceNotesRepository implements NotesRepository {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void getNotes(Callback<List<Notes>> callback) {
        new Thread(() -> {
            ArrayList<Notes> list = new ArrayList<>();

            list.add(new Notes("First", "It's the first note", new Date()));
            list.add(new Notes("Second", "It's the second note", new Date()));
            list.add(new Notes("Third", "It's the third note", new Date()));
            list.add(new Notes("Fourth", "It's the fourth note", new Date()));
            list.add(new Notes("Fifth", "It's the fifth note", new Date()));

            handler.post(() -> callback.onSuccess(list));
        }).start();


    }
}
