package com.dylanreinsma.notes.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dylanreinsma.notes.models.Note;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";

    private static NoteDatabase instance;

    static NoteDatabase getInstance(final Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
}
