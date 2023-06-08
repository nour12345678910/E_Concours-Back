package com.concours.Controller;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.concours.Model.Etablissement;
import com.concours.Model.User;
import com.concours.Repository.EtablissementRepository;
import com.concours.Repository.UserRepository;
import com.concours.enums.ERole;
import com.concours.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.IOException;
import java.security.SecureRandom;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;





@CrossOrigin("*")
@RequestMapping("/api/user")
@RestController

public class UserController {
	
	
	@Autowired
	JavaMailSender mailSender;

	
	@Autowired
	UserService userService;
	
	
	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	private EtablissementRepository etablissementRepository;
	
	
	@Autowired
	private JavaMailSender emailSender;
	
	
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	
	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	

	
	@PostMapping("/signUp")
	public ResponseEntity<?> save(@RequestParam("cin") String cin, @RequestParam("nom") String nom,
	        @RequestParam("prenom") String prenom, @RequestParam("telephone") String telephone,
	        @RequestParam("motdepasse") String motdepasse, @RequestParam("email") String email,
	        @RequestParam("photo") MultipartFile photo, @RequestParam("role") ERole role) throws IOException {
	    try {
	        User u = new User();
	        u.setCin(cin);
	        u.setNom(nom);
	        u.setPrenom(prenom);
	        u.setTelephone(telephone);
	        u.setMotdepasse(motdepasse);
	        u.setEmail(email);
	        u.setRole(role);
	        u.setPhoto(photo.getBytes());
	        
	        
	        //vérifier l'existance du l'utilisateur à travers de son CIN 
	        if (userService.exist(u)) {
	            return ResponseEntity.badRequest().body("User already exists");
	        } else {

	        	
	        	//l'enregistrement de l'utilisateur avec un mot de passe crypté 
	            User savedUser = userService.createUser(u);

	            // Get l'etablissement
	            List<Etablissement> etablissements = etablissementRepository.findAll();

	            //l'etablissement présent 
	            StringBuilder sb = new StringBuilder();
	            for (Etablissement e : etablissements) {
	                sb.append(e.getNom());
	            }
	            String nomsEtablissements = sb.toString();

	            // envoyer un mail de confirmation de l'inscription à l'utilisateur 
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setTo(email);
	            helper.setSubject("إعلام بتسجيل الحساب في " + nomsEtablissements + "\r\n");
	            helper.setText("إلى السيد(ة) " + nom + " " + prenom + ",\n يعلم " + nomsEtablissements + " السيد(ة) " + nom + " " + prenom
	                    + " صاحب(ة) بطاقة التعريف عدد " + cin + " بإتمام عملية تسجيل الحساب بنجاح\r\n" + "\r\n " + "\r\n"
	                    + "\r\n" + "\r\n" + "");
	            mailSender.send(message);

	            return ResponseEntity.ok(savedUser);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	
	
	
	
	
	
	
	
	@PostMapping("/signIn")
	public ResponseEntity<?> login(String cin, String motdepasse) {
		
	    //chercher l'utilisateur qui a le CIN correspondante pour vérifier son présence 
	    User user = userRepository.findByCin(cin);
	    
	    if (user == null) {
			return ResponseEntity.badRequest().body("Invalid username or password");

	    }

	    // Valider le mot de passe 
	    if (!passwordEncoder.matches(motdepasse, user.getMotdepasse())) {
			return ResponseEntity.badRequest().body("Invalid username or password");
	    }

	    // Generer un  JWT token 
	    Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);
	    String token = Jwts.builder()
	            .setSubject(user.getId().toString())
	            .setIssuedAt(new Date())
	            .setExpiration(expirationDate)
	            .signWith(SignatureAlgorithm.HS512, jwtSecret)
	            .compact();

	    // Create a JSON response object
	    Map<String, Object> response = new HashMap<>();
	    response.put("token", token);
	    response.put("user", user);
	    return ResponseEntity.ok(user);
	    
	}	
	
	
	
	
	
	
	
	@PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
    	User upUser=userService.updateUser(user);
    	return new ResponseEntity<>(upUser, HttpStatus.OK);
    }


	
	
	
	
	
	
	
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable("id") Long id,
	        @RequestParam("cin") String cin, @RequestParam("nom") String nom,
	        @RequestParam("prenom") String prenom, @RequestParam("telephone") String telephone,
	        @RequestParam("motdepasse") String motdepasse, @RequestParam("email") String email,
	        @RequestParam("photo") MultipartFile photo, @RequestParam("role") ERole role) throws IOException {
	    User user = userService.get(id);
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String hashedPassword = encoder.encode(user.getMotdepasse());
	    user.setMotdepasse(hashedPassword);
	    user.setCin(cin);
	    user.setNom(nom);
	    user.setPrenom(prenom);
	    user.setTelephone(telephone);
	    user.setEmail(email);
	    user.setRole(role);
	    if (!photo.isEmpty()) {
	        user.setPhoto(photo.getBytes());
	    }
	    return userService.updateUser(user);
	}

	
	
	
	
	
	@GetMapping("/all")
	 public ResponseEntity<List<User>> getAllUsers(){
		 List<User> u=userService.findAll();
		 return new ResponseEntity<>(u, HttpStatus.OK);
	    }
	
	
	
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(userService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	

	
	
	

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
	    String email = request.get("email");

	    // Générer un nouveau mot de passe aléatoire
	    SecureRandom random = new SecureRandom();
	    byte[] bytes = new byte[4];
	    random.nextBytes(bytes);
	    String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	    String newPassword;
	    
	    // découpage de mot de passe aléatoire et obtenir les 8 premiers caractères
	    try {
	        newPassword = encoded.substring(0, 8);
	    } 
	    //Si la chaîne est trop courte, le mot de passe aléatoire complet est utilisé
	    catch (StringIndexOutOfBoundsException e) {
	        newPassword = encoded;
	    }

	    // Stocke le nouveau mot de passe dans la base de données
	    User user = userRepository.findByEmail(email);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }{
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
	        user.setMotdepasse(encoder.encode(newPassword));
	        userRepository.save(user);
	    }

	    // Envoie un e-mail contenant le nouveau mot de passe
	    try {
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo(email);
	        helper.setSubject("Réinitialisation du mot de passe");
	        helper.setText("Votre nouveau mot de passe est : " + newPassword);
	        emailSender.send(message);
	    } catch (MessagingException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
	    }

	    return ResponseEntity.ok("Password reset successful");
	}


	
	
	
	
	
}





















