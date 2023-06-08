package com.concours.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.concours.Model.Reclamation;
import com.concours.Repository.ReclamationRepository;
import com.concours.services.ReclamationService;



@CrossOrigin("*")
@RequestMapping("/api/reclamation")
@RestController
public class ReclamationController {

	
	@Autowired
	ReclamationService rs;
	
	
	@Autowired
	ReclamationRepository rr;
	
	
	
	
		@PostMapping("/add")
		 public ResponseEntity<Reclamation> ajouterReclamation(@RequestBody Reclamation r) {
			Reclamation reclamation=rs.add(r);
		    	return new ResponseEntity<>(reclamation, HttpStatus.CREATED);
		}
	
	
	
		@GetMapping("/all")
		public ResponseEntity<List<Reclamation>> getAllReclamation(){
			 List<Reclamation> r=rs.findAll();
			 return new ResponseEntity<>(r, HttpStatus.OK);
		}
	
	
	
	 
	   
	   
		@GetMapping("{id}")
		 public ResponseEntity<Reclamation> getReclamation(@PathVariable("id") Long id){
			Reclamation reclamation=rs.findById(id);
			 return new ResponseEntity<>(reclamation, HttpStatus.OK);
		 }
	
		
		
		
		 @DeleteMapping("/delete/{id}")
		    public ResponseEntity<Reclamation> deleteReclamation(@PathVariable("id") Long id){
		    	rs.deleteById(id);
		    	return new ResponseEntity<>( HttpStatus.OK);
		 }
	
	
}
