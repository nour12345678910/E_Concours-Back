package com.concours.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.concours.Model.CandidatInfo;


@Repository
public interface CandidatInfoRepository extends JpaRepository<CandidatInfo, Long> {
	  List<CandidatInfo> findByUserId(Long userId);
	  List<CandidatInfo> findByConcoursId(Long concoursId);
	  CandidatInfo findByUserIdAndConcoursId(Long userId, Long concoursId);
	  @Query("SELECT c FROM CandidatInfo c WHERE c.reussite = true")
	    List<CandidatInfo> findByReussiteTrue();


}

