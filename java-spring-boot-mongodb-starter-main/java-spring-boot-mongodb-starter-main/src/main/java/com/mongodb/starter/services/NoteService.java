package com.mongodb.starter.services;

import com.mongodb.starter.dtos.NoteDTO;

import java.util.List;

public interface NoteService {
    NoteDTO save(NoteDTO NoteDTO);
    List<NoteDTO> saveAll(List<NoteDTO> noteEntities);
    List<NoteDTO> findAll();
    List<NoteDTO> findAll(List<String> ids);
    long count();
    NoteDTO findOne(String id);
    long delete(String id);

    long delete(List<String> ids);
    long deleteAll();
    NoteDTO update(NoteDTO NoteDTO);
    long update(List<NoteDTO> noteEntities);
}
