package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.NoteDTO;
import com.mongodb.starter.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    //private final static Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final NoteService noteService;

    public NoteController(NoteService noteService) {

        this.noteService = noteService;
    }

    @PostMapping("note")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO postNote(@RequestBody NoteDTO NoteDTO) {
        return noteService.save(NoteDTO);
    }

    @PostMapping("notes")
    @ResponseStatus(HttpStatus.CREATED)
    public List<NoteDTO> postNotes(@RequestBody List<NoteDTO> noteEntities) {
        return noteService.saveAll(noteEntities);
    }
    @GetMapping("notes")
    public List<NoteDTO> getNotes() {
        return noteService.findAll();
    }

    @GetMapping("note/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable String id) {
        NoteDTO noteDTO = noteService.findOne(id);
        if (noteDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(noteDTO);
    }

    @GetMapping("notes/{ids}")
    public List<NoteDTO> getNotes(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return noteService.findAll(listIds);
    }

    @GetMapping("notes/count")
    public Long getCount() {
        return noteService.count();
    }

    @DeleteMapping("note/{id}")
    public Long deleteNote(@PathVariable String id) {
        return noteService.delete(id);
    }

    @DeleteMapping("notes/{ids}")
    public Long deleteNotes(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return noteService.delete(listIds);
    }

    @DeleteMapping("notes")
    public Long deleteNotes() {
        return noteService.deleteAll();
    }

    @PutMapping("note")
    public NoteDTO putNote(@RequestBody NoteDTO noteDTO) {
        return noteService.update(noteDTO);
    }

    @PutMapping("notes")
    public Long putNotes(@RequestBody List<NoteDTO> noteDTOS) {
        return noteService.update(noteDTOS);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        //LOGGER.error("Internal server error.", e);
        return e;
    }

}