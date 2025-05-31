package com.pdl.pdl.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "rapport")
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_generation", nullable = false)
    private Date dateGeneration = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonBackReference("rapport-utilisateur")
    private AppUser utilisateur;

    public Rapport() {
    }

    public Rapport(String titre, String contenu, AppUser utilisateur) {
        this.titre = titre;
        this.contenu = contenu;
        this.utilisateur = utilisateur;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(Date dateGeneration) { this.dateGeneration = dateGeneration; }

    public AppUser getUtilisateur() { return utilisateur; }
    public void setUtilisateur(AppUser utilisateur) { this.utilisateur = utilisateur; }
}
