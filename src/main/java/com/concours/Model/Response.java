package com.concours.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Response {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private Long id;
	private String message;
	private LocalDateTime dateTime;
    private String responderName;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reclamation_id")
    private Reclamation reclamation;
    
    
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Response(Long id, String message, LocalDateTime dateTime, String responderName) {
		super();
		this.id = id;
		this.message = message;
		this.dateTime = dateTime;
		this.responderName = responderName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getResponderName() {
		return responderName;
	}
	public void setResponderName(String responderName) {
		this.responderName = responderName;
	}
	@Override
	public String toString() {
		return "Response [id=" + id + ", message=" + message + ", dateTime=" + dateTime + ", responderName="
				+ responderName + "]";
	}
	
	  
    
	  
	}
