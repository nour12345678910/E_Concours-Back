package com.concours.services;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concours.Model.User;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CandidatInfoRepository cInfoRepo;
	
	
	
    @Override
    public User login(String cin, String motdepasse) {
        // Retrieve the user entity based on the CIN from the repository
        User user = userRepository.findByCin(cin);

        // Check if the retrieved user entity is not null and the password matches
        if (user != null && BCrypt.checkpw(motdepasse, user.getMotdepasse())) {
            return user;
        } else {
            return null;
        }
    }
	
	
	
	

	//@Override
	//public User createUser(User u) {
		//u.setRole(ERole.CANDIDAT);
		//u = userRepository.save(u);
		//return u;
//	}
	@Override
	public User createUser(User u) {
		String hashedPassword=BCrypt.hashpw(u.getMotdepasse(), BCrypt.gensalt());
	    u.setMotdepasse(hashedPassword);
		//u.setRole(ERole.CANDIDAT);
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
