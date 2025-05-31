package com.pdl.pdl.service;

import com.pdl.pdl.entity.Depense;
import com.pdl.pdl.entity.StatutDepense;
import com.pdl.pdl.entity.History;
import com.pdl.pdl.repository.CategoryRepository;
import com.pdl.pdl.repository.DepenseRepository;
import com.pdl.pdl.repository.UserRepository;
import com.pdl.pdl.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DepenseService {

  @Autowired
  private DepenseRepository depenseRepository;

  @Autowired
  private CategoryRepository categorieRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private HistoryRepository historyRepository;

  public Depense saveDepense(Depense depense) {
    if (depense.getCategorie() == null || depense.getUtilisateur() == null) {
      throw new RuntimeException("Category or User cannot be null");
    }

    // Set default status if not provided
    if (depense.getStatut() == null) {
      depense.setStatut(StatutDepense.SOUMIS);
    }

    Depense savedDepense = depenseRepository.save(depense);

    History history = new History();
    history.setAction("Create");
    history.setDetails("Created a depense with ID: " + savedDepense.getId());
    history.setActionDate(LocalDate.now());
    historyRepository.save(history);

    return savedDepense;
  }

  public List<Depense> getAllDepenses() {
    return depenseRepository.findAll();
  }

  public Depense getDepenseById(Long id) {
    return depenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Depense not found with id: " + id));
  }

  public void deleteDepense(Long id) {
    Depense depense = depenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Depense not found with id: " + id));

    History history = new History();
    history.setAction("Delete");
    history.setDetails("Deleted a depense with ID: " + id);
    history.setActionDate(LocalDate.now());
    historyRepository.save(history);

    depenseRepository.deleteById(id);
  }


  public Depense updateDepense(Long id, Depense updatedDepense) {
    Depense existingDepense = depenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Depense not found with id: " + id));

    if (updatedDepense.getLibelle() != null) {
      existingDepense.setLibelle(updatedDepense.getLibelle());
    }
    if (updatedDepense.getMontant() != null) {
      existingDepense.setMontant(updatedDepense.getMontant());
    }
    if (updatedDepense.getDateDepense() != null) {
      existingDepense.setDateDepense(updatedDepense.getDateDepense());
    }
    if (updatedDepense.getCategorie() != null) {
      existingDepense.setCategorie(updatedDepense.getCategorie());
    }
    if (updatedDepense.getJustificatifPath() != null) {
      existingDepense.setJustificatifPath(updatedDepense.getJustificatifPath());
    }
    if (updatedDepense.getStatut() != null) {
      existingDepense.setStatut(updatedDepense.getStatut());
    }

    Depense savedDepense = depenseRepository.save(existingDepense);

    History history = new History();
    history.setAction("Update");
    history.setDetails("Updated a depense with ID: " + savedDepense.getId());
    history.setActionDate(LocalDate.now());
    historyRepository.save(history);

    return savedDepense;
  }

  public Depense updateDepenseStatus(Long id, StatutDepense newStatus) {
    Depense depense = depenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Depense not found with id: " + id));

    depense.setStatut(newStatus);
    Depense savedDepense = depenseRepository.save(depense);

    History history = new History();
    history.setAction("Status Update");
    history.setDetails("Updated status of depense with ID: " + id + " to " + newStatus);
    history.setActionDate(LocalDate.now());
    historyRepository.save(history);

    return savedDepense;
  }


}