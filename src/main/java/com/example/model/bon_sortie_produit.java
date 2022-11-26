package com.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_sortie_produit {
	public int id_bsp;
	public int id;
	public int id_entree;
	public int id_bon_sortie;
	public String num_sortie;
	public String num_be;
	public String reference;
	public String designation;
	public int quantite;
	
	public int getId_bsp() {
		return id_bsp;
	}
	public void setId_bsp(int id_bsp) {
		this.id_bsp = id_bsp;
	}
	public int getId_entree() {
		return id_entree;
	}
	public void setId_entree(int id_entree) {
		this.id_entree = id_entree;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_bon_sortie() {
		return id_bon_sortie;
	}
	public void setId_bon_sortie(int id_bon_sortie) {
		this.id_bon_sortie = id_bon_sortie;
	}
	public String getNum_sortie() {
		return num_sortie;
	}
	public void setNum_sortie(String num_be) {
		this.num_sortie = num_be;
	}
	public String getNum_be() {
		return num_be;
	}
	public void setNum_be(String num_be) {
		this.num_be = num_be;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public bon_sortie_produit() {}
	public bon_sortie_produit(int id, int id_entree,int id_bon_sortie,String num_be, String reference, String designation, int quantite) {
		this.setId(id);
		this.setId_entree(id_entree);
		this.setNum_be(num_be);
		this.setReference(reference);
		this.setDesignation(designation);
		this.setQuantite(quantite);
	}
	
	public bon_sortie_produit(int id,int id_bon_sortie,String num_sortie, String reference, String designation, int quantite) {
		this.setId_bsp(id);
		this.setId_bon_sortie(id_bon_sortie);
		this.setNum_sortie(num_sortie);
		this.setReference(reference);
		this.setDesignation(designation);
		this.setQuantite(quantite);
	}

	
	public static ArrayList<bon_sortie_produit> GetAll(Connection co,String facture) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_sortie_produit WHERE num_sortie='"+facture+"'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_sortie_produit> bonentree = new ArrayList<bon_sortie_produit>();
        while(rs.next()){
        	bon_sortie_produit temp = new bon_sortie_produit();
        	temp.setId_bsp(rs.getInt("id_bsp"));
        	temp.setId_bon_sortie(rs.getInt("id_bon_sortie"));
        	temp.setNum_sortie(rs.getString("num_sortie"));
        	temp.setReference(rs.getString("reference"));
        	temp.setDesignation(rs.getString("designation"));
        	temp.setQuantite(rs.getInt("quantite"));
        	bonentree.add(temp);
        }
        return bonentree;
	  }
}
