package com.dylanreinsma.notes.async;

import android.os.AsyncTask;

import com.dylanreinsma.notes.models.Note;
import com.dylanreinsma.notes.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;
    public UpdateAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.update(notes);
        return null;
    }
}
