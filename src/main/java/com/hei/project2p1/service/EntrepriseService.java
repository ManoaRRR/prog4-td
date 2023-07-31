package com.hei.project2p1.service;

import com.hei.project2p1.model.Entreprise;
import com.hei.project2p1.repository.EntrepriseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class EntrepriseService {
    private EntrepriseRepository entrepriseRepository;
    public void saveEntreprise(Entreprise entreprise){
         entrepriseRepository.save(entreprise);
    }

    private List<Entreprise> getEntreprise(){
        return entrepriseRepository.findAll();
    }

    public void processEntrepriseLogo(Entreprise entreprise, MultipartFile logo) {
        try {
            // Vérifier si un fichier d'image est téléchargé
            if (logo != null && !logo.isEmpty()) {
                // Si un fichier d'image est téléchargé, lisez le contenu du fichier et attribuez-le à la propriété 'logo' de l'entreprise
                entreprise.setLogo(logo.getBytes());
            } else {
                // Si aucun fichier d'image n'est téléchargé, attribuez une valeur null à la propriété 'logo' de l'entreprise
                entreprise.setLogo(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur ici, par exemple : logger.error("Erreur lors du traitement du logo", e);
        }
    }


}
