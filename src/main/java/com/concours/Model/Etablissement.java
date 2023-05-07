package com.concours.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table (name="ETABLISSEMENT")
public class Etablissement {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String nom;
	@Lob
	private byte[] logo;
	private String telephone;
	private String numFix;
	private String adresse;
	private String email;
	@Lob
	private byte[] imagefond;
	
	public Etablissement(Long id, String nom,  byte[]  logo, String telephone, String numFix, String adresse,
			String email,byte[] imagefond) {
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
	public Etablissement() {
		super();
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
	
	
	
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
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
	
	
	
	
	public byte[] getImagefond() {
		return imagefond;
	}
	public void setImagefond(byte[] imagefond) {
		this.imagefond = imagefond;
	}
	@Override
	public String toString() {
		return "Etablissement [id=" + id + ", nom=" + nom + ", logo=" + logo + ", telephone=" + telephone + ", numFix="
				+ numFix + ", adresse=" + adresse + ", email=" + email + "]";
	}
	
	
	
	
	
	

}
