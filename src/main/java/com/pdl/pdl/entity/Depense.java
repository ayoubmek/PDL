package com.pdl.pdl.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "depense")
public class Depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private BigDecimal montant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_depense", nullable = false)
    private Date dateDepense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    @JsonBackReference("categorie-reference")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonBackReference("utilisateur-reference")
    private AppUser utilisateur;

    @Column(name = "justificatif_path")
    private String justificatifPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDepense statut = StatutDepense.SOUMIS;

    public Depense() {
    }

    public Depense(String libelle, BigDecimal montant, Date dateDepense, Categorie categorie, AppUser utilisateur, String justificatifPath) {
        this.libelle = libelle;
        this.montant = montant;
        this.dateDepense = dateDepense;
        this.categorie = categorie;
        this.utilisateur = utilisateur;
        this.justificatifPath = justificatifPath;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public Date getDateDepense() { return dateDepense; }
    public void setDateDepense(Date dateDepense) { this.dateDepense = dateDepense; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public AppUser getUtilisateur() { return utilisateur; }
    public void setUtilisateur(AppUser utilisateur) { this.utilisateur = utilisateur; }
    public String getJustificatifPath() { return justificatifPath; }
    public void setJustificatifPath(String justificatifPath) { this.justificatifPath = justificatifPath; }

    public StatutDepense getStatut() { return statut; }
    public void setStatut(StatutDepense statut) { this.statut = statut; }
}
