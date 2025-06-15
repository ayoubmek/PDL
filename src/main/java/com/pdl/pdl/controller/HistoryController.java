package com.pdl.pdl.controller;

import com.pdl.pdl.entity.History;
import com.pdl.pdl.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {

  @Autowired
  private HistoryService historyService;

  @GetMapping
  public ResponseEntity<List<History>> getAllHistories() {
    List<History> histories = historyService.getAllHistories();
    return ResponseEntity.ok(histories);
  }

  @PostMapping
  public ResponseEntity<History> addHistory(@RequestBody History history) {
    History savedHistory = historyService.addHistory(history);
    return ResponseEntity.ok(savedHistory);
  }

  @GetMapping("/{id}")
  public ResponseEntity<History> getHistoryById(@PathVariable Long id) {
    return historyService.getHistoryById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHistoryById(@PathVariable Long id) {
    if (historyService.getHistoryById(id).isPresent()) {
      historyService.deleteHistoryById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
