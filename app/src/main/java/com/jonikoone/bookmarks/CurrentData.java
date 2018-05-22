package com.jonikoone.bookmarks;

import com.jonikoone.bookmarks.entity.Bookmark;

//класс для передачи выбранной заметки в экран редактирования или просмотр
public class CurrentData {
    private static volatile CurrentData instance = null;

    private Bookmark bookmark;
    private boolean isUsed = false;

    public static CurrentData getInstance() {
        if (instance == null)
            instance = new CurrentData();
        return instance;
    }

    public boolean isUsed() {
        if (bookmark == null)
            return true;
        return isUsed;
    }

    public Bookmark getBookmark() {
        isUsed = true;
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
        isUsed = false;
    }
}
