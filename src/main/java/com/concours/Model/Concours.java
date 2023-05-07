package com.concours.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Base64;
@Entity
//creation du table
@Table (name="CONCOURS")

public class  Concours {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	private String poste;
	@Lob 
	private byte[] image;
	private String description;
	
    @Temporal(TemporalType.DATE)
	private Date dateExamen;
    
    @Temporal(TemporalType.DATE)
	private Date dateDelais;

	
	
	public Concours(Long id, String poste, byte[] image, String description, Date dateExamen, Date dateDelais) {
		super();
		this.id = id;
		this.poste = poste;
		this.image = image;
		this.description = description;
		this.dateExamen = dateExamen;
		this.dateDelais = dateDelais;
	}

	public Concours() {
		super();
	}




	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	@Override
	public String toString() {
		return "Concours [id=" + id + ", poste=" + poste + ", photo=" + image + ", description=" + description
				+ ", dateExamen=" + dateExamen + ", dateDelais=" + dateDelais + "]";
	}
    
    
	
	

	
	
	public String getBase64Image() {
        if (this.image == null || this.image.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(this.image);
    }
	  public void setBase64Image(String base64Image) {
	        if (base64Image == null || base64Image.isEmpty()) {
	            this.image = null;
	        } else {
	            this.image = Base64.getDecoder().decode(base64Image);
	        }
	    }
	
	
	
}
	
	
	
	
	
	

