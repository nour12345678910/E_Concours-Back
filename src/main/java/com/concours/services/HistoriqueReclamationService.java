package com.concours.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concours.Model.ActionHistorique;
import com.concours.Model.Concours;
import com.concours.Model.HistoriqueReclamation;
import com.concours.Model.Reclamation;
import com.concours.Repository.ActionHistoriqueRepository;
import com.concours.Repository.ConcoursRepository;
import com.concours.Repository.HistoriqueReclamationRepository;
import com.concours.Repository.ReclamationRepository;


@Service
public class HistoriqueReclamationService {

	
private final HistoriqueReclamationRepository hrr;
    
	@Autowired
	ReclamationService rs;

	
	@Autowired
	ReclamationRepository rr;

    public HistoriqueReclamationService(HistoriqueReclamationRepository hrr) {
        this.hrr = hrr;
    }

    public void createActionLog(String action, Long reclamationId) {
        Optional<Reclamation> reclamationOptional = rr.findById(reclamationId);
        if (reclamationOptional.isPresent()) {
            Reclamation r = reclamationOptional.get();
            HistoriqueReclamation hr = new HistoriqueReclamation();
            hr.setDate(new Date());
            hr.setAction(action);
            hr.setReclamationId(reclamationId);
            hr.setReclamationName(r.getSujet());
            hr.setSender(r.getNom());
            hr.setEmailSender(r.getEmail());

            hrr.save(hr);
        }
    }
    
    
    public List<HistoriqueReclamation> getActionLogsReclamations() {
        return hrr.findAll();
      }
    
	public void deleteById(Long id) {
			 hrr.deleteById(id);
	}
	
    

	
	
}
