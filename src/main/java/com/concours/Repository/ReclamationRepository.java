package com.concours.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concours.Model.Reclamation;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
	 
}
