package com.pdl.pdl.controller;

import com.pdl.pdl.entity.Depense;
import com.pdl.pdl.entity.StatutDepense;
import com.pdl.pdl.service.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depenses")
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    @PostMapping
    public ResponseEntity<Depense> createDepense(@RequestBody Depense depense) {
        Depense createdDepense = depenseService.saveDepense(depense);
        return ResponseEntity.ok(createdDepense);
    }

    @GetMapping
    public ResponseEntity<List<Depense>> getAllDepenses() {
        List<Depense> depenses = depenseService.getAllDepenses();
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depense> getDepenseById(@PathVariable Long id) {
        Depense depense = depenseService.getDepenseById(id);
        return ResponseEntity.ok(depense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depense> updateDepense(@PathVariable Long id, @RequestBody Depense depense) {
        Depense updatedDepense = depenseService.updateDepense(id, depense);
        return ResponseEntity.ok(updatedDepense);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Depense> updateDepenseStatus(
            @PathVariable Long id,
            @RequestParam StatutDepense status) {
        Depense updatedDepense = depenseService.updateDepenseStatus(id, status);
        return ResponseEntity.ok(updatedDepense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable Long id) {
        depenseService.deleteDepense(id);
        return ResponseEntity.noContent().build();
    }
}