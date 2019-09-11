package com.dylanreinsma.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.dylanreinsma.notes.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";

    private LinedEditText linedEditText;
    private EditText editText;
    private TextView textView;

    private boolean isNewNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        linedEditText = findViewById(R.id.noteText);
        editText = findViewById(R.id.noteTextTitle);
        textView = findViewById(R.id.note_title);

        if (getIntent().hasExtra("selected_note")) {

            Note note = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "onCreate: " + note.toString());
        }
    }
}
