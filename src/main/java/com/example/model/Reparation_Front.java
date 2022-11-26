package com.example.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class Reparation_Front {
	public int id_reparation;
	public int id_utilisateur;
	public int id_bon_entree;
	public int id_bon_entree_produit;
	public int avancement;
	public String prevu;
	public String dates;
	public String step;
	public String client;
	public String technicien;
	public String num_be;
	public String desigantion;
	public String reference;
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int etat;
	
	
	

	public Reparation_Front(int id_reparation, int id_utilisateur, int id_bon_entree, int id_bon_entree_produit,
			int avancement,String avancemen,String dates,String step,String client, String technicien, String num_be, String desigantion,String reference) {
		super();
		this.id_reparation = id_reparation;
		this.id_utilisateur = id_utilisateur;
		this.id_bon_entree = id_bon_entree;
		this.id_bon_entree_produit = id_bon_entree_produit;
		this.client = client;
		this.avancement = avancement;
		this.prevu = prevu;
		this.dates = dates;
		this.step = step;
		this.technicien = technicien;
		this.num_be = num_be;
		this.desigantion = desigantion;
		this.reference = reference;
	}
	
	public Reparation_Front(int id_reparation, int id_utilisateur, int id_bon_entree, int id_bon_entree_produit,
			int avancement,String avancemen,String dates,String step,String client, String technicien, String num_be, String desigantion,int etat,String reference) {
		super();
		this.id_reparation = id_reparation;
		this.id_utilisateur = id_utilisateur;
		this.id_bon_entree = id_bon_entree;
		this.id_bon_entree_produit = id_bon_entree_produit;
		this.client = client;
		this.avancement = avancement;
		this.prevu = prevu;
		this.dates = dates;
		this.step = step;
		this.technicien = technicien;
		this.num_be = num_be;
		this.desigantion = desigantion;
		this.reference = reference;
	}
	public Reparation_Front() {}
	public int getId_reparation() {
		return id_reparation;
	}
	public void setId_reparation(int id_reparation) {
		this.id_reparation = id_reparation;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_bon_entree() {
		return id_bon_entree;
	}
	public void setId_bon_entree(int id_bon_entree) {
		this.id_bon_entree = id_bon_entree;
	}
	public int getId_bon_entree_produit() {
		return id_bon_entree_produit;
	}
	public void setId_bon_entree_produit(int id_bon_entree_produit) {
		this.id_bon_entree_produit = id_bon_entree_produit;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

	public int getAvancement() {
		return avancement;
	}
	public void setAvancement(int avancement) {
		this.avancement = avancement;
	}
	public String getPrevu() {
		return prevu;
	}
	public void setPrevu(String prevu) {
		this.prevu = prevu;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}

	public String getTechnicien() {
		return technicien;
	}
	public void setTechnicien(String technicien) {
		this.technicien = technicien;
	}
	public String getNum_be() {
		return num_be;
	}
	public void setNum_be(String num_be) {
		this.num_be = num_be;
	}
	public String getDesigantion() {
		return desigantion;
	}
	public void setDesigantion(String desigantion) {
		this.desigantion = desigantion;
	}
	
	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
	
	public static ArrayList<Reparation_Front> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from R_P ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Reparation_Front> detail = new ArrayList<Reparation_Front>();
        while(rs.next()){
        	Reparation_Front temp = new Reparation_Front();
        	temp.setId_reparation(rs.getInt("id_reparation"));
        	temp.setId_utilisateur(rs.getInt("id_utilisateur"));
        	temp.setId_bon_entree(rs.getInt("id_bon_entree"));
        	temp.setId_bon_entree_produit(rs.getInt("id_bon_entree_produit"));
        	temp.setClient(rs.getString("client"));
        	temp.setAvancement(rs.getInt("avancement"));
        	temp.setPrevu(rs.getString("prevu"));
        	temp.setDates(rs.getString("dates"));
        	temp.setStep(rs.getString("step"));
        	temp.setTechnicien(rs.getString("technicien"));
        	temp.setNum_be(rs.getString("num_be"));
        	temp.setDesigantion(rs.getString("designation"));
            detail.add(temp);
        }
        return detail;
	}
	
	public static ArrayList<Reparation_Front> GetAllWithEtat(Connection co) throws ClassNotFoundException, Exception{
        String query = "select R_P.id_reparation,R_P.id_utilisateur,R_P.id_utilisateur,R_P.id_bon_entree,R_P.id_bon_entree_produit,R_P.avancement,R_P.prevu,R_P.dates,R_P.step,R_P.technicien,R_P.num_be,R_P.client,R_P.reference,R_P.designation,\r\n"
        		+ "  CASE WHEN R_P.avancement = 0 THEN 0\r\n"
        		+ "       WHEN R_P.avancement > 0 AND R_P.avancement < 100 THEN 1\r\n"
        		+ "       WHEN R_P.avancement = 100  THEN 2 END\r\n"
        		+ "       AS etat\r\n"
        		+ "  FROM R_P;";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Reparation_Front> detail = new ArrayList<Reparation_Front>();
        while(rs.next()){
        	Reparation_Front temp = new Reparation_Front();
        	temp.setId_reparation(rs.getInt("id_reparation"));
        	temp.setId_utilisateur(rs.getInt("id_utilisateur"));
        	temp.setId_bon_entree(rs.getInt("id_bon_entree"));
        	temp.setId_bon_entree_produit(rs.getInt("id_bon_entree_produit"));
        	temp.setClient(rs.getString("client"));
        	temp.setAvancement(rs.getInt("avancement"));
        	temp.setPrevu(rs.getString("prevu"));
        	temp.setDates(rs.getString("dates"));
        	temp.setStep(rs.getString("step"));
        	temp.setTechnicien(rs.getString("technicien"));
        	temp.setNum_be(rs.getString("num_be"));
        	temp.setDesigantion(rs.getString("designation"));
        	temp.setEtat(rs.getInt("etat"));
            detail.add(temp);
        }
        return detail;
	}
	
	public static Reparation_Front GetDetailReparation(Connection co, int id_reparation) throws ClassNotFoundException, Exception{
        String query = "Select * from R_P where id_reparation="+id_reparation;
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        Reparation_Front detail = new Reparation_Front();
        while(rs.next()){
        	detail.setId_reparation(rs.getInt("id_reparation"));
        	detail.setId_utilisateur(rs.getInt("id_utilisateur"));
        	detail.setId_bon_entree(rs.getInt("id_bon_entree"));
        	detail.setId_bon_entree_produit(rs.getInt("id_bon_entree_produit"));
        	detail.setClient(rs.getString("client"));
        	detail.setAvancement(rs.getInt("avancement"));
        	detail.setPrevu(rs.getString("prevu"));
        	detail.setDates(rs.getString("dates"));
        	detail.setStep(rs.getString("step"));
        	detail.setTechnicien(rs.getString("technicien"));
        	detail.setNum_be(rs.getString("num_be"));
        	detail.setDesigantion(rs.getString("designation"));
        	detail.setReference(rs.getString("reference"));
        }
        return detail;
	}
	
	
	public static Reparation_Front GetDetailReparationByFacture(Connection co,String reparation) throws ClassNotFoundException, Exception{
        String query = "Select * from R_P where num_be='"+reparation+"'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        Reparation_Front detail = new Reparation_Front();
        while(rs.next()){
        	detail.setId_reparation(rs.getInt("id_reparation"));
        	detail.setId_utilisateur(rs.getInt("id_utilisateur"));
        	detail.setId_bon_entree(rs.getInt("id_bon_entree"));
        	detail.setId_bon_entree_produit(rs.getInt("id_bon_entree_produit"));
        	detail.setClient(rs.getString("client"));
        	detail.setAvancement(rs.getInt("avancement"));
        	detail.setPrevu(rs.getString("prevu"));
        	detail.setDates(rs.getString("dates"));
        	detail.setStep(rs.getString("step"));
        	detail.setTechnicien(rs.getString("technicien"));
        	detail.setNum_be(rs.getString("num_be"));
        	detail.setDesigantion(rs.getString("designation"));
        	detail.setReference(rs.getString("reference"));
        }
        return detail;
	}
	

	public static ArrayList<Reparation_Front> GetClientReparation(Connection co, String client) throws ClassNotFoundException, Exception{
        String query = "Select * from R_P where client='"+ client + "';";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        ArrayList<Reparation_Front> temp = new ArrayList<Reparation_Front>();
        while(rs.next()){
        	Reparation_Front detail = new Reparation_Front();
        	detail.setId_reparation(rs.getInt("id_reparation"));
        	detail.setId_utilisateur(rs.getInt("id_utilisateur"));
        	detail.setId_bon_entree(rs.getInt("id_bon_entree"));
        	detail.setId_bon_entree_produit(rs.getInt("id_bon_entree_produit"));
        	detail.setClient(rs.getString("client"));
        	detail.setAvancement(rs.getInt("avancement"));
        	detail.setPrevu(rs.getString("prevu"));
        	detail.setDates(rs.getString("dates"));
        	detail.setStep(rs.getString("step"));
        	detail.setTechnicien(rs.getString("technicien"));
        	detail.setNum_be(rs.getString("num_be"));
        	detail.setDesigantion(rs.getString("designation"));
        	detail.setReference(rs.getString("reference"));
        	temp.add(detail);
        }
        return temp;
	}
	
	public static int En_cours(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT count(avancement)as en_cours FROM (select avancement,dates  FROM R_P WHERE avancement <100 AND EXTRACT(MONTH FROM dates) ="+ month +"  AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)) AS t";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("en_cours");
        }
        return result;
	}
	
	public static int Terminer(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT count(avancement)as termine FROM (select avancement,dates  FROM R_P WHERE avancement =100 AND EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)) AS t";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("termine");
        }
        return result;
	}
	
	public static int Total(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS total FROM R_P WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("total");
        }
        return result;
	}
	
}
