package com.concours.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.concours.Model.User;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
	

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CandidatInfoRepository cInfoRepo;
	

	

    @Override
    public User createUser(User u) {
        String hashedPassword = BCrypt.hashpw(u.getMotdepasse(), BCrypt.gensalt());
        u.setMotdepasse(hashedPassword);
        u = userRepository.save(u);
        return u;
    }
 

	
	@Override
	public User get(Long id) {
		Optional<User> userOp = userRepository.findById(id);
		if (userOp.isPresent())
			return userOp.get();

		return null;
	}

	@Override
	public boolean exist(User u) {
		Optional<User> user = userRepository.findFirstByCin(u.getCin());
		return user.isPresent();
	}



	
	
	
	@Override
	public User updateUser(User user) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String hashedPassword = encoder.encode(user.getMotdepasse());
	    user.setMotdepasse(hashedPassword);

	    // Retrieve the user from the database
	    User existingUser = userRepository.findById(user.getId()).orElse(null);

	    if (existingUser == null) {
	        // User not found
	        return null;
	    }

	    // Update the user's information
	    existingUser.setNom(user.getNom());
	    existingUser.setPrenom(user.getPrenom());
	    existingUser.setTelephone(user.getTelephone());
	    existingUser.setEmail(user.getEmail());
	    existingUser.setMotdepasse(user.getMotdepasse());
	    existingUser.setCin(user.getCin());


	    return userRepository.save(existingUser);
	}






	@Override
	public List<User> findAll() {
		return userRepository.findAll();

	}







  
	
	
}
