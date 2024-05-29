package com.mongodb.starter.dtos;

import com.mongodb.starter.models.NoteEntity;
import org.bson.types.ObjectId;

public record NoteDTO(String id, String title, String content) {

    public NoteDTO(NoteEntity c) {
        this(c.getId() == null ? new ObjectId().toHexString() : c.getId().toHexString(), c.getTitle(), c.getContent());
    }

    public NoteEntity toNoteEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new NoteEntity(_id, title, content);
    }
}
