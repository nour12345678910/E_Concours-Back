package com.concours.Controller;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.CandidatInfo;
import com.concours.Model.Concours;
import com.concours.Model.Diplome;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.DiplomeRepository;
import com.concours.services.ConcoursService;

@CrossOrigin("*")
@RequestMapping("/api/concours")
@RestController

public class ConcoursController {

	
@Autowired
ConcoursService cs ;


@Autowired
CandidatInfoRepository candidatinforep;


@Autowired
DiplomeRepository diplomeRepository;








		@PostMapping("/add")
		public Concours  addConcours(@RequestParam("poste") String  poste,@RequestParam("description") String  description,
									 @RequestParam("formule") String  formule,
									 @RequestParam("dateExamen") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExamen,
									 @RequestParam("dateDelais") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDelais,
									 @RequestParam("dateResultat") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateResultat,
		 
		@RequestParam("image")MultipartFile image, @RequestParam("diplomes")String diplomes) throws IOException {
		
		Concours c=new Concours();
		c.setDateDelais(dateDelais);
		c.setDateExamen(dateExamen);
		c.setDescription(description);
		c.setDateResultat(dateResultat);
		c.setFormule(formule);
		c.setImage(image.getBytes());
		c.setPoste(poste);
		c.setDiplomes(diplomes);
		return cs.add(c);
		
		}

		
		
		
		
		
		@PutMapping("/update/{id}")
		public Concours updateConcours(@PathVariable("id") Long id,
                              @RequestParam("poste") String poste,
                              @RequestParam("description") String description,
                              @RequestParam("formule") String formule,
                              @RequestParam("dateExamen") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExamen,
                              @RequestParam("dateDelais") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDelais,
                              @RequestParam("dateResultat") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateResultat,
                              @RequestParam("diplomes")String diplomes,
                              @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
	
			   // Récupération du concours à mettre à jour		
			   Concours c = cs.findById(id); 
			   c.setPoste(poste);
			   c.setDescription(description);
			   c.setFormule(formule);
			   c.setDateExamen(dateExamen);
			   c.setDateDelais(dateDelais);
			   c.setDateResultat(dateResultat);
			   c.setDiplomes(diplomes);
			
			   if (image != null && !image.isEmpty()) { 
			       c.setImage(image.getBytes());
			   }
			
			   // Mise à jour du concours dans la base de données
			   return cs.add(c); 
			}
		
		
		


		@GetMapping("/all")
		public ResponseEntity<List<Concours>> getAllConcours(){
		List<Concours> c=cs.findAll();
		return new ResponseEntity<>(c, HttpStatus.OK);
		}	

		

		@GetMapping("/{id}")
		public ResponseEntity<?> get(@PathVariable Long id) {
		try {
		return ResponseEntity.ok(cs.getConcoursById(id));
		} catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
		}
		}







		@DeleteMapping("/delete/{id}")
		   public ResponseEntity<Concours> deleteConcours(@PathVariable("id") Long id){
			//delete l'elt  de la table concours
		    cs.deleteById(id);
		    
		    
		    //delete des candidats qui ont participé aux concours supprimé
		    List<CandidatInfo> candidats = candidatinforep.findByConcoursId(id);
		    candidatinforep.deleteAll(candidats);
		       
		    
		    //delete les diplomes du concours supprimés 
		    for(CandidatInfo candidat:candidats) {
		    List<Diplome> diplomes = diplomeRepository.findByCandidat(candidat);
		       
		    diplomeRepository.deleteAll(diplomes);}
		   
		    return new ResponseEntity<>( HttpStatus.OK);
		}

 
 




		//get liste des candidats ByConcoursId
		  @GetMapping("/{id}/candidats")
		   public List<CandidatInfo> getCandidatsByConcoursId(@PathVariable("id") Long concoursId) {
			  
		       List<CandidatInfo> candidats = candidatinforep.findByConcoursId(concoursId);
		       // Return the list of candidats
		       return candidats;
		       
		   }


}



