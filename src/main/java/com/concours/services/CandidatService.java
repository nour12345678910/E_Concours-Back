package com.concours.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.concours.Model.CandidatInfo;


public interface CandidatService {
	public List<CandidatInfo> findAll();
	public CandidatInfo findById(Long id);
	public void  deleteById(Long id);
	public CandidatInfo add(CandidatInfo c);
	public CandidatInfo update(CandidatInfo c);
	public CandidatInfo saveCandidat(CandidatInfo c,MultipartFile image ) throws IOException;
}
