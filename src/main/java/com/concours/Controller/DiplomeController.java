package com.concours.Controller;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.concours.Model.CandidatInfo;
import com.concours.Model.Diplome;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.DiplomeRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/candidats/{candidatId}/diplomes")
public class DiplomeController {
    
    @Autowired
    private DiplomeRepository diplomeRepository;
    
    @Autowired
    private CandidatInfoRepository candidatRepository;
    
    @PostMapping
    public Diplome createDiplome(@PathVariable Long candidatId, @RequestBody Diplome diplome) {
        CandidatInfo candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new EntityNotFoundException("Candidat not found with id " + candidatId));
        diplome.setCandidat(candidat);
        return diplomeRepository.save(diplome);
    }
}



