package com.concours.services;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.CandidatInfo;
import com.concours.Model.Diplome;
import com.concours.Model.User;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.DiplomeRepository;

@Service
public class CandidatServiceImp implements CandidatService{
	
	
	@Autowired
	CandidatInfoRepository cr;
	
	@Autowired
	DiplomeRepository dr;

	@Override
	public List<CandidatInfo> findAll() {
		return cr.findAll();
	}

	@Override
	public CandidatInfo findById(Long id) {
		return cr.findById(id).orElse(null);

	}

	@Override
	public void deleteById(Long id) {
		cr.deleteById(id);
		
	}
User user;
	@Override
	public CandidatInfo add(CandidatInfo c) {
	    c.setId(user.getId());

		return cr.save(c);

	}

	@Override
	public CandidatInfo update(CandidatInfo c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CandidatInfo saveCandidat(CandidatInfo c, MultipartFile image) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	  public Diplome createDiplome(Long candidatId, Diplome diplome) {
	        CandidatInfo candidat = cr.findById(candidatId).orElseThrow(EntityNotFoundException::new);
	        diplome.setCandidat(candidat);
	        return dr.save(diplome);
	    }

}
