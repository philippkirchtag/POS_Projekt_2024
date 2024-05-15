package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.NoteDTO;
import com.mongodb.starter.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    private final NoteService noteService;

    public NoteController(NoteService noteService) {

        this.noteService = noteService;
    }

    @PostMapping("note")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO postNote(@RequestBody NoteDTO NoteDTO) {
        return noteService.save(NoteDTO);
    }

    @GetMapping("notes")
    public List<NoteDTO> getNotes() {
        return noteService.findAll();
    }


    @DeleteMapping("note/{id}")
    public Long deleteNote(@PathVariable String id) {
        return noteService.delete(id);
    }


    @PutMapping("note")
    public NoteDTO putNote(@RequestBody NoteDTO NoteDTO) {
        return noteService.update(NoteDTO);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
