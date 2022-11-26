package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reclamation {
	public int id_reclamation;
	public int id_utilisateur;
	public String dates;
	public String client;
	public String sollicitant;
	public String contact;
	public String ref_facture;
	public String envoyeur;
	public String destinataire;
	public String cc;
	public String objet;
	public String message;
	public int etat;

	public Reclamation(int id_reclamation, int id_utilisateur,String dates, String client, String sollicitant, String contact,
			String ref_facture, String envoyeur,String destinataire,String cc, String objet, String message,int etat) {
		super();
		this.id_reclamation = id_reclamation;
		this.id_utilisateur = id_utilisateur;
		this.dates = dates;
		this.client = client;
		this.sollicitant = sollicitant;
		this.contact = contact;
		this.ref_facture = ref_facture;
		this.envoyeur = envoyeur;
		this.destinataire = destinataire;
		this.cc = cc;
		this.objet = objet;
		this.message = message;
		this.etat = etat;
	}
	public Reclamation() {};
	public int getId_reclamation() {
		return id_reclamation;
	}
	public void setId_reclamation(int id_reclamation) {
		this.id_reclamation = id_reclamation;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}

	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getSollicitant() {
		return sollicitant;
	}
	public void setSollicitant(String sollicitant) {
		this.sollicitant = sollicitant;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRef_facture() {
		return ref_facture;
	}
	public void setRef_facture(String ref_facture) {
		this.ref_facture = ref_facture;
	}
	public String getEnvoyeur() {
		return envoyeur;
	}
	public void setEnvoyeur(String envoyeur) {
		this.envoyeur = envoyeur;
	}
	public String getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void save(Connection co,Reclamation newD) throws Exception {
		PreparedStatement pst=null;
		try{
		  pst=co.prepareStatement("INSERT INTO reclamation(id_u,dates,client,sollicitant,contact,ref_facture,envoyeur,destinataire,cc,objet,message) VALUES(?,to_date(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?)");
		  pst.setInt(1,newD.getId_utilisateur());
		  pst.setString(2,newD.getDates());
		  pst.setString(3,newD.getClient());
          pst.setString(4,newD.getSollicitant());
          pst.setString(5,newD.getContact());
          pst.setString(6,newD.getRef_facture());
          pst.setString(7,newD.getEnvoyeur());
          pst.setString(8,newD.getDestinataire());
          pst.setString(9,newD.getCc());
          pst.setString(10,newD.getObjet());
          pst.setString(11,newD.getMessage());
          System.out.println(pst);
          pst.executeUpdate();
          
        }
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			pst.close();
			co.close();
		}
	}
	
	public static void Update(Connection co,int idreclamation) {
		 String query = "UPDATE reclamation " + "SET etat = ? "+ "WHERE id_reclamation= ?";
       try{
          
      	 PreparedStatement pst = co.prepareStatement(query);
           pst.setInt(1, 1);
           pst.setInt(2, idreclamation);
           pst.executeUpdate();

       } catch (SQLException ex) {
           ex.printStackTrace();
           System.out.println(ex.getMessage());
       }
	}
	
	public static ArrayList<Reclamation> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from reclamation ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Reclamation> detail = new ArrayList<Reclamation>();
        while(rs.next()){
        	Reclamation temp = new Reclamation();
        	temp.setId_reclamation(rs.getInt("id_reclamation"));
        	temp.setId_utilisateur(rs.getInt("id_u"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setClient(rs.getString("client"));
    		temp.setSollicitant(rs.getString("sollicitant"));
    		temp.setContact(rs.getString("contact"));
    		temp.setRef_facture(rs.getString("ref_facture"));
    		temp.setEnvoyeur(rs.getString("envoyeur"));
    		temp.setDestinataire(rs.getString("destinataire"));
    		temp.setCc(rs.getString("cc"));
    		temp.setObjet(rs.getString("objet"));
    		temp.setMessage(rs.getString("message"));
//    		temp.setEtat(rs.getInt("etat"));
            detail.add(temp);
        }
        
        return detail;
	 }
	public static Reclamation GetReclamation(Connection co,int id) throws ClassNotFoundException, Exception{
        String query = "Select * from reclamation WHERE id_reclamation="+id;
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        Reclamation temp = new Reclamation();
        while(rs.next()){
        	temp.setId_reclamation(rs.getInt("id_reclamation"));
        	temp.setId_utilisateur(rs.getInt("id_u"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setClient(rs.getString("client"));
    		temp.setSollicitant(rs.getString("sollicitant"));
    		temp.setContact(rs.getString("contact"));
    		temp.setRef_facture(rs.getString("ref_facture"));
    		temp.setEnvoyeur(rs.getString("envoyeur"));
    		temp.setDestinataire(rs.getString("destinataire"));
    		temp.setCc(rs.getString("cc"));
    		temp.setObjet(rs.getString("objet"));
    		temp.setMessage(rs.getString("message"));
    		temp.setEtat(rs.getInt("etat"));
        }
        return temp;
	 }
	public static double StatistiqueMonthly(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS total FROM Reclamation WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM now())";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("total");
        }
        return result;
	}
	
	public static double[] Statistique(Connection co) throws ClassNotFoundException, Exception {
		int i = 12;
		double[] result = new double[12];
		for(int j =0;j<i;j++) {
			result[j] = StatistiqueMonthly(co,j+1);
		}
		return result;
	}
}
