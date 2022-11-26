package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class devis_client {
	
	public String num_facture;
	public String dates; 
	public String etat;
	public int tech_id;
	public String tech;
	public String tech_mail;
	public String tech_phone;
	public String client;
	public String admin;
    public String contact;
	public String email;
    public String object;
    public String valide;
    public String disponibilite;
    public String reglement;
    public String preconisation;
	public int garanti;
    public String nif;
	public String stat;
	public String rcs;
	public String adress;
	public String cin;
	public String permis;
	public String passport;
	public boolean particulier;
   
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getStat() {
		return stat;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
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


	public String getNum_facture() {
		return num_facture;
	}
	public void setNum_facture(String num_facture) {
		this.num_facture = num_facture;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public int getTech_id() {
		return tech_id;
	}
   public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}
	public String getTech_mail() {
		return tech_mail;
	}
	public void setTech_mail(String tech_mail) {
		this.tech_mail = tech_mail;
	}
	public String getTech_phone() {
		return tech_phone;
	}
	public void setTech_phone(String tech_phone) {
		this.tech_phone = tech_phone;
	}
	public void setTech_id(int tech_id) {
		this.tech_id = tech_id;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

    public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
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
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getValide() {
		return valide;
	}
	public void setValide(String valide) {
		this.valide = valide;
	}
	public String getDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}
	public String getReglement() {
		return reglement;
	}
	public void setReglement(String reglement) {
		this.reglement = reglement;
	}
	public String getPreconisation() {
		return preconisation;
	}
	public void setPreconisation(String preconisation) {
		this.preconisation = preconisation;
	}
	public int getGaranti() {
		return garanti;
	}
	public void setGaranti(int garanti) {
		this.garanti = garanti;
	}
	public devis_client() {}
	
	public devis_client(String facture,String date,String etat,int tech_id,String tech,String tech_mail,String tech_phone,String client,String adress,String admin,String contact,String mail,String object,String valide,String disponibilite,String reglement,String preconisation,int garanti,String nif,String stat,String rcs,String cin,String permis,String passport,boolean particulier) {
		this.setNum_facture(facture);
		this.setDates(date);
		this.setEtat(etat);
		this.setTech_id(tech_id);
		this.setTech(tech);
		this.setTech_mail(tech_mail);
		this.setTech_phone(tech_phone);
		this.setClient(client);
		this.setAdress(adress);
		this.setAdmin(admin);
		this.setContact(contact);
		this.setEmail(mail);
		this.setObject(object);
		this.setValide(valide);
		this.setDisponibilite(disponibilite);
		this.setReglement(reglement);
		this.setPreconisation(preconisation);
		this.setGaranti(garanti);
		this.setNif(nif);
		this.setStat(stat);
		this.setRcs(rcs);
		this.setCin(cin);
		this.setPermis(permis);
		this.setPassport(passport);
		this.setParticulier(particulier);
	}
	
	

	public static devis_client getdetail_devis(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    devis_client user = new devis_client();
	    try{
	      pst=con.prepareStatement("select * from devis_client where num_facture=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	          user = new devis_client(result.getString("num_facture"),result.getDate("dates").toString(),result.getString("etat"),result.getInt("tech_id"),result.getString("tech"),result.getString("tech_mail"),result.getString("tech_contact"),result.getString("nom"),result.getString("adresse"),result.getString("admins"),result.getString("contact"),result.getString("email"),result.getString("objet"),result.getString("valide"),result.getDate("disponibilite").toString(),result.getString("reglement"),result.getString("preconisaiton"),result.getInt("garanti"),result.getString("nif"),result.getString("stat"),result.getString("rcs"),result.getString("cin"),result.getString("permis"),result.getString("passeport"),result.getBoolean("particulier"));
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
	
	public static ArrayList<devis_client> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from devis_client ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<devis_client> detail = new ArrayList<devis_client>();
        while(rs.next()){
        	devis_client temp = new devis_client();
        	temp.setNum_facture(rs.getString("num_facture"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setEtat(rs.getString("etat"));
        	temp.setTech_id(rs.getInt("tech_id"));
        	temp.setTech(rs.getString("tech"));
        	temp.setTech_mail(rs.getString("tech_mail"));
        	temp.setTech_phone(rs.getString("tech_contact"));
    		temp.setClient(rs.getString("nom"));
    		temp.setAdmin(rs.getString("admins"));
    		temp.setContact(rs.getString("contact"));
    		temp.setEmail(rs.getString("email"));
    		temp.setObject(rs.getString("objet"));
    		temp.setValide(rs.getString("valide"));
    		temp.setDisponibilite(rs.getDate("disponibilite").toString());
    		temp.setReglement(rs.getString("reglement"));
    		temp.setPreconisation(rs.getString("preconisaiton"));
    		temp.setGaranti(rs.getInt("garanti"));
    		temp.setNif(rs.getString("nif"));
    		temp.setStat(rs.getString("stat"));
    		temp.setRcs(rs.getString("rcs"));
    		temp.setCin(rs.getString("cin"));
    		temp.setPermis(rs.getString("permis"));
    		temp.setPassport(rs.getString("passeport"));
    		temp.setParticulier(rs.getBoolean("particulier"));	
    		
            detail.add(temp);
        }
        return detail;
	  }
	
}
