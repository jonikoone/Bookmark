package com.jonikoone.bookmarks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jonikoone.bookmarks.entity.Bookmark;

import java.text.SimpleDateFormat;

public class PreviewBookmark extends AppCompatActivity {

    private TextView note, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview_bookmark);

        date = findViewById(R.id.date);
        note = findViewById(R.id.note);
        time = findViewById(R.id.time);

        Bookmark bookmark = CurrentData.getInstance().getBookmark();

        this.setTitle(bookmark.getName());

        date.setText(new SimpleDateFormat("dd MM yy").format(bookmark.getDate()));
        time.setText(new SimpleDateFormat("hh mm").format(bookmark.getDate()));
        note.setText(bookmark.getNote());

    }
}
