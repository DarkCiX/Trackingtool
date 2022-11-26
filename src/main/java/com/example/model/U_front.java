package com.example.model;

public class U_front {
	public int id_u;
	public int id_type_u;
	public String token;
	public String mail;
	public String nom;
	public String phone;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId_u() {
		return id_u;
	}
	public void setId_u(int id_u) {
		this.id_u = id_u;
	}
	public int getId_type_u() {
		return id_type_u;
	}
	public void setId_type_u(int id_type_u) {
		this.id_type_u = id_type_u;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public U_front(int id_u,int id_type_u,String token,String mail,String nom,String phone) {
		this.setId_u(id_u);
		this.setId_type_u(id_type_u);
		this.setToken(token);
		this.setMail(mail);
		this.setNom(nom);
		this.setPhone(phone);
	}
	public U_front() {}
}
