package com.concours.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concours.Model.ActionHistorique;
import com.concours.Model.Concours;
import com.concours.Repository.ActionHistoriqueRepository;
import com.concours.Repository.ConcoursRepository;
@Service
public class ActionHistoriqueService {

	
    private final ActionHistoriqueRepository actionLogRepository;
    
	@Autowired
	ConcoursService cs;
	
	
	@Autowired
	ConcoursRepository cr;
	

    public ActionHistoriqueService(ActionHistoriqueRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    
    public void createActionLog(String action, Long concoursId) {
        Optional<Concours> concoursOptional = cr.findById(concoursId);
        if (concoursOptional.isPresent()) {
            Concours concours = concoursOptional.get();
            ActionHistorique actionLog = new ActionHistorique();
            actionLog.setDate(new Date());
            actionLog.setAction(action);
            actionLog.setConcoursId(concoursId);
            actionLog.setConcoursName(concours.getPoste());
            actionLogRepository.save(actionLog);
        }
    }
    
    
    public List<ActionHistorique> getActionLogs() {
        return actionLogRepository.findAll();
      }
	
    public void deleteById(Long id) {
    	actionLogRepository.deleteById(id);
}

	
}
