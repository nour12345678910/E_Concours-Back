package com.concours.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concours.Model.Etablissement;
import com.concours.Model.Reclamation;
import com.concours.Model.ResourceNotFoundException;
import com.concours.Model.Response;
import com.concours.Repository.EtablissementRepository;
import com.concours.Repository.ReclamationRepository;
@CrossOrigin("*")




@RestController
@RequestMapping("/api/response")
public class ResponseController {
    
	
    @Autowired
    private JavaMailSender javaMailSender;
    
    
    @Autowired
    private ReclamationRepository reclamationRepository;
    
    
    @Autowired
	private EtablissementRepository etablissementRepository;
    
    
    
    
    @PostMapping("/answer/{id}")
    public ResponseEntity<Reclamation> sendResponse(@PathVariable(value = "id") Long reclamationId, @RequestBody Response response) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reclamation", "id", reclamationId));

        
     // Get etablissement
	    Optional<Etablissement> optionalEtablissement = etablissementRepository.findById(1L);
	        Etablissement etablissement = optionalEtablissement.get();
	        String nomsEtablissements = etablissement.getNom();
        
	    
        //envoyer le mail de réponse à l'utilisateur
        String toEmail = reclamation.getEmail();
        String subject = "الإجابة عن تساؤلك  #" + reclamation.getId();
        String text = ", " + reclamation.getNom() + " إلى السيد(ة) \n\n"
                + "  تم استلامه ومعالجته . " + reclamation.getSujet() + " موضوع تساؤلك المطروح وهو  \n\n"
                + "الإجابة عن استفسارك: " + response.getMessage() + "\n\n"
                + "  " + LocalDateTime.now() + " تاريخ ووقت الرد: \n\n"
                + "مع كل إحتراماتنا,\n"
                + nomsEtablissements;

        
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("centrenationalinformatique@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);

        
        
        //mettre la réponse à la réclamation 
        reclamation.setResponse(response);
        Reclamation savedReclamation = reclamationRepository.save(reclamation);
        return ResponseEntity.ok(savedReclamation);
    }
    
    
}