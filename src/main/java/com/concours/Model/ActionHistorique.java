package com.concours.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActionHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Long concoursId;
    
    
    @Column
    private String concoursName;
    

    
    
    


	public String getConcoursName() {
		return concoursName;
	}

	public void setConcoursName(String concoursName) {
		this.concoursName = concoursName;
	}

	public ActionHistorique(Long id, Date date, String action, Long concoursId, String concoursName) {
		super();
		this.id = id;
		this.date = date;
		this.action = action;
		this.concoursId = concoursId;
		this.concoursName = concoursName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getConcoursId() {
		return concoursId;
	}

	public void setConcoursId(Long concoursId) {
		this.concoursId = concoursId;
	}

	public ActionHistorique(Long id, Date date, String action, Long concoursId) {
		super();
		this.id = id;
		this.date = date;
		this.action = action;
		this.concoursId = concoursId;
	}

	public ActionHistorique() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ActionHistorique [id=" + id + ", date=" + date + ", action=" + action + ", concoursId=" + concoursId
				+ "]";
	}
    
    
    
    

}