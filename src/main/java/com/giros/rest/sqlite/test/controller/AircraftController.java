package com.giros.rest.sqlite.test.controller;

import com.giros.rest.sqlite.test.model.Aircraft;
import com.giros.rest.sqlite.test.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/aircraft")
public class AircraftController {

    @Autowired
    AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<?> addAircraft(@RequestBody Aircraft aircraft) {
        try {
            return ResponseEntity.ok(aircraftService.addAircraft(aircraft));
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAircraft(@PathVariable("id") String id) {
        return ResponseEntity.ok(aircraftService.getAircraft(id));
    }

    @PutMapping
    public ResponseEntity<?> updateAircraft(@RequestBody Aircraft aircraft) {
        try {
            return ResponseEntity.ok(aircraftService.updateAircraft(aircraft));
        }
        catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAircraft(@PathVariable("id") String id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchAircrafts(
        @RequestParam(required = false, name = "code") String code,
        @RequestParam(required = false, name = "manufacturer") String manufacturer,
        @RequestParam(required = false, name = "model") String model,
        @RequestParam(required = false, name = "description") String description,
        @RequestParam(required = false, name = "startDate") LocalDate startDate) {

        return ResponseEntity.ok(aircraftService.searchAircrafts(code, manufacturer, model, description, startDate));
    }
}
