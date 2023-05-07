package com.concours.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
@Entity
@Table(name = "diplome")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "diplome")
    private String diplome;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "faculte")
    private String faculte;

    @Column(name = "annee_obtention")
    private String anneeObtention;

    @Column(name = "moyenne_1")
    private String moyenne1;

    @Column(name = "moyenne_2")
    private String moyenne2;

    @Column(name = "moyenne_3")
    private String moyenne3;

    @Column(name = "moyenne_bac")
    private String moyenneBac;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id")
    private CandidatInfo candidat;

	public Diplome() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Diplome(Long id, String diplome, String specialite, String faculte, String anneeObtention, String moyenne1,
			String moyenne2, String moyenne3, String moyenneBac, CandidatInfo candidat) {
		super();
		this.id = id;
		this.diplome = diplome;
		this.specialite = specialite;
		this.faculte = faculte;
		this.anneeObtention = anneeObtention;
		this.moyenne1 = moyenne1;
		this.moyenne2 = moyenne2;
		this.moyenne3 = moyenne3;
		this.moyenneBac = moyenneBac;
		this.candidat = candidat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getFaculte() {
		return faculte;
	}

	public void setFaculte(String faculte) {
		this.faculte = faculte;
	}

	public String getAnneeObtention() {
		return anneeObtention;
	}

	public void setAnneeObtention(String anneeObtention) {
		this.anneeObtention = anneeObtention;
	}

	public String getMoyenne1() {
		return moyenne1;
	}

	public void setMoyenne1(String moyenne1) {
		this.moyenne1 = moyenne1;
	}

	public String getMoyenne2() {
		return moyenne2;
	}

	public void setMoyenne2(String moyenne2) {
		this.moyenne2 = moyenne2;
	}

	public String getMoyenne3() {
		return moyenne3;
	}

	public void setMoyenne3(String moyenne3) {
		this.moyenne3 = moyenne3;
	}

	public String getMoyenneBac() {
		return moyenneBac;
	}

	public void setMoyenneBac(String moyenneBac) {
		this.moyenneBac = moyenneBac;
	}

	public CandidatInfo getCandidat() {
		return candidat;
	}

	public void setCandidat(CandidatInfo candidat) {
		this.candidat = candidat;
	}

	@Override
	public String toString() {
		return "Diplome [id=" + id + ", diplome=" + diplome + ", specialite=" + specialite + ", faculte=" + faculte
				+ ", anneeObtention=" + anneeObtention + ", moyenne1=" + moyenne1 + ", moyenne2=" + moyenne2
				+ ", moyenne3=" + moyenne3 + ", moyenneBac=" + moyenneBac + ", candidat=" + candidat + "]";
	}
    
    
    
}
