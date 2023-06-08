package com.concours.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	@Entity
	public class HistoriqueReclamation {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private Date date;

	    @Column(nullable = false)
	    private String action;

	    @Column(nullable = false)
	    private Long reclamationId;
	    
	    
	    @Column
	    private String reclamationName;

	    
	    @Column
	    private String sender;
	    
	    @Column
	    private String emailSender;
	    
	    
	    
	    
	    
	    
	    

		public String getSender() {
			return sender;
		}


		public void setSender(String sender) {
			this.sender = sender;
		}


		public String getEmailSender() {
			return emailSender;
		}


		public void setEmailSender(String emailSender) {
			this.emailSender = emailSender;
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


		public Long getReclamationId() {
			return reclamationId;
		}


		public void setReclamationId(Long reclamationId) {
			this.reclamationId = reclamationId;
		}


		public String getReclamationName() {
			return reclamationName;
		}


		public void setReclamationName(String reclamationName) {
			this.reclamationName = reclamationName;
		}

		public HistoriqueReclamation() {
			super();
			// TODO Auto-generated constructor stub
		}


		public HistoriqueReclamation(Long id, Date date, String action, Long reclamationId, String reclamationName,
				String sender, String emailSender) {
			super();
			this.id = id;
			this.date = date;
			this.action = action;
			this.reclamationId = reclamationId;
			this.reclamationName = reclamationName;
			this.sender = sender;
			this.emailSender = emailSender;
		}


		@Override
		public String toString() {
			return "HistoriqueReclamation [id=" + id + ", date=" + date + ", action=" + action + ", reclamationId="
					+ reclamationId + ", reclamationName=" + reclamationName + ", sender=" + sender + ", emailSender="
					+ emailSender + "]";
		}

	    
	    
	    

	}
