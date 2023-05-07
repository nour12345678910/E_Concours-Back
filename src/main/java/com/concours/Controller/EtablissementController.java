package com.concours.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
import com.concours.Model.etablissementDTO;
import com.concours.services.EtablissementService;

@CrossOrigin("*")
@RequestMapping("/etablissement")
@RestController
public class EtablissementController {


	@Autowired
	EtablissementService es;
	
	
	@GetMapping("/all")
	 public ResponseEntity<List<etablissementDTO>> getetablissement(){
		
		List<Etablissement> List = es.find();
	    List<etablissementDTO> dtoList = new ArrayList<>();

	    for (Etablissement e : List) {
	     
	          String imagefond = Base64.getEncoder().encodeToString(e.getImagefond());
	          String imageData = Base64.getEncoder().encodeToString(e.getLogo());
	          dtoList.add(new etablissementDTO(e.getId(),e.getNom(),imageData,e.getNumFix(),e.getTelephone(),e.getAdresse(),e.getEmail(),imagefond));
	          
	                
	    }
	    return new ResponseEntity<>(dtoList, HttpStatus.OK);
	    
	}
	
	
	@PostMapping("/add")
	 public ResponseEntity<Etablissement> ajouterEtablissement(@RequestBody Etablissement e) {
		Etablissement etablissement=es.add(e);
	    	return new ResponseEntity<>(etablissement, HttpStatus.CREATED);
		}
	 
	 
	 
	   @PutMapping("/update")
	    public ResponseEntity<Etablissement> updateEtablissement( @RequestBody Etablissement e){
			
		   Etablissement etablissement=es.update(e);
	    	return new ResponseEntity<>(etablissement, HttpStatus.OK);
		}
	   
	   
		@GetMapping("{id}")
		 public ResponseEntity<Etablissement> getEtab(@PathVariable("id") Long id){
			Etablissement etablissement=es.findById(id);
			 return new ResponseEntity<>(etablissement, HttpStatus.OK);
		    }
	   
	   
	   
	   @PutMapping("/update/{id}")
	    public Etablissement updateEtablissement(@PathVariable("id") Long id,@RequestParam String nom,
	    		@RequestParam String adresse,@RequestParam String email,@RequestParam (required = false) MultipartFile logo,@RequestParam String numfix,
	    		@RequestParam String telephone,@RequestParam (required = false) MultipartFile imagefond ) throws IOException{
		   Etablissement upEtab=es.findById(id);
		    upEtab.setNom(nom);
		    upEtab.setAdresse(adresse);
		    upEtab.setEmail(email);
		    if (logo != null && !logo.isEmpty()) { // Si une nouvelle image a Ã©tÃ© uploadÃ©e
		    upEtab.setLogo(logo.getBytes());}
		    if (imagefond != null && !imagefond.isEmpty()) { // Si une nouvelle image a Ã©tÃ© uploadÃ©e
			    upEtab.setImagefond(imagefond.getBytes());}
		    upEtab.setNumFix(numfix);
		    upEtab.setTelephone(telephone);
		   
		    return es.add(upEtab);
		    
		   
		   
		   
	   }
	 
	   
	   
	  
	
	
}
