package com.pdl.pdl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
@Entity
public class History {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String action;
  private String details;
  private LocalDate actionDate;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public LocalDate getActionDate() {
    return actionDate;
  }

  public void setActionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
  }


}
