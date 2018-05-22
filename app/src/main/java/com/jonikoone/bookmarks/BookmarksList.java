package com.jonikoone.bookmarks;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jonikoone.bookmarks.entity.Bookmark;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class BookmarksList extends AppCompatActivity implements View.OnCreateContextMenuListener {

    FloatingActionButton buttonAddNewBookmark;
    LinearLayout mainLinearLayout;

    LinkedList<BookmarkView> listBookmarkView = new LinkedList<>();

    View currentViewContexMenu;

    @Override
    protected void onStart() {
        super.onStart();
        uploadBookmarksEntityAndView();
    }

    private void uploadBookmarksEntityAndView() {
        try {
            listBookmarkView.clear();
            List<Bookmark> bookmarks = Bookmark.listAll(Bookmark.class);
            for (Bookmark bookmark : bookmarks)
                listBookmarkView.add(new BookmarkView(this, bookmark));

            mainLinearLayout.removeAllViews();
            LinearLayout.LayoutParams paramsBookmarkView =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            paramsBookmarkView.bottomMargin = 25;
            for (BookmarkView bmv : listBookmarkView) {
                registerForContextMenu(bmv);
                mainLinearLayout.addView(bmv, paramsBookmarkView);
            }
        } catch (SQLiteException exception) {
            exception.printStackTrace();
            Toast.makeText(this, "Ошибка подключения\nк базе данных", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks_list);

        mainLinearLayout = findViewById(R.id.mainBookMarkList);

        uploadBookmarksEntityAndView();

        buttonAddNewBookmark = findViewById(R.id.newBookMark);
        buttonAddNewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarksList.this, CreatingBookmark.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        currentViewContexMenu = v;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bookmark, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit: {
                CurrentData.getInstance().setBookmark(((BookmarkView) this.currentViewContexMenu).getBookMark());
                Intent intent = new Intent(BookmarksList.this, CreatingBookmark.class);
                startActivity(intent);
                break;
            }
            case R.id.delete: {
                ((BookmarkView) this.currentViewContexMenu).getBookMark().delete();
                uploadBookmarksEntityAndView();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }
}

class BookmarkView extends LinearLayout implements View.OnClickListener{

    private TextView name, date, note;
    private Bookmark bookmark;
    Context context;

    public BookmarkView(Context context, Bookmark bookmark) {
        super(context);
        this.context = context;
        this.bookmark = bookmark;
        this.setOrientation(VERTICAL);

        this.setBackgroundColor(Color.rgb(33, 33, 33));

        LayoutParams horizontalLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        horizontalLayoutParams.leftMargin = 10;
        horizontalLayoutParams.rightMargin = 10;
        horizontalLayoutParams.topMargin = 15;
        horizontalLayoutParams.bottomMargin = 5;
        LinearLayout horizontalLayout = new LinearLayout(this.getContext());
        horizontalLayout.setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams paramsName = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        paramsName.leftMargin = 15;
        LinearLayout.LayoutParams paramsDate = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
        LinearLayout.LayoutParams paramsNote = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        paramsNote.leftMargin = 30;
        paramsDate.gravity = Gravity.RIGHT;

        name = new TextView(this.getContext());
        name.setTextSize(20);
        name.setTextColor(Color.WHITE);
        name.setMaxLines(1);
        date = new TextView(this.getContext());
        date.setTextSize(20);
        date.setTextColor(Color.WHITE);
        date.setMaxLines(1);
        note = new TextView(this.getContext());
        note.setTextSize(15);
        note.setTextColor(Color.WHITE);
        note.setMaxLines(1);
        note.setBackgroundColor(Color.rgb(66, 66, 66));

        name.setText(bookmark.getName());
        note.setText(bookmark.getNote());
        date.setText(new SimpleDateFormat("dd MM yy").format(bookmark.getDate()));

        horizontalLayout.addView(name, paramsName);
        horizontalLayout.addView(date, paramsDate);

        this.addView(horizontalLayout, horizontalLayoutParams);
        this.addView(note, paramsNote);
        this.setOnClickListener(this);
    }

    public Bookmark getBookMark() {
        return bookmark;
    }

    @Override
    public String toString() {
        return bookmark.toString();
    }

    @Override
    public void onClick(View v) {
        CurrentData.getInstance().setBookmark(bookmark);
        Intent intent = new Intent(context, PreviewBookmark.class);
        context.startActivity(intent);
    }
}