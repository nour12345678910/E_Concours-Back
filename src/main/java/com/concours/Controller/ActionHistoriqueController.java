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

import com.concours.Model.ActionHistorique;
import com.concours.Model.HistoriqueReclamation;
import com.concours.services.ActionHistoriqueService;
import com.concours.services.HistoriqueReclamationService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ActionHistoriqueController {
	
	
	@Autowired
	HistoriqueReclamationService hrs ;
	
	
    private final ActionHistoriqueService actionLogService;

     //crééer instance du service 
    public ActionHistoriqueController(ActionHistoriqueService actionLogService) {
        this.actionLogService = actionLogService;
    }

  
    
	
    
    @PostMapping("/action")
    public ResponseEntity<?> createActionLog(@RequestBody ActionHistorique request) {
        actionLogService.createActionLog(request.getAction(), request.getConcoursId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historiquesConcours")
    public List<ActionHistorique> getActionLogs() {
        return actionLogService.getActionLogs();
    }
    
    
    
	 @DeleteMapping("/deleteHistoriqueConcours/{id}")
	    public ResponseEntity<ActionHistorique> deleteHistoriqueConcours(@PathVariable("id") Long id){
		 actionLogService.deleteById(id);
	    	return new ResponseEntity<>( HttpStatus.OK);
	    }
	 
	 
	 
    
    //pour réclamation historique 
    @PostMapping("/actionReclamation")
    public ResponseEntity<?> createActionLogReclamation(@RequestBody HistoriqueReclamation request) {
        hrs.createActionLog(request.getAction(), request.getReclamationId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historiquesReclamations")
    public List<HistoriqueReclamation> getActionLogsReclamation() {
        return hrs.getActionLogsReclamations();
    }
    
    
	
	 @DeleteMapping("/deleteHistoriqueReclamation/{id}")
	    public ResponseEntity<HistoriqueReclamation> deleteHistoriqueReclamation(@PathVariable("id") Long id){
		 hrs.deleteById(id);
	    	return new ResponseEntity<>( HttpStatus.OK);
	    }
	 


    
    
    
    
}
