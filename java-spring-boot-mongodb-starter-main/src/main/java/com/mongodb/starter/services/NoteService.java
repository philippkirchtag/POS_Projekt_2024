package com.mongodb.starter.services;

import com.mongodb.starter.dtos.NoteDTO;

import java.util.List;

public interface NoteService {
    NoteDTO save(NoteDTO noteDTO);

    List<NoteDTO> saveAll(List<NoteDTO> noteEntities);

    List<NoteDTO> findAll();

    List<NoteDTO> findAll(List<String> ids);

    NoteDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    NoteDTO update(NoteDTO noteDTO);

    long update(List<NoteDTO> noteEntities);
}
