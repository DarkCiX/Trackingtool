package com.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Devis {
	public int id;
	public String name;
	public String email;
	public String contact;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Devis() {}
	public Devis(int id,String nom,String email,String contact) {
		this.setId(id);
		this.setName(nom);
		this.setEmail(email);
		this.setContact(contact);
	}
	
	public Devis(String nom,String email,String contact) {
		this.setName(nom);
		this.setEmail(email);
		this.setContact(contact);
	}
	
	public static Devis FindById(String name,Connection con) {
		String query = "SELECT *  FROM t_client WHERE nom='"+name+"'"; 
		System.out.println(query);
		Devis devis = new Devis();
		try {
	        Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				 devis = new Devis(rs.getInt("id"),rs.getString("nom"),rs.getString("email"),rs.getString("contact"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devis;
       
	}
	
}
