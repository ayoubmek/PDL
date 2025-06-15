package com.pdl.pdl.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "depense")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private AppUser utilisateur;

    @Column(name = "justificatif_path")
    private String justificatifPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDepense statut = StatutDepense.SOUMIS;

    // Getters and setters...

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
