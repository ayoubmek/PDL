package com.pdl.pdl.repository;

import com.pdl.pdl.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {}

