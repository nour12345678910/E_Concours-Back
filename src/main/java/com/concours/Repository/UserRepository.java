package com.concours.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concours.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findFirstByCin(String cin);//inscription
	User findFirstByCinAndMotdepasse(String cin, String motdepasse);//login
	User findById(long id);
    User findByEmail(String email);
	User findByCin(String cin);



	

}

