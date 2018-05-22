package com.jonikoone.bookmarks.entity;

import com.orm.SugarRecord;

import java.util.Date;

//модель таблицы
public class Bookmark extends SugarRecord<Bookmark> {

    private String name,
            note;
    private Date date;

    public Bookmark() {
    }

    public Bookmark(String name, String note, Date date) {
        this.name = name;
        this.note = note;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
