package com.concours.Controller;


import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.concours.Model.CandidatInfo;
import com.concours.Model.Concours;
import com.concours.Model.Diplome;
import com.concours.Model.Etablissement;
import com.concours.Model.User;
import com.concours.Repository.CandidatInfoRepository;
import com.concours.Repository.ConcoursRepository;
import com.concours.Repository.DiplomeRepository;
import com.concours.Repository.EtablissementRepository;
import com.concours.Repository.UserRepository;
import com.concours.services.CandidatService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")

public class CandidatController {
	@Autowired
	private ConcoursRepository concoursRepository;
	
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	CandidatService cs ;
	
	@Autowired
	private EtablissementRepository etablissementRepository;
	
	@Autowired
	  private CandidatInfoRepository candidatRepository;

	@Autowired
	  private DiplomeRepository diplomeRepository;
	
	@Autowired
	  private UserRepository userRepository;
	
	

	@GetMapping("/candidates/sexes")
	public Map<String, Long> getCandidateSexes() {
	    List<CandidatInfo> candidates = candidatRepository.findAll();
	    Map<String, Long> sexesCount = new HashMap<>();
	    for (CandidatInfo candidate : candidates) {
	        String sexe = candidate.getSex();
	        Long count = sexesCount.getOrDefault(sexe, 0L);
	        sexesCount.put(sexe, count + 1);
	    }
	    return sexesCount;
	}



	
	@GetMapping("/candidates/ages")
	public List<Integer> getCandidateAges() {
	    List<CandidatInfo> candidates = candidatRepository.findAll();
	    LocalDate currentDate = LocalDate.now();
	    List<Integer> ages = new ArrayList<>();
	    for (CandidatInfo candidate : candidates) {
	        LocalDate birthDate = candidate.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        int age = Period.between(birthDate, currentDate).getYears();
	        ages.add(age);
	    }
	    return ages;
	}
	
	
	@PostMapping("/addCandidat")
	public ResponseEntity<CandidatInfo> ajouterCandidat(@RequestBody CandidatInfo candidat, @RequestHeader("userId") Long userId, @RequestHeader("concoursId") Long concoursId) throws MessagingException {
	    

	    // Check if a record already exists for the same user and contest combination
	    CandidatInfo existingCandidat = candidatRepository.findByUserIdAndConcoursId(userId, concoursId);
	    if (existingCandidat != null) {
	        // Return a response indicating that the candidate has already submitted their candidacy and cannot submit again
	        return ResponseEntity.badRequest().body(existingCandidat);
	    }
	    
	    candidat.setUserId(userId);
	    candidat.setConcoursId(concoursId);
	    
	    // Calculate the candidate's score
	    float moyenneBac = 0;
	    float moyenne1 = 0;
	    float moyenne2 = 0;
	    float moyenne3 = 0;
	    float score = 0;
	    
	    // Calculate the averages for each diploma
	    for (Diplome diplome : candidat.getDiplomes()) {
	        moyenneBac += Float.parseFloat(diplome.getMoyenneBac());
	        moyenne1 += Float.parseFloat(diplome.getMoyenne1());
	        moyenne2 += Float.parseFloat(diplome.getMoyenne2());
	        moyenne3 += Float.parseFloat(diplome.getMoyenne3());
	    }
	    int numDiplomes = candidat.getDiplomes().size();
	    moyenneBac /= numDiplomes;
	    moyenne1 /= numDiplomes;
	    moyenne2 /= numDiplomes;
	    moyenne3 /= numDiplomes;
	    
	    // Calculate the score using the formula
	    score = ((moyenneBac * 3) + ((moyenne1 + moyenne2 + moyenne3) * 2)) / 5;
	    
	    candidat.setScore(score);
	    
	    // Save the candidate in the database
	    CandidatInfo savedCandidat = candidatRepository.save(candidat);

	    // Save each diploma of the candidate in the database
	    for (Diplome diplome : candidat.getDiplomes()) {
	        diplome.setCandidat(candidat);
	        diplomeRepository.save(diplome);
	    }

	    // Retrieve the concours object associated with the concoursId
	    Concours concours = concoursRepository.findById(concoursId).orElse(null);

	    // Get etablissement
	    List<Etablissement> etablissements = etablissementRepository.findAll();

	    // Create a string of etablissement presents
	    StringBuilder sb = new StringBuilder();
	    for (Etablissement e : etablissements) {
	        sb.append(e.getNom());
	    }
	    String nomsEtablissements = sb.toString();

	    // Retrieve the user object associated with the userId
	    User user = userRepository.findById(userId).orElse(null);

	    if (user != null) {
	        // Send an email to the registered user
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(user.getEmail());
	        if (concours != null) {
	            helper.setSubject("إعلام بتسجيل مطلب الترشح في " + concours.getPoste() + " - " + nomsEtablissements);
	            helper.setText("إلى السيد(ة) "  + user.getNom() + "  "+ user.getPrenom()+ ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   بتسجيل مطلب الترشح للمناظرة بنجاح");
	        } else {
	            helper.setSubject("إعلام بتسجيل مطلب الترشح في " + nomsEtablissements);
	            helper.setText("إلى السيد(ة) "  + user.getNom() + "  "+ user.getPrenom()+ ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   بتسجيل مطلب الترشح للمناظرة بنجاح  ");
	        }
	        mailSender.send(message);
	    }

	    // Return the added candidate with their diplomas and HTTP status CREATED
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidat);
	}

	
	
	

	@GetMapping("/candidats/{id}")
	public ResponseEntity<CandidatInfo> getCandidat(@PathVariable Long id) {
	    // Find the candidate in the database
	    Optional<CandidatInfo> optionalCandidat = candidatRepository.findById(id);
	    if (!optionalCandidat.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    CandidatInfo candidat = optionalCandidat.get();

	    // Retrieve the diplomas of the candidate
	    List<Diplome> diplomes = diplomeRepository.findByCandidat(candidat);

	    // Set the diplomas of the candidate and return the candidate with HTTP status OK
	    candidat.setDiplomes(diplomes);
	    return ResponseEntity.ok(candidat);
	}

	
	
	@PostMapping("/candidat")
	public CandidatInfo saveCandidats(@RequestBody CandidatInfo candidat, @RequestHeader("userId") Long userId) {
	    candidat.setUserId(userId);
	    return candidatRepository.save(candidat);
	}
	
	
	

	
	@GetMapping("/all")
	 public ResponseEntity<List<CandidatInfo>> getAllCandidatInfo(){
		 List<CandidatInfo> c=cs.findAll();
		 return new ResponseEntity<>(c, HttpStatus.OK);
	    }
	
	@GetMapping("/diplomes")
	 public ResponseEntity<List<Diplome>> getAllDiplome(){
		 List<Diplome> d=diplomeRepository.findAll();
		 return new ResponseEntity<>(d, HttpStatus.OK);
	    }

	
	 @GetMapping("/{id}")
	 public ResponseEntity<CandidatInfo> getCandidatInfoById(@PathVariable("id") Long id){
		 CandidatInfo c=cs.findById(id);
		 return new ResponseEntity<>(c, HttpStatus.OK);
	    }
	 
	 
	 @PostMapping("/add")
	 public ResponseEntity<CandidatInfo> ajouterCandidatInfo(@RequestBody CandidatInfo c) {
		 CandidatInfo CandidatInfo=cs.add(c);
	    	return new ResponseEntity<>(CandidatInfo, HttpStatus.CREATED);
		}
	 
	 
	   @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Concours> deleteConcours(@PathVariable("id") Long id){
	    	cs.deleteById(id);
	    	return new ResponseEntity<>( HttpStatus.OK);
	    }
	 
	
	   
	   @GetMapping("/{id}/diplome")
	    public List<Diplome> getDiplomes(@PathVariable("id") Long id) {
	       CandidatInfo candidat=cs.findById(id); 
		   List<Diplome> diplomes = diplomeRepository.findByCandidat(candidat);
	        // You can customize the query based on your entity and repository setup
	        // For example, if you have a Many-to-Many relationship between Concours and Candidat,
	        // you may need to use a @Query annotation to specify the JOIN and WHERE clauses
	        
	        // Return the list of candidats
	        return diplomes;
	    }
	 
	 
	   @PutMapping("/{id}")
	   public ResponseEntity<CandidatInfo> updateCandidatEtat(@PathVariable Long id, @RequestBody CandidatInfo candidatInfo) {
	       Optional<CandidatInfo> optionalCandidat = candidatRepository.findById(id);
	       if (!optionalCandidat.isPresent()) {
	           return ResponseEntity.notFound().build();
	       }
	       CandidatInfo candidat = optionalCandidat.get();
	       candidat.setEtat(candidatInfo.getEtat());
	       candidatRepository.save(candidat);
	       
	       
	    // Get etablissement
		    List<Etablissement> etablissements = etablissementRepository.findAll();

		    // Create a string of etablissement presents
		    StringBuilder sb = new StringBuilder();
		    for (Etablissement e : etablissements) {
		        sb.append(e.getNom());
		    }
		    String nomsEtablissements = sb.toString();
	       

	       // Get the user associated with the candidat
	       Optional<User> optionalUser = userRepository.findById(candidat.getUserId());
	       if (!optionalUser.isPresent()) {
	           return ResponseEntity.notFound().build();
	       }
	       User user = optionalUser.get();
	       
	       
	     //Get the concours associated with the candidat
	   		 Optional<Concours> optionalConcours = concoursRepository.findById(candidat.getConcoursId());
	   		 if (!optionalConcours.isPresent()) {
	       		 return ResponseEntity.notFound().build();
	   			 }
	   		 Concours concours = optionalConcours.get();
	       
	       

	   		String subject = "";
	   		String message = "";

	   		if (candidat.getEtat()) {
	   		    subject = "طلب ترشح مقبول  " + concours.getPoste() + " - " + nomsEtablissements;
	   		    message = "إلى السيد(ة)  " + user.getNom() + " " + user.getPrenom() + ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   بقبول مطلب الترشح";
	   		} else {
	   		    subject = "طلب ترشح  مرفوض" + concours.getPoste() + " - " + nomsEtablissements;
	   		    message = "إلى السيد(ة)  " + user.getNom() + " " + user.getPrenom() + ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   للأسف برفض مطلب الترشح";
	   		}

	   		sendEmail(user.getEmail(), subject, message);

		       return ResponseEntity.ok(candidat);
		   }

	   private void sendEmail(String toEmail, String subject, String message) {
	       SimpleMailMessage mailMessage = new SimpleMailMessage();
	       mailMessage.setTo(toEmail);
	       mailMessage.setSubject(subject);
	       mailMessage.setText(message);
	       mailSender.send(mailMessage);
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   @GetMapping("/successful-candidates")
	    public List<CandidatInfo> getSuccessfulCandidates() {
	        return candidatRepository.findByReussiteTrue();
	    } 
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   @PutMapping("reussite/{id}")
	   public ResponseEntity<CandidatInfo> reussiteCandidat(@PathVariable Long id, @RequestBody CandidatInfo candidatInfo) {
	       Optional<CandidatInfo> optionalCandidat = candidatRepository.findById(id);
	       if (!optionalCandidat.isPresent()) {
	           return ResponseEntity.notFound().build();
	       }
	       CandidatInfo candidat = optionalCandidat.get();
	       candidat.setReussite(candidatInfo.getReussite());
	       candidatRepository.save(candidat);
	       
	       
	    // Get etablissement
		    List<Etablissement> etablissements = etablissementRepository.findAll();

		    // Create a string of etablissement presents
		    StringBuilder sb = new StringBuilder();
		    for (Etablissement e : etablissements) {
		        sb.append(e.getNom());
		    }
		    String nomsEtablissements = sb.toString();
	       

	       // Get the user associated with the candidat
	       Optional<User> optionalUser = userRepository.findById(candidat.getUserId());
	       if (!optionalUser.isPresent()) {
	           return ResponseEntity.notFound().build();
	       }
	       User user = optionalUser.get();
	       
	       
	     //Get the concours associated with the candidat
	   		 Optional<Concours> optionalConcours = concoursRepository.findById(candidat.getConcoursId());
	   		 if (!optionalConcours.isPresent()) {
	       		 return ResponseEntity.notFound().build();
	   			 }
	   		 Concours concours = optionalConcours.get();
	       
	       

	   		String subjectReussite = "";
	   		String messageReussite = "";

	   		if (candidat.getReussite()) {
	   			subjectReussite = "اانجاح في إجتياز المناظرة في وظيفة  " + concours.getPoste() + " - " + nomsEtablissements;
	   			messageReussite = "إلى السيد(ة)  " + user.getNom() + " " + user.getPrenom() + ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   بنجاحه في إجتياز المناظرة";
	   		} else {
	   			subjectReussite = "الفشل في إجتياز المناظرة في وظيفة  " + concours.getPoste() + " - " + nomsEtablissements;
	   			messageReussite = "إلى السيد(ة)  " + user.getNom() + " " + user.getPrenom() + ",\n يعلم "+nomsEtablissements+" السيد(ة)  " +user.getNom() + "  "+ user.getPrenom()+ " صاحب(ة) بطاقة التعريف عدد   "+user.getCin()+"   فشله للأسف في إجتياز المناظرة";
	   		}

	   		sendEmailReussite(user.getEmail(), subjectReussite, messageReussite);

		       return ResponseEntity.ok(candidat);
	   }

	   private void sendEmailReussite(String toEmailCandidatReussit, String subjectReussite, String messageReussite) {
		    SimpleMailMessage mailMessage = new SimpleMailMessage();
		    mailMessage.setTo(toEmailCandidatReussit);
		    mailMessage.setSubject(subjectReussite);
		    mailMessage.setText(messageReussite);
		    mailSender.send(mailMessage);
		}

	   
	   
	   
	   
	   
	   
	   
	   


	 
}
