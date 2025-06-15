package com.pdl.pdl.service;

import com.pdl.pdl.entity.Rapport;
import com.pdl.pdl.repository.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;
 
    public Rapport createRapport(Rapport rapport) {
        if (rapport.getDateGeneration() == null) {
            rapport.setDateGeneration(new Date());
        }
        return rapportRepository.save(rapport);
    }
 
    public List<Rapport> getAllRapports() {
        return rapportRepository.findAll();
    } 
    public Optional<Rapport> getRapportById(Long id) {
        return rapportRepository.findById(id);
    } 
    public Rapport updateRapport(Long id, Rapport updatedRapport) {
        return rapportRepository.findById(id)
                .map(rapport -> {
                    if (updatedRapport.getTitre() != null) {
                        rapport.setTitre(updatedRapport.getTitre());
                    }
                    if (updatedRapport.getContenu() != null) {
                        rapport.setContenu(updatedRapport.getContenu());
                    }
                    if (updatedRapport.getDateGeneration() != null) {
                        rapport.setDateGeneration(updatedRapport.getDateGeneration());
                    }
                    if (updatedRapport.getUtilisateur() != null) {
                        rapport.setUtilisateur(updatedRapport.getUtilisateur());
                    }
                    return rapportRepository.save(rapport);
                })
                .orElseThrow(() -> new RuntimeException("Rapport not found with id: " + id));
    }
 
    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }

}