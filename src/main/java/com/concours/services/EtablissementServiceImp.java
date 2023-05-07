package com.concours.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concours.Model.Etablissement;
import com.concours.Repository.EtablissementRepository;

@Service
public class EtablissementServiceImp implements EtablissementService{
	
	
	private EtablissementRepository er;
	
	@Autowired
	public void setEtablissementRepository(EtablissementRepository er){
        this.er=er;
    }
	
	
	@Override
	public List<Etablissement> find() {
		return er.findAll();
	}
	
	
	
	
	@Transactional
	public Etablissement add(Etablissement e) {
		return er.save(e);
	}

	@Override
	public Etablissement update(Etablissement e) {
		
		return er.save(e);
	}
	
	@Override
	public Etablissement findById(Long id) {
		return er.findById(id).orElse(null);

	}
	
	@Override
	public Etablissement save(Etablissement etablissement) {
		return er.save(etablissement);
	}
	

}
