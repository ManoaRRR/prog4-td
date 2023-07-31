package com.hei.project2p1.controller;

import com.hei.project2p1.model.Entreprise;
import com.hei.project2p1.service.EntrepriseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Controller
@AllArgsConstructor
public class EntrepriseController {
    private final EntrepriseService entrepriseService;

    @PostMapping("/addEntreprise")
    public String addEntreprise(@ModelAttribute("newEntreprise") Entreprise newEntreprise,
                                @RequestParam("logo") MultipartFile logo) {
        // Traiter le logo de l'entreprise et le stocker dans l'attribut 'logo' de l'entreprise
        entrepriseService.processEntrepriseLogo(newEntreprise, logo);

        // Enregistrer l'entreprise dans la base de données
        entrepriseService.saveEntreprise(newEntreprise);

        // Rediriger vers une autre page (par exemple la liste des entreprises) après l'ajout
        return "redirect:/listEntreprises";
    }
}
