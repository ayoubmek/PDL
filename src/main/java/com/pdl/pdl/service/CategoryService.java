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
  private CategoryRepository CategoryRepository;
  private DepenseRepository depenseRepository;
  private HistoryRepository historyRepository;


  public List<Categorie> getAllCategories() {
    return CategoryRepository.findAll();
  }

  public Optional<Categorie> getCategorieById(Long id) {
    return CategoryRepository.findById(id);
  }

  public Categorie saveCategorie(Categorie Categorie) {
    // Check if this is a new Categorie
    boolean isNew = Categorie.getId() == null;

    // Save the Categorie to the repository
    Categorie savedCategorie = CategoryRepository.save(Categorie);

    History history = new History();
    history.setAction(isNew ? "Create Categorie" : "Update Categorie");
    history.setDetails((isNew ? "Created" : "Updated") + " Categorie with ID: " + savedCategorie.getId());
    history.setActionDate(LocalDate.now());

    // Save the history entry
    historyRepository.save(history);

    return savedCategorie;
  }

  public void deleteCategorie(Long id) {
    CategoryRepository.findById(id).ifPresent(Categorie -> {
      CategoryRepository.delete(Categorie);

      History history = new History();
      history.setAction("Delete Categorie");
      history.setDetails("Deleted Categorie with ID: " + id);
      history.setActionDate(LocalDate.now());
      historyRepository.save(history);
    });
  }

  public Depense addDepenseToCategorie(Long CategorieId, String depenseName) {
    return CategoryRepository.findById(CategorieId)
            .map(Categorie -> {
              Depense depense = new Depense();
              depenseRepository.save(depense);

              History history = new History();
              history.setAction("Add Depense to Categorie");
              history.setDetails("Added depense '" + depenseName + "' to Categorie with ID: " + CategorieId);
              history.setActionDate(LocalDate.now());
              historyRepository.save(history);

              return depense;
            })
            .orElse(null);
  }

  public void removeDepenseFromCategorie(Long CategorieId, Long depenseId) {
    Optional<Categorie> CategorieOpt = CategoryRepository.findById(CategorieId);
    Optional<Depense> depenseOpt = depenseRepository.findById(depenseId);

    if (CategorieOpt.isPresent() && depenseOpt.isPresent()) {
      Categorie Categorie = CategorieOpt.get();
      Depense depense = depenseOpt.get();
      depenseRepository.delete(depense);

      History history = new History();
      history.setAction("Remove Depense from Categorie");
      history.setDetails("Removed depense with ID: " + depenseId + " from Categorie with ID: " + CategorieId);
      history.setActionDate(LocalDate.now());
      historyRepository.save(history);
    }
  }
}