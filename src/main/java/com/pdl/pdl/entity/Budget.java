package com.pdl.pdl.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;

@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(nullable = false)
    private int mois;

    @Column(nullable = false)
    private int annee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonBackReference("budget-utilisateur")
    private AppUser utilisateur;

    public Budget() {
    }

    public Budget(BigDecimal montant, int mois, int annee, AppUser utilisateur) {
        this.montant = montant;
        this.mois = mois;
        this.annee = annee;
        this.utilisateur = utilisateur;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public int getMois() { return mois; }
    public void setMois(int mois) { this.mois = mois; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public AppUser getUtilisateur() { return utilisateur; }
    public void setUtilisateur(AppUser utilisateur) { this.utilisateur = utilisateur; }
}
