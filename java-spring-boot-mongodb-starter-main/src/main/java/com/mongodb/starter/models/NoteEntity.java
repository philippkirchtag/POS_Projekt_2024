package com.mongodb.starter.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class NoteEntity {
    private ObjectId id;
    private String brand;
    private String model;
    private Float maxSpeedKmH;

    public NoteEntity() {
    }

    public NoteEntity(ObjectId id, String brand, String model, Float maxSpeedKmH) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxSpeedKmH = maxSpeedKmH;
    }

    public ObjectId getId() {
        return id;
    }

    public NoteEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public NoteEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public NoteEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public Float getMaxSpeedKmH() {
        return maxSpeedKmH;
    }

    public NoteEntity setMaxSpeedKmH(Float maxSpeedKmH) {
        this.maxSpeedKmH = maxSpeedKmH;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" + "brand='" + brand + '\'' + ", model='" + model + '\'' + ", maxSpeedKmH=" + maxSpeedKmH + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity noteEntity = (NoteEntity) o;
        return Objects.equals(brand, noteEntity.brand) && Objects.equals(model, noteEntity.model) && Objects.equals(
                maxSpeedKmH, noteEntity.maxSpeedKmH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, maxSpeedKmH);
    }
}
