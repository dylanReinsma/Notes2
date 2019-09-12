package com.dylanreinsma.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dylanreinsma.notes.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {

    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    private LinedEditText linedEditText;
    private EditText editText;
    private TextView textView;
    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton check, backArrow;

    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        linedEditText = findViewById(R.id.noteText);
        editText = findViewById(R.id.noteEditText);
        textView = findViewById(R.id.noteTextTitle);
        checkContainer = findViewById(R.id.checkContainer);
        backArrowContainer = findViewById(R.id.backArrowContainer);
        check = findViewById(R.id.toolbarCheck);
        backArrow = findViewById(R.id.toolbarBackArrow);

        if (getIncomingIntent()) {
            //this is a new note (EDIT MODE)
            setNewNoteProperties();
            enableEditMode();
        } else {
            //this is not a new note (VIEW MODE)
            setNoteProperties();
            disableContentInteraction();
        }
        setListeners();
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            initialNote = getIntent().getParcelableExtra("selected_note");
            //Log.d(TAG, "getIncomingIntent: " + initialNote.toString());

            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }
        mode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    private void disableContentInteraction(){
        linedEditText.setKeyListener(null);
        linedEditText.setFocusable(false);
        linedEditText.setFocusableInTouchMode(false);
        linedEditText.setCursorVisible(false);
        linedEditText.clearFocus();

    }

    private void enableContentInteraction(){
        linedEditText.setKeyListener(new EditText(this).getKeyListener());
        linedEditText.setFocusable(true);
        linedEditText.setFocusableInTouchMode(true);
        linedEditText.setCursorVisible(true);
        linedEditText.requestFocus();

    }

    private void enableEditMode() {
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);

        mode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode() {
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;

        disableContentInteraction();
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null){
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void setListeners() {
        linedEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        textView.setOnClickListener(this);
        check.setOnClickListener(this);
        backArrow.setOnClickListener(this);
    }

    private void setNoteProperties() {
        textView.setText(initialNote.getTitle());
        editText.setText(initialNote.getTitle());
        linedEditText.setText(initialNote.getContent());
    }

    private void setNewNoteProperties() {
        textView.setText("Note Title");
        editText.setText("Note Edit Title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: tapped my guy!");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbarCheck:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }

            case R.id.noteTextTitle:{
                enableEditMode();
                textView.requestFocus();
                editText.setSelection(editText.length());
                break;
            }

            case R.id.toolbarBackArrow:{
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == EDIT_MODE_ENABLED){
            onClick(check);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mode = savedInstanceState.getInt("mode");
        if (mode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }
}
