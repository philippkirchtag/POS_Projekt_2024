package com.mongodb.starter;

import com.mongodb.starter.dtos.NoteDTO;
import com.mongodb.starter.models.NoteEntity;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TestHelper {

    NoteEntity getMaxEntity() {
        return new NoteEntity().setBrand("Ferrari")
                .setMaxSpeedKmH(339f)
                .setModel("SF90 Stradale");
    }

    NoteEntity getAlexEntity() {
        return new NoteEntity().setBrand("Mercedes")
                .setMaxSpeedKmH(355f)
                .setModel("Project One");
    }

    NoteDTO getMaxDTO() {
        return new NoteDTO(getMaxEntity());
    }

    public NoteDTO getMaxDTOWithId(ObjectId id) {
        return new NoteDTO(getMaxEntity().setId(id));
    }

    NoteDTO getAlexDTO() {
        return new NoteDTO(getAlexEntity());
    }

    NoteDTO getAlexDTOWithId(ObjectId id) {
        return new NoteDTO(getAlexEntity().setId(id));
    }

    List<NoteEntity> getListMaxAlexEntity() {
        return List.of(getMaxEntity(), getAlexEntity());
    }

    List<NoteDTO> getListMaxAlexDTO() {
        return List.of(getMaxDTO(), getAlexDTO());
    }
}
