package com.concours.services;

import java.util.List;

import com.concours.Model.Reclamation;


public interface ReclamationService {
	
	
	public List<Reclamation> findAll();
	public Reclamation add(Reclamation r);
	public Reclamation findById(Long id);
	public void  deleteById(Long id);

}
