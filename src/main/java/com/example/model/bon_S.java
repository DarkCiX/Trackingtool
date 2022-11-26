package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_S {
	public int id_sortie;
	public String num_sortie;
	public String num_bon_entree;
	public String dates; 
	public String fournisseur;
	public String phone;
	public String etat;
	public String client;
	public int client_id;
	public String motif;
	public String recepteur;
	public String observation;
	public String sign_responsable;
	public String sign_recepteur;
	public String getRecepteur() {
		return recepteur;
	}
	public void setRecepteur(String recepteur) {
		this.recepteur = recepteur;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getSign_responsable() {
		return sign_responsable;
	}
	public void setSign_responsable(String sign_responsable) {
		this.sign_responsable = sign_responsable;
	}
	public String getSign_recepteur() {
		return sign_recepteur;
	}
	public void setSign_recepteur(String sign_recepteur) {
		this.sign_recepteur = sign_recepteur;
	}
	public int getId_sortie() {
		return id_sortie;
	}
	public void setId_sortie(int id_sortie) {
		this.id_sortie = id_sortie;
	}
	public String getNum_bon_entree() {
		return num_bon_entree;
	}
	public void setNum_bon_entree(String num_bon_entree) {
		this.num_bon_entree = num_bon_entree;
	}
	public String getNum_sortie() {
		return num_sortie;
	}
	public void setNum_sortie(String num_sortie) {
		this.num_sortie = num_sortie;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public bon_S() {}
	public bon_S(int id_sortie,String num_sortie,String num_bon_entree,String dates,String fournisseur,String phone,String etat,String client,int client_id,String motif,String recepteur,String observation,String sign_responsable,String sign_recepteur) {
		this.setId_sortie(id_sortie);
		this.setNum_sortie(num_sortie);
		this.setNum_bon_entree(num_bon_entree);
		this.setDates(dates);
		this.setFournisseur(fournisseur);
		this.setPhone(phone);
		this.setEtat(etat);
		this.setClient(client);
		this.setClient_id(client_id);
		this.setMotif(motif);
		this.setRecepteur(recepteur);
		this.setObservation(observation);
		this.setSign_responsable(sign_responsable);
		this.setSign_recepteur(sign_recepteur);
	}
	
	public static ArrayList<bon_S> GetAllBon_S(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_S ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_S> detail = new ArrayList<bon_S>();
        while(rs.next()){
        	bon_S temp = new bon_S();
        	temp.setId_sortie(rs.getInt("id_sortie"));
        	temp.setNum_sortie(rs.getString("num_sortie"));
        	temp.setNum_bon_entree(rs.getString("num_bon_entree"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setFournisseur(rs.getString("fournisseur")); 
        	temp.setPhone(rs.getString("phone"));
        	temp.setEtat(rs.getString("etat"));
        	temp.setClient(rs.getString("nom"));
        	temp.setClient_id(rs.getInt("id"));
        	temp.setMotif(rs.getString("motif"));
        	temp.setRecepteur(rs.getString("recepteur"));
        	temp.setObservation(rs.getString("observation"));
        	temp.setSign_responsable(rs.getString("sign_responsable"));
        	temp.setSign_recepteur(rs.getString("sign_recepteur"));
            detail.add(temp);
        }
        co.close();
        return detail;
	  }
	
	public static bon_S getdetail_bon_S(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_S user = new bon_S();
	    try{
	      pst=con.prepareStatement("select * from bon_S where num_sortie=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	          user = new bon_S(result.getInt("id_sortie"),result.getString("num_sortie"),result.getString("num_bon_entree"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getString("nom"),result.getInt("id"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_responsable"),result.getString("sign_recepteur"));
	      }
	    }
		catch(Exception ex){
		 throw ex;
		}
		finally{
			result.close();
			pst.close();
		}
	  return user;
	} 
}
