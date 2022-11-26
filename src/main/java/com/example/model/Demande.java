package com.example.model;

public class Demande {
	String email;
	String nomClient;
	String object;
	String text;
	String fileName;
	
	
	public Demande(String email, String nomClient, String object, String text, String fileName) {
		this.email = email;
		this.nomClient = nomClient;
		this.object = object;
		this.text = text;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
