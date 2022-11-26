package com.example.model;

public class EnguageMessage {
	String serial;
	String email;
	String destinataire;
	String text;
	String name;
	

	public EnguageMessage(String serial, String email, String destinataire, String text, String name) {
		super();
		this.serial = serial;
		this.email = email;
		this.destinataire = destinataire;
		this.text = text;
		this.name = name;
	}
	
	public String getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
