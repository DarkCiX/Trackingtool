package com.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {
	public int id;
	public String nom;
	public String nif;
	public String stat;
	public String rcs;
	public String adress;
	public String cin;
	public String permis;
	public String passport;
	public boolean particulier;
	
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getPermis() {
		return permis;
	}
	public void setPermis(String permis) {
		this.permis = permis;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}

	public boolean getParticulier() {
		return particulier;
	}
	public void setParticulier(boolean particulier) {
		this.particulier = particulier;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		System.out.println(this.getId());
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getRcs() {
		return rcs;
	}
	public void setRcs(String rcs) {
		this.rcs = rcs;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Client() {}
	
	public Client(int id,String nom,String nif,String stat,String rcs,String adress,String cin,String permis,String passport,boolean particulier) {
		this.setId(id);
		this.setNom(nom);
		this.setNif(nif);
		this.setStat(stat);
		this.setRcs(rcs);
		this.setAdress(adress);
		this.setCin(cin);
		this.setPermis(permis);
		this.setPassport(passport);
		this.setParticulier(particulier);
	}
	public Client(String nom,String nif,String stat,String rcs,String adress) {
		this.setNif(nif);
		this.setStat(stat);
		this.setRcs(rcs);
		this.setAdress(adress);
	}
	
	public static Client FindById(String name,Connection con) throws SQLException {
		String query = "SELECT * FROM Client WHERE nom='"+name+"'"; 
		System.out.println(query);
		Client cl = new Client();
		try {
	        Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				 cl = new Client(rs.getInt("id"),rs.getString("nom"),rs.getString("nif"),rs.getString("stat"),rs.getString("rcs"),rs.getString("adresse"),rs.getString("cin"),rs.getString("permis"),rs.getString("passeport"),rs.getBoolean("particulier"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			con.close();
		}
		return cl;
       
	}
	
	public static Client Find(String id,Connection con) throws SQLException {
		String query = "SELECT *  FROM Client WHERE id='"+id+"'"; 
		System.out.println(query);
		Client cl = new Client();
		try {
	        Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				 cl = new Client(rs.getInt("id"),rs.getString("nom"),rs.getString("nif"),rs.getString("stat"),rs.getString("rcs"),rs.getString("adresse"),rs.getString("cin"),rs.getString("permis"),rs.getString("passeport"),rs.getBoolean("particulier"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			con.close();
		}
		return cl;
       
	}
	
	
	public  Client FindClient(String id,Connection con) throws SQLException {
		String query = "SELECT *  FROM Client WHERE id='"+id+"'"; 
		System.out.println(query);
		Client cl = new Client();
		try {
	        Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				 cl = new Client(rs.getInt("id"),rs.getString("nom"),rs.getString("nif"),rs.getString("stat"),rs.getString("rcs"),rs.getString("adresse"),rs.getString("cin"),rs.getString("permis"),rs.getString("passeport"),rs.getBoolean("particulier"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			con.close();
		}
		return cl;
       
	}
	
	
	
}
