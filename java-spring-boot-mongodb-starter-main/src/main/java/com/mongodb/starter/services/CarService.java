package com.mongodb.starter.services;

import com.mongodb.starter.dtos.CarDTO;
import com.mongodb.starter.dtos.PersonDTO;

import java.util.List;

public interface CarService {
    CarDTO save(CarDTO CarDTO);

    List<CarDTO> saveAll(List<CarDTO> carEntities);

    List<CarDTO> findAll();

    List<CarDTO> findAll(List<String> ids);

    CarDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    CarDTO update(CarDTO CarDTO);

    long update(List<CarDTO> carEntities);
}
