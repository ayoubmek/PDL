package com.pdl.pdl.service;

import com.pdl.pdl.entity.Budget;
import com.pdl.pdl.entity.History;
import com.pdl.pdl.repository.BudgetRepository;
import com.pdl.pdl.repository.HistoryRepository;
import com.pdl.pdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    public Budget saveBudget(Budget budget) {
        if (budget.getUtilisateur() == null) {
            throw new RuntimeException("User cannot be null");
        }

        Budget savedBudget = budgetRepository.save(budget);

        History history = new History();
        history.setAction("Create");
        history.setDetails("Created a budget with ID: " + savedBudget.getId());
        history.setActionDate(LocalDate.now());
        historyRepository.save(history);

        return savedBudget;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }

    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));

        budgetRepository.deleteById(id);

        History history = new History();
        history.setAction("Delete");
        history.setDetails("Deleted budget with ID: " + id);
        history.setActionDate(LocalDate.now());
        historyRepository.save(history);
    }

    public Budget updateBudget(Long id, Budget updatedBudget) {
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));

        if (updatedBudget.getMontant() != null) {
            existingBudget.setMontant(updatedBudget.getMontant());
        }
        if (updatedBudget.getMois() != 0) {
            existingBudget.setMois(updatedBudget.getMois());
        }
        if (updatedBudget.getAnnee() != 0) {
            existingBudget.setAnnee(updatedBudget.getAnnee());
        }
        if (updatedBudget.getUtilisateur() != null) {
            existingBudget.setUtilisateur(updatedBudget.getUtilisateur());
        }

        Budget savedBudget = budgetRepository.save(existingBudget);

        History history = new History();
        history.setAction("Update");
        history.setDetails("Updated budget with ID: " + savedBudget.getId());
        history.setActionDate(LocalDate.now());
        historyRepository.save(history);

        return savedBudget;
    }
}
