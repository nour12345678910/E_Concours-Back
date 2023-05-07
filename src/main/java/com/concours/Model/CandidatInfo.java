package com.concours.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "candidat_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    
    
    @Column(name = "etat")
    private Boolean etat;
    
    @Column(name = "reussite")
    private Boolean reussite;
    
    
    @Column(name = "concours_id")
    private Long concoursId;

    @Column(name = "sex")
    private String sex;

    @Column(name = "date_naissance")
    private Date dateNaissance;

    @Column(name = "place_naissnce")
    private String placeNaissnce;

    @Column(name = "statut_civil")
    private String statutCivil;

    @Column(name = "adress")
    private String adress;

    @Column(name = "ville")
    private String ville;

    @Column(name = "etat_ville")
    private String etatVille;

    @Column(name = "zip_code")
    private String zipCode;
    
    
    @Column(name = "score")
    private float score;
    

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diplome> diplomes;

	
	 public CandidatInfo() {
		 super();
	        this.diplomes = new ArrayList<>();
	    }

	public CandidatInfo(Long id, Long userId,boolean etat,boolean reussite,Long concoursId, String sex, Date dateNaissance, String placeNaissnce, String statutCivil,
			String adress, String ville, String etatVille, String zipCode, List<Diplome> diplomes,float score) {
		super();
		this.id = id;
		this.userId = userId;
		this.etat=etat;
		this.reussite=reussite;
		this.concoursId=concoursId;
		this.sex = sex;
		this.dateNaissance = dateNaissance;
		this.placeNaissnce = placeNaissnce;
		this.statutCivil = statutCivil;
		this.adress = adress;
		this.ville = ville;
		this.etatVille = etatVille;
		this.zipCode = zipCode;
		this.diplomes = diplomes;
		this.score=score;
	}

	@Override
	public String toString() {
		return "CandidatInfo [id=" + id + ", userId=" + userId + ", sex=" + sex + ", dateNaissance=" + dateNaissance
				+ ", placeNaissnce=" + placeNaissnce + ", statutCivil=" + statutCivil + ", adress=" + adress
				+ ", ville=" + ville + ", etatVille=" + etatVille + ", zipCode=" + zipCode + ", diplomes=" + diplomes
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getPlaceNaissnce() {
		return placeNaissnce;
	}

	public void setPlaceNaissnce(String placeNaissnce) {
		this.placeNaissnce = placeNaissnce;
	}

	public String getStatutCivil() {
		return statutCivil;
	}

	public void setStatutCivil(String statutCivil) {
		this.statutCivil = statutCivil;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getEtatVille() {
		return etatVille;
	}

	public void setEtatVille(String etatVille) {
		this.etatVille = etatVille;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	  public List<Diplome> getDiplomes() {
	        return this.diplomes;
	    }

	    public void setDiplomes(List<Diplome> diplomes) {
	        this.diplomes = diplomes;
	    }

		public Long getConcoursId() {
			return concoursId;
		}

		public void setConcoursId(Long concoursId) {
			this.concoursId = concoursId;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public Boolean getEtat() {
			return etat;
		}

		public void setEtat(Boolean etat) {
			this.etat = etat;
		}

		public Boolean getReussite() {
			return reussite;
		}

		public void setReussite(Boolean reussite) {
			this.reussite = reussite;
		}


	


    
	

}