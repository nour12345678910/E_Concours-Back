package com.concours.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concours.Model.Reclamation;
import com.concours.Repository.ReclamationRepository;

@Service
public class ReclamationServiceImp implements ReclamationService {
	
	@Autowired
	ReclamationRepository rr;

	@Override
	public List<Reclamation> findAll() {
		return rr.findAll();
	}

	@Override
	public Reclamation add(Reclamation r) {
		return rr.save(r);
	}

	@Override
	public Reclamation findById(Long id) {
		return rr.findById(id).orElse(null);
	}

	
	
	@Override
	public void deleteById(Long id) {
			 rr.deleteById(id);
	}

}
