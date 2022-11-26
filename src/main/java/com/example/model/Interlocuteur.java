package com.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Interlocuteur extends BaseObject {
	public int id;
	public int id_client;
	public String nom;
	public String contact;
	public String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Interlocuteur() {}
	public Interlocuteur(int id,int id_client,String nom,String contact,String email) {
		this.setId(id);
		this.setId_client(id_client);
		this.setNom(nom);
		this.setContact(contact);
		this.setEmail(email);
	}
	public Interlocuteur(int id_client,String nom,String contact,String email) {
		this.setId_client(id_client);
		this.setNom(nom);
		this.setContact(contact);
		this.setEmail(email);
	}
	
	public static Interlocuteur FindById(String id_client,Connection con) {
		String query = "SELECT *  FROM interlocuteur WHERE id_client='"+id_client+"'"; 
		System.out.println(query);
		Interlocuteur cl = new Interlocuteur();
		try {
	        Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				 cl = new Interlocuteur(rs.getInt("id"),rs.getInt("id_client"),rs.getString("nom"),rs.getString("contact"),rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cl;
       
	}
}
