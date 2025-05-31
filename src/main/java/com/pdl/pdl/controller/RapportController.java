package com.pdl.pdl.controller;

import com.pdl.pdl.entity.Rapport;
import com.pdl.pdl.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {
    private final RapportService rapportService;

    @Autowired
    public RapportController(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    @PostMapping
    public ResponseEntity<Rapport> createRapport(@RequestBody Rapport rapport) {
        Rapport createdRapport = rapportService.createRapport(rapport);
        return new ResponseEntity<>(createdRapport, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rapport>> getAllRapports() {
        List<Rapport> rapports = rapportService.getAllRapports();
        return new ResponseEntity<>(rapports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rapport> getRapportById(@PathVariable Long id) {
        return rapportService.getRapportById(id)
                .map(rapport -> new ResponseEntity<>(rapport, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rapport> updateRapport(
            @PathVariable Long id,
            @RequestBody Rapport rapportDetails) {
        try {
            Rapport updatedRapport = rapportService.updateRapport(id, rapportDetails);
            return new ResponseEntity<>(updatedRapport, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRapport(@PathVariable Long id) {
        try {
            rapportService.deleteRapport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}