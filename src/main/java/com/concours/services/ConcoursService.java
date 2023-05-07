package com.concours.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.Concours;


public interface ConcoursService {
	
	
	public List<Concours> findAll();
	public Concours getConcoursById(Long id);
	public void  deleteById(Long id);
	public Concours add(Concours c);
	public Concours update(Concours c);
    public Concours findById(Long id);
    Optional<Concours> findByid(Long id);
	public String saveImage(MultipartFile file) throws Exception;

	

}
