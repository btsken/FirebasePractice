package com.biginnov.syncnote.data;

/**
 * Created by ken on 2015/12/11.
 */
public class Note {
    long id;
    String title;
    String content;
    Tag tag;

    public Note(String title, String content) {
        id = System.currentTimeMillis();
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
