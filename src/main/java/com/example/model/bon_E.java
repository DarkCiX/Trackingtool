package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_E {
	public int id_entree;
	public String num_be;
	public String dates; 
	public String fournisseur;
	public String phone;
	public String etat;
	public int reparation;
	public int sortie;
	public String client;
	public int client_id;
	public String motif;
	public String recepteur;
	public String observation;
	public String sign_recepteur;
	public String sign_client;
	public String num_achat; 
	
	
	public int getReparation() {
		return reparation;
	}
	public void setReparation(int reparation) {
		this.reparation = reparation;
	}
	public int getSortie() {
		return sortie;
	}
	public void setSortie(int sortie) {
		this.sortie = sortie;
	}

	public int getId_entree() {
		return id_entree;
	}
	public void setId_entree(int id_entree) {
		this.id_entree = id_entree;
	}
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
	public String getSign_recepteur() {
		return sign_recepteur;
	}
	public void setSign_recepteur(String sign_recepteur) {
		this.sign_recepteur = sign_recepteur;
	}
	public String getSign_client() {
		return sign_client;
	}
	public void setSign_client(String sign_client) {
		this.sign_client = sign_client;
	}
	public String getNum_achat() {
		return num_achat;
	}
	public void setNum_achat(String num_achat) {
		this.num_achat = num_achat;
	}
	public int getId() {
		return id_entree;
	}
	public void setId(int id) {
		this.id_entree = id;
	}
	public String getNum_be() {
		return num_be;
	}
	public void setNum_be(String num_be) {
		this.num_be = num_be;
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
	public bon_E() {}
	public bon_E(int id,String num_be,String dates,String fournisseur,String phone,String etat,int reparation,int sortie,String client,int client_id,String motif,String recepteur,String observation,String sign_recepteur,String sign_client,String num_achat) {
		this.setId(id);
		this.setNum_be(num_be);
		this.setDates(dates);
		this.setFournisseur(fournisseur);
		this.setPhone(phone);
		this.setEtat(etat);
		this.setReparation(reparation);
		this.setSortie(sortie);
		this.setClient(client);
		this.setClient_id(client_id);
		this.setMotif(motif);
		this.setRecepteur(recepteur);	
		this.setObservation(observation);
		this.setSign_recepteur(sign_recepteur);
		this.setSign_client(sign_client);
		this.setNum_achat(num_achat);
	}
	
	public static ArrayList<bon_E> GetAllBon_E(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_E ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_E> detail = new ArrayList<bon_E>();
        while(rs.next()){
        	bon_E temp = new bon_E();
        	temp.setId(rs.getInt("id_entree"));
        	temp.setNum_be(rs.getString("num_be"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setFournisseur(rs.getString("fournisseur")); 
        	temp.setPhone(rs.getString("phone"));
        	temp.setEtat(rs.getString("etat"));
        	temp.setReparation(rs.getInt("reparation"));
        	temp.setSortie(rs.getInt("sortie"));
        	temp.setClient(rs.getString("nom"));
        	temp.setClient_id(rs.getInt("id"));
        	temp.setMotif(rs.getString("motif"));
        	temp.setRecepteur(rs.getString("recepteur"));
        	temp.setObservation(rs.getString("observation"));
        	temp.setSign_recepteur(rs.getString("sign_recepteur"));
        	temp.setSign_client(rs.getString("sign_client"));
        	temp.setNum_achat(rs.getString("num_achat"));
            detail.add(temp);
        }
        co.close();
        return detail;
	  }
	
	public static bon_E getdetail_bon_E(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_E user = new bon_E();
	    try{
	      pst=con.prepareStatement("select * from bon_E where num_be=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	          user = new bon_E(result.getInt("id_entree"),result.getString("num_be"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getInt("reparation"),result.getInt("sortie"),result.getString("nom"),result.getInt("id"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_recepteur"),result.getString("sign_client"),result.getString("num_achat"));
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
