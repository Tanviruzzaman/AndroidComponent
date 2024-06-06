package com.example.dailynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.example.dailynotes.provider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private EditText titleEditText;
    private EditText descriptionEditText;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.edit_text_title);
        descriptionEditText = findViewById(R.id.edit_text_description);
        recyclerView = findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onViewClick(Note note) {
                viewNoteDetails(note);
            }

            @Override
            public void onDeleteClick(Note note) {
                deleteNoteFromProvider(note);
            }
        });
        recyclerView.setAdapter(noteAdapter);

        Button addNoteButton = findViewById(R.id.button_add_note);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteToProvider();
            }
        });

        loadNotesFromProvider();
    }

    private void addNoteToProvider() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", description);
        Uri newUri = getContentResolver().insert(CONTENT_URI, values);
        if (newUri != null) {
            loadNotesFromProvider();
            titleEditText.setText("");
            descriptionEditText.setText("");
            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotesFromProvider() {
        noteList.clear();
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                noteList.add(new Note(id, title, description));
            }
            cursor.close();
            noteAdapter.notifyDataSetChanged();
        }
    }

    private void viewNoteDetails(Note note) {
        // Show note details (e.g., in a dialog or a new activity)
        new AlertDialog.Builder(this)
                .setTitle(note.getTitle())
                .setMessage(note.getDescription())
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void deleteNoteFromProvider(Note note) {
        Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, note.getId());
        int rowsDeleted = getContentResolver().delete(noteUri, null, null);
        if (rowsDeleted > 0) {
            removeNoteFromList(note);
            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeNoteFromList(Note note) {
        int position = noteList.indexOf(note);
        if (position >= 0) {
            noteList.remove(position);
            noteAdapter.notifyItemRemoved(position);
        }
    }
}