package com.mongodb.starter.dtos;

import com.mongodb.starter.models.NoteEntity;
import org.bson.types.ObjectId;

public record NoteDTO(String id, String brand, String model, Float maxSpeedKmH) {

    public NoteDTO(NoteEntity c) {
        this(c.getId() == null ? new ObjectId().toHexString() : c.getId().toHexString(), c.getBrand(), c.getModel(), c.getMaxSpeedKmH());
    }

    public NoteEntity toNoteEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new NoteEntity(_id, brand, model, maxSpeedKmH);
    }
}
