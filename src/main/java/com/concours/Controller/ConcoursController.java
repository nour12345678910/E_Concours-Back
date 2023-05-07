package com.concours.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.CandidatInfo;
import com.concours.Model.Concours;
import com.concours.Model.ConcoursDTO;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.services.ConcoursService;

@CrossOrigin("*")
@RequestMapping("/api/concours")
@RestController

public class ConcoursController {
	
	@Autowired
	ConcoursService cs ;
	

	@Autowired
	CandidatInfoRepository candidatinforep;
	
	@GetMapping("/{id}")
	public ResponseEntity<ConcoursDTO> getConcoursById(@PathVariable Long id) {
	    Optional<Concours> optionalConcours = cs.findByid(id);
	    if (optionalConcours.isPresent()) {
	        Concours concours = optionalConcours.get();
	        String imageData = Base64.getEncoder().encodeToString(concours.getImage());
	        ConcoursDTO concoursDTO = new ConcoursDTO(concours.getId(), concours.getPoste(), imageData, concours.getDescription(), concours.getDateExamen(), concours.getDateDelais());
	        return new ResponseEntity<>(concoursDTO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	
	  
	  


	@PutMapping("/hhh/{id}")
	public Concours updateConcours(@PathVariable("id") Long id,
	                               @RequestParam("poste") String poste,
	                               @RequestParam("description") String description,
	                               @RequestParam("dateExamen") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExamen,
	                               @RequestParam("dateDelais") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDelais,
	                               @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
	    Concours c = cs.findById(id); // Récupération du concours à mettre à jour
	    c.setPoste(poste);
	    c.setDescription(description);
	    c.setDateExamen(dateExamen);
	    c.setDateDelais(dateDelais);

	    if (image != null && !image.isEmpty()) { // Si une nouvelle image a été uploadée
	        c.setImage(image.getBytes());
	    }

	    return cs.add(c); // Mise à jour du concours dans la base de données
	}



	
	
	
	
	

	  @GetMapping("/all")
	  public ResponseEntity<List<ConcoursDTO>> getAllConcours() {
	      List<Concours> concoursList = cs.findAll();
	      List<ConcoursDTO> concoursDTOList = new ArrayList<>();

	      for (Concours concours : concoursList) {
	          String imageData = Base64.getEncoder().encodeToString(concours.getImage());
	          concoursDTOList.add(new ConcoursDTO(concours.getId(), concours.getPoste(), imageData, concours.getDescription(), concours.getDateExamen(), concours.getDateDelais()));
	      }

	      return new ResponseEntity<>(concoursDTOList, HttpStatus.OK);
	  }



	




	 
	 @PostMapping("/add")
	 public Concours  addConcours(@RequestParam("poste") String  poste,@RequestParam("description") String  description,
			 @RequestParam("dateExamen") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExamen,
	           @RequestParam("dateDelais") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDelais, 
			 @RequestParam("image")MultipartFile image) throws IOException {

		 Concours c=new Concours();
		 c.setDateDelais(dateDelais);
		 c.setDateExamen(dateExamen);
		 c.setDescription(description);
		 c.setImage(image.getBytes());
		 c.setPoste(poste);
		 return cs.add(c);
	 }
	 
	 
	 
	   @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Concours> deleteConcours(@PathVariable("id") Long id){
	    	cs.deleteById(id);
	    	return new ResponseEntity<>( HttpStatus.OK);
	    }
	 
	   
	   
	   @PutMapping("/update")
	    public ResponseEntity<Concours> updateConcours( @RequestBody Concours c){
			
			Concours upconcours=cs.update(c);
	    	return new ResponseEntity<>(upconcours, HttpStatus.OK);
		}
	 
	 

	 
	   @GetMapping("/{id}/candidats")
	    public List<CandidatInfo> getCandidatsByConcoursId(@PathVariable("id") Long concoursId) {
	        // Logic to retrieve candidats from candidatRepository based on concoursId
	        List<CandidatInfo> candidats = candidatinforep.findByConcoursId(concoursId);
	        // You can customize the query based on your entity and repository setup
	        // For example, if you have a Many-to-Many relationship between Concours and Candidat,
	        // you may need to use a @Query annotation to specify the JOIN and WHERE clauses
	        
	        // Return the list of candidats
	        return candidats;
	    }

	 
}
