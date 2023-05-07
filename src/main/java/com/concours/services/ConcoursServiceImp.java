package com.concours.services;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.Concours;
import com.concours.Repository.ConcoursRepository;

@Service
public class ConcoursServiceImp implements ConcoursService {
	
	
	@Autowired
	ConcoursRepository cr;
	
	@Override
	 public Concours findById(Long id) {
	        Optional<Concours> concours = cr.findById(id);
	        if (concours.isPresent()) {
	            return concours.get();
	        } else {
	            throw new RuntimeException("Concours not found with ID: " + id);
	        }
	    }

	@Override
	public Optional<Concours> findByid(Long id) {
	    return cr.findById(id);
	}
	
	@Override
	public List<Concours> findAll() {
		return cr.findAll();
	}

	@Override
	public Concours getConcoursById(Long id) {
		return cr.findById(id).orElse(null);

	}
	
	
	
	


	@Override
	public void deleteById(Long id) {
		cr.deleteById(id);
	}

	@Override
	public Concours add(Concours c) {
		return cr.save(c);
	}

	@Override
	public Concours update(Concours c) {
		return cr.save(c);
	}

	//@Override
	//public boolean exist(Concours c) {
		//Optional<Concours> concours = cr.findFirstByUser(c.getUser());
		//return concours.isPresent();
	//}

	public String saveImage(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String generatedFileName = UUID.randomUUID().toString() + fileExtension;
        File imageFile = new File("uploads/" + generatedFileName);
        file.transferTo(imageFile);
        return "/uploads/" + generatedFileName;
    }

	
}
