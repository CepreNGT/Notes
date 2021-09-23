package com.example.notes.domain;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FireStoreNotesRepository implements NotesRepository {

    public static final String CREATED_AT = "created_at";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String NOTES = "Notes";


    @Override
    public void getNotes(Callback<List<Notes>> callback) {
        db.collection(NOTES)
                .orderBy(CREATED_AT, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Notes> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(new Notes(document.getId(), document.get(TITLE, String.class), document.get(DESCRIPTION, String.class), new Date(document.getDate(CREATED_AT).getTime())));
                        }
                        callback.onSuccess(list);
                    }
                });

    }

    @Override
    public void addNote(String title, String description, Callback<Notes> callback) {

        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(TITLE, title);
        data.put(DESCRIPTION, description);
        data.put(CREATED_AT, date);

        db.collection(NOTES)
                .add(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(new Notes(task.getResult().getId(), title, description, date));
                    }
                });
    }

    @Override
    public void removeNote(Notes note, Callback<Void> callback) {

        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(callback::onSuccess);
    }

    @Override
    public void editNote(Notes note, Callback<Notes> callback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(TITLE, note.getName());
        data.put(DESCRIPTION, note.getDescription());
        data.put(CREATED_AT, note.getDate());

        db.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(note);
                    }
                });
    }
}
