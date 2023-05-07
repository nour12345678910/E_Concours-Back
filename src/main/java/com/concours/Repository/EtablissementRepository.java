package com.concours.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concours.Model.Etablissement;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
}