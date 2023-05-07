package com.concours.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

import com.concours.enums.ERole;
import com.concours.Model.User;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private Long id;
	
	@Column(name="cin")
	private String cin;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="prenom")
	private String prenom;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="motdepasse")
	private String motdepasse;
	

    @Column(name = "email", unique = true)
    private String email;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private ERole role;
	
	
	@Lob 
	private byte[] photo;
	
	
	


	public User(Long id, String cin, String nom, String prenom, String telephone, String motdepasse, String email,
			ERole role, byte[] photo) {
		super();
		this.id = id;
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.motdepasse = motdepasse;
		this.email = email;
		this.role = role;
		this.photo = photo;
	}



	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone
				+ ", motdepasse=" + motdepasse + ", email=" + email + ", role=" + role + ", photo=" +photo
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getFullName() {
		return nom;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}



	public byte[] getPhoto() {
		return photo;
	}



	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}



	



	

	

	
	
	
	
	
	
	
}








































