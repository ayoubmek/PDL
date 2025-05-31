package com.pdl.pdl.controller;

import com.pdl.pdl.service.CategoryService;
import com.pdl.pdl.entity.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private CategoryService CategoryService;

  @Autowired
  public CategoryController(CategoryService CategoryService) {
    this.CategoryService = CategoryService;
  }

  @GetMapping
  public ResponseEntity<List<Categorie>> getAllCategories() {
    List<Categorie> categories = CategoryService.getAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
    Optional<Categorie> Categorie = CategoryService.getCategorieById(id);
    return Categorie.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }


  @PostMapping
  public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie Categorie) {
    Categorie createdCategorie = CategoryService.saveCategorie(Categorie);
    return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
  }


  @PutMapping("/{id}")
  public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie Categorie) {
    Optional<Categorie> existingCategorie = CategoryService.getCategorieById(id);
    if (existingCategorie.isPresent()) {
      Categorie.setId(id);
      Categorie updatedCategorie = CategoryService.saveCategorie(Categorie);
      return ResponseEntity.ok(updatedCategorie);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }



  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
    Optional<Categorie> Categorie = CategoryService.getCategorieById(id);
    if (Categorie.isPresent()) {
      CategoryService.deleteCategorie(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
