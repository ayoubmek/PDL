package com.pdl.pdl.service;

import com.pdl.pdl.entity.History;
import com.pdl.pdl.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

  @Autowired
  private HistoryRepository historyRepository;


  public List<History> getAllHistories() {
    return historyRepository.findAll();
  }

  public History addHistory(History history) {
    return historyRepository.save(history);
  }

  public Optional<History> getHistoryById(Long id) {
    return historyRepository.findById(id);
  }

   public void deleteHistoryById(Long id) {
    historyRepository.deleteById(id);
  }
}
