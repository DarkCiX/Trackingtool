package com.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_entree_produit {
	
	public int id;
	public String num_be;
	public int id_entree;
	public String reference;
	public String designation;
	public int quantite;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum_be() {
		return num_be;
	}
	public void setNum_be(String num_be) {
		this.num_be = num_be;
	}
	public int getId_entree() {
		return id_entree;
	}
	public void setId_entree(int id_entree) {
		this.id_entree = id_entree;
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
	public bon_entree_produit() {}
	public bon_entree_produit(int id,int id_entree,String num_be, String reference, String designation, int quantite) {
		this.setId(id);
		this.setId_entree(id_entree);
		this.setNum_be(num_be);;
		this.setReference(reference);
		this.setDesignation(designation);
		this.setQuantite(quantite);
	}
	
	
	public static ArrayList<bon_entree_produit> GetAll(Connection co,String facture) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_entree_produit WHERE num_be='"+facture+"'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_entree_produit> bonentree = new ArrayList<bon_entree_produit>();
        while(rs.next()){
        	bon_entree_produit temp = new bon_entree_produit();
        	temp.setId(rs.getInt("id_be"));
        	temp.setId_entree(rs.getInt("id_bon_entre"));
        	temp.setNum_be(rs.getString("num_be"));
        	temp.setReference(rs.getString("reference"));
        	temp.setDesignation(rs.getString("designation"));
        	temp.setQuantite(rs.getInt("quantite"));
        	bonentree.add(temp);
        }
        return bonentree;
	  }
	
	public static ArrayList<bon_entree_produit> GetAllBS(Connection co,String facture) throws ClassNotFoundException, Exception{
        String query = "Select reference,designation,quantite from bon_entree_produit WHERE num_be='"+facture+"'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_entree_produit> bonentree = new ArrayList<bon_entree_produit>();
        while(rs.next()){
        	bon_entree_produit temp = new bon_entree_produit();
        	temp.setReference(rs.getString("reference"));
        	temp.setDesignation(rs.getString("designation"));
        	temp.setQuantite(rs.getInt("quantite"));
        	bonentree.add(temp);
        }
        return bonentree;
	  }
}
