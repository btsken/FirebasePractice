package com.biginnov.syncnote.data;

/**
 * Created by ken on 2015/12/11.
 */
public class Note {
    long time;
    String title;
    String content;
    Tag tag;
    String uid;
    String id;

    public Note() {
    }

    public Note(String title, String content, String uid) {
        this.time = System.currentTimeMillis();
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.id = String.valueOf(time);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
