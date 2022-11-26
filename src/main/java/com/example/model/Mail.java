package com.example.model;


public class Mail {
	// Replace with your email here:  
    public static final String MY_EMAIL = "";
 
    // Replace password!!
    public static final String MY_PASSWORD = "";
 
    // And receiver!
    public static final String FRIEND_EMAIL = "";
    
    private String envoyeur;
    private String receveur;
    private String Object;
	private String message;
	private String filename;
	private byte[] bytes;
   
	
	public String getEnvoyeur() {
		return envoyeur;
	}
	public void setEnvoyeur(String envoyeur) {
		this.envoyeur = envoyeur;
	}
	public String getReceveur() {
		return receveur;
	}
	public void setReceveur(String receveur) {
		this.receveur = receveur;
	}
	
    public String getObject() {
		return Object;
	}
	public void setObject(String object) {
		Object = object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	 public String getFilename() {
			return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public Mail() {}
	public Mail(String envoyeur,String recevoir,String Object,String message) {
		this.setEnvoyeur(envoyeur);
		this.setReceveur(recevoir);
		this.setObject(Object);
		this.setMessage(message);
	}
	public Mail(String envoyeur,String recevoir,String Object,String message,String filename,byte[] byt) {
		this.setEnvoyeur(envoyeur);
		this.setReceveur(recevoir);
		this.setObject(Object);
		this.setMessage(message);
		this.setFilename(filename);
		this.setBytes(byt);
	}
    
}
