package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_sortie {
	public int id_sortie;
	public String num_sortie;
	public String num_bon_entree;
	public String dates; 
	public String fournisseur;
	public String phone;
	public String etat;
	public int id_client;
	public String motif;
	public String recepteur;
	public String observation;
	public String sign_responsable;
	public String sign_recepteur;
	public String getSign_responsable() {
		return sign_responsable;
	}
	public void setSign_responsable(String sign_responsable) {
		this.sign_responsable = sign_responsable;
	}
	public String getNum_sortie() {
		return num_sortie;
	}
	public void setNum_sortie(String num_sortie) {
		this.num_sortie = num_sortie;
	}
	public String getNum_bon_entree() {
		return num_bon_entree;
	}
	public void setNum_bon_entree(String num_bon_entree) {
		this.num_bon_entree = num_bon_entree;
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

	public int getId_sortie() {
		return id_sortie;
	}
	public void setId_sortie(int id_sortie) {
		this.id_sortie = id_sortie;
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
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public bon_sortie() {}
	public bon_sortie(int id_sortie,String num_sortie,String num_bon_entree,String dates,String fournisseur,String phone,String etat,int id_client,String motif,String recepteur,String observation,String sign_responsable,String sign_recepteur) {
		this.setId_sortie(id_sortie);
		this.setNum_sortie(num_sortie);
		this.setNum_bon_entree(num_bon_entree);
		this.setDates(dates);
		this.setFournisseur(fournisseur);
		this.setPhone(phone);
		this.setEtat(etat);
		this.setId_client(id_client);
		this.setMotif(motif);
		this.setRecepteur(recepteur);	
		this.setObservation(observation);
		this.setSign_responsable(sign_responsable);
		this.setSign_recepteur(sign_recepteur);
		
	}
	
	public static void Save(Connection co,bon_sortie newD,bon_sortie_produit[] produit) throws Exception {
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		try{
		  pst=co.prepareStatement("INSERT INTO bon_sortie(num_bon_entree,dates,fournisseur,phone,etat,id_client,motif,recepteur,observation,sign_responsable,sign_recepteur) VALUES(?,to_date(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?)");
		  pst.setString(1,newD.getNum_bon_entree());
		  pst.setString(2,newD.getDates());
          pst.setString(3,newD.getFournisseur());
          pst.setString(4,newD.getPhone());
          pst.setString(5,newD.getEtat());
          pst.setInt(6,newD.getId_client());
          pst.setString(7,newD.getMotif());
          pst.setString(8,newD.getRecepteur());
          pst.setString(9,newD.getObservation());
          pst.setString(10,newD.getSign_responsable());
          pst.setString(11,newD.getSign_recepteur());
          System.out.println("Bon de sortie " +pst);
          pst.executeUpdate();
          
          bon_sortie user = getMaxId_sortie(co);
          
          for(int i=0;i<produit.length;i++) {
			  pst2=co.prepareStatement("INSERT INTO bon_sortie_produit(id_bon_sortie,num_sortie,reference,designation,quantite) VALUES(?,?,?,?,?)");
			  pst2.setInt(1,user.getId_sortie());	
			  pst2.setString(2,user.getNum_sortie());
	          pst2.setString(3,produit[i].getReference());
	          pst2.setString(4,produit[i].getDesignation());
	          pst2.setInt(5,produit[i].getQuantite());
	          System.out.println("Bon de sortie produit"+ pst2);
	          pst2.executeUpdate();	
			}

        }
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			pst.close();
			co.close();
		}
	}
	
	public static bon_sortie  getMaxId_sortie(Connection con) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_sortie user = new bon_sortie();
	    try{
	      pst=con.prepareStatement("select*from bon_sortie where id_sortie=(select Max(id_sortie) from bon_sortie);");
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	    	  user =   new bon_sortie(result.getInt("id_sortie"),result.getString("num_sortie"),result.getString("num_bon_entree"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getInt("id_client"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_responsable"),result.getString("sign_recepteur"));
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
	public static ArrayList<bon_sortie> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_sortie ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_sortie> detail = new ArrayList<bon_sortie>();
        while(rs.next()){
        	bon_sortie temp = new bon_sortie();
        	temp.setId_sortie(rs.getInt("id"));
        	temp.setNum_sortie(rs.getString("num_sortie"));
        	temp.setNum_bon_entree(rs.getString("num_bon_entree"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setFournisseur(rs.getString("fournisseur")); 
        	temp.setPhone(rs.getString("phone"));
        	temp.setEtat(rs.getString("etat"));
        	temp.setId_client(rs.getInt("id_client"));
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
	
	public static bon_sortie getdetail_bon_entree(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_sortie user = new bon_sortie();
	    try{
	      pst=con.prepareStatement("select * from bon_sortie where num_be=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	    	  user =   new bon_sortie(result.getInt("id_sortie"),result.getString("num_sortie"),result.getString("num_bon_entree"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getInt("id_client"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_responsable"),result.getString("sign_recepteur"));
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
