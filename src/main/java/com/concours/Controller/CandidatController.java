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
import com.concours.Model.EmailRequest;
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
	
	
	
	
	
	
//passer la candidature
@PostMapping("/addCandidat")
	public ResponseEntity<CandidatInfo> ajouterCandidat(@RequestBody CandidatInfo candidat,
	        @RequestHeader("userId") Long userId, @RequestHeader("concoursId") Long concoursId)
	        throws MessagingException {

	    // vérifier si un enregistrement présent du mm utilisateur , il n'a pas le droit de participer 2 fois pour la mm concours 
	    CandidatInfo existingCandidat = candidatRepository.findByUserIdAndConcoursId(userId, concoursId);
	    if (existingCandidat != null) {
	        return ResponseEntity.badRequest().body(existingCandidat);
	    }

	    candidat.setUserId(userId);
	    candidat.setConcoursId(concoursId);

	    // Enregistrer le candidat dans la base de données
	    CandidatInfo savedCandidat = candidatRepository.save(candidat);

	    //Enregistrer chaque diplome du candidats dans la base de donnée aussi
	    for (Diplome diplome : candidat.getDiplomes()) {
	        diplome.setCandidat(candidat);
	        diplomeRepository.save(diplome);
	    }

	    //récupérer le concours qui a le concoursId correspondant 
	    Concours concours = concoursRepository.findById(concoursId).orElse(null);

	    // Get etablissement
	    List<Etablissement> etablissements = etablissementRepository.findAll();

	    // Create a string of etablissement present
	    StringBuilder sb = new StringBuilder();
	    for (Etablissement e : etablissements) {
	        sb.append(e.getNom());
	    }	    String nomsEtablissements = sb.toString();

	    //Récuperer l'utilisateur qui a le userId
	    User user = userRepository.findById(userId).orElse(null);

	    if (user != null) {
	        // Envoyer un mail au candidat qui lui informe que son candidature a été bien envoyée
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

	    
	    //retourner le candidat avec ses diplomes et http status created
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidat);
	}
	
	




	//envoyer un email d'acceptation au candidat pour passer l'examen du concours correspondant "concoursId"
	@PostMapping("/sendEmails/{concoursId}")
	public void sendEmailsToCandidats(@RequestBody EmailRequest emailRequest,
	       @PathVariable("concoursId") Long concoursId ) throws MessagingException {
	 //récuperer les candidats leurs emails depuis emailRequest 
	   List<CandidatInfo> candidats = emailRequest.getCandidats();
	   List<User> users = emailRequest.getUsers();
	   List<String> candidateEmails = emailRequest.getCandidateEmails();
	     
	   //récupérer l'etablissement 
	List<Etablissement> etablissements = etablissementRepository.findAll();
	StringBuilder sb = new StringBuilder();
	   for (Etablissement e : etablissements) {
	       sb.append(e.getNom());
	   }
	   
	   String nomsEtablissements = sb.toString();
	   
	   //récuperer le concours correspondant
	  Optional<Concours> optionalConcours = concoursRepository.findById(concoursId);
	  Concours concours = optionalConcours.get();
	   
	  //envoie mail aux candidats 
	  for (String email : candidateEmails) {
	         for (CandidatInfo candidat : candidats) {
	             for (User user : users) {
	                 if (candidat.getUserId().equals(user.getId()) && user.getEmail().equals(email)) {
	                     // Send email logic
	                     MimeMessage message = mailSender.createMimeMessage();
	                     MimeMessageHelper helper = new MimeMessageHelper(message, true);
	                     try {
	                         helper.setTo(email);
	                         helper.setSubject("طلب ترشح مقبول " + " - " + nomsEtablissements);
	                         helper.setText(",\n يعلم " + nomsEtablissements + " السيد(ة)  " + user.getNom() + "  " + user.getPrenom() + " صاحب(ة) بطاقة التعريف عدد   "
	                                 + user.getCin() + "   بقبول مطلب الترشح في " + concours.getPoste());

	                         mailSender.send(message);
	                     } catch (MessagingException e) {
	                                   e.printStackTrace();
	                     }

	                 
	                     candidat.setEtat(true);
	                     candidatRepository.save(candidat);
	                     
	                 } else {
	                 for (CandidatInfo c : candidats) {
	                  if(c.getEtat()==null) {
	                   c.setEtat(false);
	                   candidatRepository.save(c);}
	                 }}
	                 
	                 
	             }
	         }}
	}



	
	
	
	
	
	//compter les sexes des candidats participés dans l'application
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



	
	
	//calculer les ages des candidats participés dans l'application
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
	
	
	



	
	

	@GetMapping("/candidats/{id}")
	public ResponseEntity<CandidatInfo> getCandidat(@PathVariable Long id) {
	    // trouver le candidat dans la BD 
	    Optional<CandidatInfo> optionalCandidat = candidatRepository.findById(id);
	    if (!optionalCandidat.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    CandidatInfo candidat = optionalCandidat.get();

	    // Récupérer les diplomes du candidat
	    List<Diplome> diplomes = diplomeRepository.findByCandidat(candidat);

	    candidat.setDiplomes(diplomes);
	    return ResponseEntity.ok(candidat);
	}

	
	
	
	
	@GetMapping("/all")
	 public ResponseEntity<List<CandidatInfo>> getAllCandidatInfo(){
		 List<CandidatInfo> c=cs.findAll();
		 return new ResponseEntity<>(c, HttpStatus.OK);
	    }
	
	
	

	
	
	
	 @GetMapping("/{id}")
	 public ResponseEntity<CandidatInfo> getCandidatInfoById(@PathVariable("id") Long id){
		 CandidatInfo c=cs.findById(id);
		 return new ResponseEntity<>(c, HttpStatus.OK);
	 }
	 
	 
	 
		   
	   
	   
	   
	
	   //get les diplomes du candidat correspondant à l'id
	   @GetMapping("/{id}/diplome")
	    public List<Diplome> getDiplomes(@PathVariable("id") Long id) {
	       CandidatInfo candidat=cs.findById(id); 
		   List<Diplome> diplomes = diplomeRepository.findByCandidat(candidat);
	        return diplomes;
	    }
	 
	   
	   
		@GetMapping("/diplomes")
		 public ResponseEntity<List<Diplome>> getAllDiplome(){
			 List<Diplome> d=diplomeRepository.findAll();
			 return new ResponseEntity<>(d, HttpStatus.OK);
		  }
	   
	   
	   
	   
	   
	 //update l'etat du candidat soit accpté ou refusé pour passer l'examen
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
	   
	   
	    
	   
	   //liste des candidat réussis à l'examen
	   @GetMapping("/successful-candidates")
	    public List<CandidatInfo> getSuccessfulCandidates() {
	        return candidatRepository.findByReussiteTrue();
	    } 
	   
	   
	   
	    
	   
	   
	   //envoeyer mail au candidat pour lui informer qui'il est soit accepter ou refuser dans l'examen du concours
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
