package com.concours.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concours.Model.CandidatInfo;
import com.concours.Model.Diplome;

public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    List<Diplome> findByCandidat(CandidatInfo candidat);

}
