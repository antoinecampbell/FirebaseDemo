package com.antoinecampbell.firebase.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NOTE";

    private DatabaseReference database;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private View rootView;
    private Note note;

    public static Intent newInstance(Context context, Note note) {
        Intent intent = new Intent(context, NoteActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        database = FirebaseDatabase.getInstance().getReference();

        titleTextView = (TextView) findViewById(R.id.note_title);
        descriptionTextView = (TextView) findViewById(R.id.note_description);

        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note != null) {
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
        }

        rootView = findViewById(R.id.root);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (note == null) {
                    note = new Note();
                    note.setUid(database.child("notes").push().getKey());
                }
                note.setTitle(titleTextView.getText().toString());
                note.setDescription(descriptionTextView.getText().toString());
                database.child("notes").child(note.getUid()).setValue(note, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            finish();
                        } else {
                            Snackbar.make(rootView, "Unable to save note at this time", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}
