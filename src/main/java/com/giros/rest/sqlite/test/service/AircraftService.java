package com.giros.rest.sqlite.test.service;

import com.giros.rest.sqlite.test.model.Aircraft;
import com.giros.rest.sqlite.test.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class AircraftService {

    @Autowired
    AircraftRepository aircraftRepository;

    public Aircraft addAircraft(Aircraft aircraft) {
        if (!Objects.isNull(aircraft.getId())) {
            throw new IllegalArgumentException(
                "Aircraft id should no be specified upon creation");
        }

        return aircraftRepository.save(
            Aircraft.builder()
                .withCode(aircraft.getCode())
                .withManufacturer(aircraft.getManufacturer())
                .withModel(aircraft.getModel())
                .withDescription(aircraft.getDescription())
                .withStartDate(LocalDate.now())
                .build());
    }

    public Aircraft getAircraft(String id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    public Aircraft updateAircraft(Aircraft aircraft) {
        Aircraft existingAircraft = getAircraft(aircraft.getId());

        if (Objects.isNull(existingAircraft)) {
            throw new IllegalArgumentException(
                String.format("Aircraft with id %s does not exist", aircraft.getId()));
        }

        return aircraftRepository.save(
            Aircraft.builder()
                .withId(aircraft.getId())
                .withCode(aircraft.getCode())
                .withManufacturer(aircraft.getManufacturer())
                .withModel(aircraft.getModel())
                .withDescription(aircraft.getDescription())
                .withStartDate(existingAircraft.getStartDate())
                .build());
    }

    public void deleteAircraft(String id) {
        aircraftRepository.deleteById(id);
    }

    public List<Aircraft> searchAircrafts(String code, String manufacturer, String model, String description, LocalDate startDate) {
        return aircraftRepository.search(code, manufacturer, model, description, startDate);
    }

}
