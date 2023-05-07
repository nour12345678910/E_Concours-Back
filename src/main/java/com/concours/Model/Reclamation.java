package com.concours.Model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class Reclamation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "nom")
    private String nom;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "sujet")
    private String sujet;
	
	@Column(name = "message")
    private String message;
	
	
	
	  @OneToOne(mappedBy = "reclamation", cascade = CascadeType.ALL)
	    private Response response;
	

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Reclamation [id=" + id + ", nom=" + nom + ", email=" + email + ", sujet=" + sujet + ", message="
				+ message + "]";
	}

	public Reclamation(Long id, String nom, String email, String sujet, String message) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.sujet = sujet;
		this.message = message;
	}

	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
	

}
