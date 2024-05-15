package com.mongodb.starter;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.starter.dtos.NoteDTO;
import com.mongodb.starter.models.NoteEntity;
import com.mongodb.starter.repositories.NoteRepository;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TestHelper testHelper;
    private String URL;

    @Autowired
    NoteControllerIT(MongoClient mongoClient) {
        createNoteCollectionIfNotPresent(mongoClient);
    }

    @PostConstruct
    void setUp() {
        URL = "http://localhost:" + port + "/api";
    }

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
    }

    @DisplayName("POST /note with 1 note")
    @Test
    void postNote() {
        // GIVEN
        // WHEN
        ResponseEntity<NoteDTO> result = rest.postForEntity(URL + "/note", testHelper.getMaxDTO(), NoteDTO.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        NoteDTO noteDTOResult = result.getBody();
        assertThat(noteDTOResult).isNotNull();
        assertThat(noteDTOResult.id()).isNotNull();
        assertThat(noteDTOResult).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(testHelper.getMaxDTO());
    }

    @DisplayName("POST /notes with 2 notes")
    @Test
    void postNotes() {
        // GIVEN
        // WHEN
        HttpEntity<List<NoteDTO>> body = new HttpEntity<>(testHelper.getListMaxAlexDTO());
        ResponseEntity<List<NoteDTO>> response = rest.exchange(URL + "/notes", HttpMethod.POST, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(testHelper.getListMaxAlexDTO());
    }

    @DisplayName("GET /notes with 2 notes")
    @Test
    void getNotes() {
        // GIVEN
        List<NoteEntity> noteEntities = noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        // WHEN
        ResponseEntity<List<NoteDTO>> result = rest.exchange(URL + "/notes", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<NoteDTO> expected = List.of(testHelper.getMaxDTOWithId(noteEntities.get(0).getId()),
                testHelper.getAlexDTOWithId(noteEntities.get(1).getId()));
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("GET /note/{id}")
    @Test
    void getNoteById() {
        // GIVEN
        NoteEntity noteInserted = noteRepository.save(testHelper.getAlexEntity());
        ObjectId idInserted = noteInserted.getId();
        // WHEN
        ResponseEntity<NoteDTO> result = rest.getForEntity(URL + "/note/" + idInserted, NoteDTO.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveComparison()
                .isEqualTo(testHelper.getAlexDTOWithId(idInserted));
    }

    @DisplayName("GET /notes/{ids}")
    @Test
    void getNotesByIds() {
        // GIVEN
        List<NoteEntity> notesInserted = noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        List<String> idsInserted = notesInserted.stream().map(NoteEntity::getId).map(ObjectId::toString).toList();
        // WHEN
        String url = URL + "/notes/" + String.join(",", idsInserted);
        ResponseEntity<List<NoteDTO>> result = rest.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(testHelper.getListMaxAlexDTO());
    }

    @DisplayName("GET /notes/count")
    @Test
    void getCount() {
        // GIVEN
        noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        // WHEN
        ResponseEntity<Long> result = rest.getForEntity(URL + "/notes/count", Long.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
    }

    @DisplayName("DELETE /note/{id}")
    @Test
    void deleteNoteById() {
        // GIVEN
        NoteEntity noteInserted = noteRepository.save(testHelper.getMaxEntity());
        String idInserted = noteInserted.getId().toHexString();
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/note/" + idInserted, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(1L);
        assertThat(noteRepository.count()).isEqualTo(0L);
    }

    @DisplayName("DELETE /notes/{ids}")
    @Test
    void deleteNotesByIds() {
        // GIVEN
        List<NoteEntity> notesInserted = noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        List<String> idsInserted = notesInserted.stream().map(NoteEntity::getId).map(ObjectId::toString).toList();
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/notes/" + String.join(",", idsInserted),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(noteRepository.count()).isEqualTo(0L);
    }

    @DisplayName("DELETE /notes")
    @Test
    void deleteNotes() {
        // GIVEN
        noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/notes", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(noteRepository.count()).isEqualTo(0L);
    }

    @DisplayName("PUT /note")
    @Test
    void putNote() {
        // GIVEN
        NoteEntity noteInserted = noteRepository.save(testHelper.getMaxEntity());
        // WHEN
        noteInserted.setModel("Updated Model");
        HttpEntity<NoteDTO> body = new HttpEntity<>(new NoteDTO(noteInserted));
        ResponseEntity<NoteDTO> result = rest.exchange(URL + "/note", HttpMethod.PUT, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(
                new NoteDTO(noteRepository.findOne(noteInserted.getId().toString())));
        assertThat(result.getBody().model()).isEqualTo("Updated Model");
        assertThat(noteRepository.count()).isEqualTo(1L);
    }

    @DisplayName("PUT /notes with 2 notes")
    @Test
    void putNotes() {
        // GIVEN
        List<NoteEntity> notesInserted = noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        // WHEN
        notesInserted.get(0).setModel("Updated Model 1");
        notesInserted.get(1).setModel("Updated Model 2");
        HttpEntity<List<NoteDTO>> body = new HttpEntity<>(notesInserted.stream().map(NoteDTO::new).toList());
        ResponseEntity<Long> result = rest.exchange(URL + "/notes", HttpMethod.PUT, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        NoteEntity max = noteRepository.findOne(notesInserted.get(0).getId().toString());
        NoteEntity alex = noteRepository.findOne(notesInserted.get(1).getId().toString());
        assertThat(max.getModel()).isEqualTo("Updated Model 1");
        assertThat(alex.getModel()).isEqualTo("Updated Model 2");
        assertThat(noteRepository.count()).isEqualTo(2L);
    }

    @DisplayName("GET /notes/averageSpeed")
    @Test
    void getAverageSpeed() {
        // GIVEN
        noteRepository.saveAll(testHelper.getListMaxAlexEntity());
        // WHEN
        ResponseEntity<Long> result = rest.getForEntity(URL + "/notes/averageSpeed", Long.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Adjust the assertion according to your business logic for average speed calculation
        // assertThat(result.getBody()).isEqualTo(averageSpeed);
    }

    private void createNoteCollectionIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase db = mongoClient.getDatabase("test");
        if (!db.listCollectionNames().into(new ArrayList<>()).contains("notes")) {
            db.createCollection("notes");
        }
    }
}
