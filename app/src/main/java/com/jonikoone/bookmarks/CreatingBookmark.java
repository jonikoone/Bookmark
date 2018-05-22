package com.jonikoone.bookmarks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jonikoone.bookmarks.entity.Bookmark;

import java.util.Date;

public class CreatingBookmark extends AppCompatActivity implements View.OnClickListener {

    TextView name, note;
    Button saveButton;

    //флаг для выбора редактирования или создания заметки
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_book_mark);

        name = findViewById(R.id.nameBookmarkCreating);
        note = findViewById(R.id.noteBookmarkCreating);
        saveButton = findViewById(R.id.saveCreating);
        saveButton.setOnClickListener(this);

        //если редактируется то загружаем данные заметки
        if (!CurrentData.getInstance().isUsed()) {
            isEdit = true;
            name.setText(CurrentData.getInstance().getBookmark().getName());
            note.setText(CurrentData.getInstance().getBookmark().getNote());
        }
    }

    @Override
    public void onClick(View v) {
        //проверяем все ли данные введены
        if (!note.getText().equals("") || !name.getText().equals("")) {
            //сохраняем изменненую заметку
            if (isEdit) {
                Bookmark bookmark = Bookmark.findById(Bookmark.class, CurrentData.getInstance().getBookmark().getId());
                bookmark.setName(name.getText().toString());
                bookmark.setNote(note.getText().toString());
                bookmark.save();
            //иначе сохраняем новою заметку
            } else {
                Bookmark bookmark = new Bookmark(name.getText().toString(),
                        note.getText().toString(),
                        new Date());

                bookmark.save();
            }
            this.finish();
        } else
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
    }
}
