package com.concours.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.concours.Model.Etablissement;
import com.concours.services.EtablissementService;



@CrossOrigin("*")
@RequestMapping("/etablissement")
@RestController
public class EtablissementController {


	@Autowired
	EtablissementService es;
	
	
	
	
	
	   @PutMapping("/update/{id}")
	    public Etablissement updateEtablissement(@PathVariable("id") Long id,@RequestParam String nom,
	    		@RequestParam String adresse,@RequestParam String email,@RequestParam (required = false) MultipartFile logo,@RequestParam String numfix,
	    		@RequestParam String telephone,@RequestParam (required = false) MultipartFile imagefond ) throws IOException{
		   
			   Etablissement upEtab=es.findById(id);
			   
			   upEtab.setNom(nom);
			   upEtab.setAdresse(adresse);
			   upEtab.setEmail(email);
			   upEtab.setNumFix(numfix);
			   upEtab.setTelephone(telephone);
		   
		   
		    if (logo != null && !logo.isEmpty())
		    { 	
		    	upEtab.setLogo(logo.getBytes());
		    }
		    
		    if (imagefond != null && !imagefond.isEmpty()) 
		    { 
				upEtab.setImagefond(imagefond.getBytes());
			}

		   
		    return es.update(upEtab);
		       
	    }
	
	
	
	
	
	 @GetMapping("/all")
	 public ResponseEntity<List<Etablissement>> getAllConcours(){
		 List<Etablissement> c=es.find();
		 return new ResponseEntity<>(c, HttpStatus.OK);
	   }
	
	
	@PostMapping("/add")
	 public ResponseEntity<Etablissement> ajouterEtablissement(@RequestBody Etablissement e) {
		
		Etablissement etablissement=es.add(e);
	    return new ResponseEntity<>(etablissement, HttpStatus.CREATED);
	
	   }
	 
	 
	 
	
	   
	   
		@GetMapping("/{id}")
		 public ResponseEntity<Etablissement> getEtab(@PathVariable("id") Long id){
			Etablissement etablissement=es.findById(id);
			return new ResponseEntity<>(etablissement, HttpStatus.OK);
			
	   }
	   
	   
	   	
}
