package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.starter.models.NoteEntity; // Änderung hier
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBNoteRepository implements NoteRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<NoteEntity> noteCollection;

    public MongoDBNoteRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        noteCollection = client.getDatabase("test").getCollection("notes", NoteEntity.class);
    }

    @Override
    public NoteEntity save(NoteEntity noteEntity) {
        noteEntity.setId(new ObjectId());
        noteCollection.insertOne(noteEntity);
        return noteEntity;
    }

    @Override
    public List<NoteEntity> saveAll(List<NoteEntity> noteEntities) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                noteEntities.forEach(c -> c.setId(new ObjectId()));
                noteCollection.insertMany(clientSession, noteEntities);
                return noteEntities;
            }, txnOptions);
        }
    }

    @Override
    public List<NoteEntity> findAll() {
        return noteCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<NoteEntity> findAll(List<String> ids) {
        return noteCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public NoteEntity findOne(String id) {
        return noteCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return noteCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return noteCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> noteCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> noteCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public NoteEntity update(NoteEntity noteEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return noteCollection.findOneAndReplace(eq("_id", noteEntity.getId()), noteEntity, options);
    }

    @Override
    public long update(List<NoteEntity> noteEntities) {
        List<ReplaceOneModel<NoteEntity>> writes = noteEntities.stream()
                .map(c -> new ReplaceOneModel<>(eq("_id", c.getId()), c))
                .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> noteCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).toList();
    }
}