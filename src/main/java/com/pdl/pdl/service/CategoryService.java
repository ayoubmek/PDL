package com.pdl.pdl.service;
 
import com.pdl.pdl.entity.Depense;
import com.pdl.pdl.entity.Categorie;
import com.pdl.pdl.entity.History;
import com.pdl.pdl.repository.CategoryRepository;
import com.pdl.pdl.repository.DepenseRepository;
import com.pdl.pdl.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final DepenseRepository depenseRepository;
  private final HistoryRepository historyRepository;

  public CategoryService(CategoryRepository categoryRepository,
                         DepenseRepository depenseRepository,
                         HistoryRepository historyRepository) {
    this.categoryRepository = categoryRepository;
    this.depenseRepository = depenseRepository;
    this.historyRepository = historyRepository;
  }

  public List<Categorie> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Categorie> getCategorieById(Long id) {
    return categoryRepository.findById(id);
  }

  public Categorie saveCategorie(Categorie categorie) {
    boolean isNew = categorie.getId() == null;

    Categorie savedCategorie = categoryRepository.save(categorie);

    History history = new History();
    history.setAction(isNew ? "Create Categorie" : "Update Categorie");
    history.setDetails((isNew ? "Created" : "Updated") + " Categorie with ID: " + savedCategorie.getId());
    history.setActionDate(LocalDate.now());

    historyRepository.save(history);
    return savedCategorie;
  }

  public void deleteCategorie(Long id) {
    categoryRepository.findById(id).ifPresent(categorie -> {
      categoryRepository.delete(categorie);

      History history = new History();
      history.setAction("Delete Categorie");
      history.setDetails("Deleted Categorie with ID: " + id);
      history.setActionDate(LocalDate.now());
      historyRepository.save(history);
    });
  }

  public Depense addDepenseToCategorie(Long categorieId, String depenseName) {
    return categoryRepository.findById(categorieId)
            .map(categorie -> {
              Depense depense = new Depense();
              // Set additional fields if necessary, e.g. name
              depenseRepository.save(depense);

              History history = new History();
              history.setAction("Add Depense to Categorie");
              history.setDetails("Added depense '" + depenseName + "' to Categorie with ID: " + categorieId);
              history.setActionDate(LocalDate.now());
              historyRepository.save(history);

              return depense;
            })
            .orElse(null);
  }

  public void removeDepenseFromCategorie(Long categorieId, Long depenseId) {
    Optional<Categorie> categorieOpt = categoryRepository.findById(categorieId);
    Optional<Depense> depenseOpt = depenseRepository.findById(depenseId);

    if (categorieOpt.isPresent() && depenseOpt.isPresent()) {
      Depense depense = depenseOpt.get();
      depenseRepository.delete(depense);

      History history = new History();
      history.setAction("Remove Depense from Categorie");
      history.setDetails("Removed depense with ID: " + depenseId + " from Categorie with ID: " + categorieId);
      history.setActionDate(LocalDate.now());
      historyRepository.save(history);
    }
  }
}
