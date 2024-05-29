package com.mongodb.starter.repositories;

import com.mongodb.starter.models.NoteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository {
    NoteEntity save(NoteEntity noteEntity);
    List<NoteEntity> saveAll(List<NoteEntity> noteEntities);
    List<NoteEntity> findAll();
    List<NoteEntity> findAll(List<String> ids);
    NoteEntity findOne(String id);
    long count();
    long delete(String id);

    long delete(List<String> ids);
    long deleteAll();
    NoteEntity update(NoteEntity noteEntity);
    long update(List<NoteEntity> noteEntities);
}
