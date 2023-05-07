package com.concours.Model;

import java.util.Date;

public class ConcoursDTO {
    
    private Long id;
    private String poste;
    private String imageData;
    private String description;
    private Date dateExamen;
    private Date dateDelais;

    public ConcoursDTO() {
    }

    public ConcoursDTO(Long id, String poste, String imageData, String description, Date dateExamen, Date dateDelais) {
        this.id = id;
        this.poste = poste;
        this.imageData = imageData;
        this.description = description;
        this.dateExamen = dateExamen;
        this.dateDelais = dateDelais;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateExamen() {
		return dateExamen;
	}

	public void setDateExamen(Date dateExamen) {
		this.dateExamen = dateExamen;
	}

	public Date getDateDelais() {
		return dateDelais;
	}

	public void setDateDelais(Date dateDelais) {
		this.dateDelais = dateDelais;
	}

 
    
}
