package com.concours.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concours.Model.Concours;

@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {
	//Optional<Concours> findFirstByUser( User user);

}
