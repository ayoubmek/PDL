package com.pdl.pdl.repository;

import com.pdl.pdl.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categorie, Long> {
}

