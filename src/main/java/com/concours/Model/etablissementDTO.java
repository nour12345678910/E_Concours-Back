package com.concours.Model;


public class etablissementDTO {

	private Long id;
	private String nom;

	private String logo;
	private String telephone;
	private String numFix;
	private String adresse;
	private String email;
	private String imagefond;
	
	public etablissementDTO(Long id, String nom, String logo, String telephone, String numFix, String adresse,
			String email, String imagefond) {
		super();
		this.id = id;
		this.nom = nom;
		this.logo = logo;
		this.telephone = telephone;
		this.numFix = numFix;
		this.adresse = adresse;
		this.email = email;
		this.imagefond=imagefond;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNumFix() {
		return numFix;
	}
	public void setNumFix(String numFix) {
		this.numFix = numFix;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImagefond() {
		return imagefond;
	}
	public void setImagefond(String imagefond) {
		this.imagefond = imagefond;
	}
	
	
	
}
