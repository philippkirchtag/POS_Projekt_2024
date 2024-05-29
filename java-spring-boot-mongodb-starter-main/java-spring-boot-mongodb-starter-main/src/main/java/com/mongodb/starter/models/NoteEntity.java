package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class NoteEntity {

    private ObjectId id;
    private String title;
    private String content;

    public NoteEntity() {
    }

    public NoteEntity(ObjectId id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public NoteEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoteEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteEntity setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "Note{" + "title='" + title + '\'' + ", content='" + content +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity noteEntity = (NoteEntity) o;
        return Objects.equals(title, noteEntity.title) && Objects.equals(content, noteEntity.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}