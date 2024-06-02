package com.mongodb.starter.services;

import com.mongodb.starter.dtos.NoteDTO;
import com.mongodb.starter.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        return new NoteDTO(noteRepository.save(noteDTO.toNoteEntity()));
    }

    @Override
    public List<NoteDTO> saveAll(List<NoteDTO> noteEntities) {
        return noteEntities.stream()
                .map(NoteDTO::toNoteEntity)
                .peek(noteRepository::save)
                .map(NoteDTO::new)
                .toList();
    }

    @Override
    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream().map(NoteDTO::new).toList();
    }

    @Override
    public List<NoteDTO> findAll(List<String> ids) {
        return noteRepository.findAll(ids).stream().map(NoteDTO::new).toList();
    }

    @Override
    public NoteDTO findOne(String id) {
        return new NoteDTO(noteRepository.findOne(id));
    }

    @Override
    public long count() {
        return noteRepository.count();
    }

    @Override
    public long delete(String id) {
        return noteRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return noteRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return noteRepository.deleteAll();
    }

    @Override
    public NoteDTO update(NoteDTO noteDTO) {
        return new NoteDTO(noteRepository.update(noteDTO.toNoteEntity()));
    }

    @Override
    public long update(List<NoteDTO> noteEntities) {
        return noteRepository.update(noteEntities.stream().map(NoteDTO::toNoteEntity).toList());
    }
}

