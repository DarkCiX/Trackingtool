package com.example.model;

import java.util.List;

public class Messages {

	private String mail;
	private String text;
	private String date;
	private String fileName;
	private String vue;
	private String admin;
	private String destinataire;
	
	
	public Messages() {
		
	}
	

	public Messages(String mail, String text, String date, String fileName, String vue, String destinataire) {
		super();
		this.mail = mail;
		this.text = text;
		this.date = date;
		this.fileName = fileName;
		this.vue = vue;
		this.destinataire = destinataire;
	}




	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public String getVue() {
		return vue;
	}


	public void setVue(String vue) {
		this.vue = vue;
	}


	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public void updateMessageArray(String mailClient,int option,List<Messages> mess){
		System.out.println("here 0");
		System.out.println(mess.getClass().getSimpleName());
		try {
			if(option==1) {
				for(Messages m:mess) {
					if(mailClient.compareTo(m.getMail())!=0) {
						System.out.println("here 1");
						m.setVue("1");
					}
					
				}
			}
			else {
				for(Messages m:mess) {
					if(mailClient.compareTo(m.getMail())==0) {
						m.setVue("1");
					}
					
				}
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
}
