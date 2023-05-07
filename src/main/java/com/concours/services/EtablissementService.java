package com.concours.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concours.Model.Etablissement;

@Service
public interface EtablissementService {
	
	public Etablissement add(Etablissement e);
	public Etablissement update(Etablissement e); 
	public List<Etablissement> find();
	public Etablissement findById(Long id);
	public Etablissement save(Etablissement etablissement);




}
