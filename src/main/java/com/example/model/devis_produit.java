package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.exemple.service.GenericDao;

public class devis_produit {
	public int id;
	public String num_facture;
	public String designation;
	public int quantite;
	public float prix_unitaire;
	public int remise;
	public float prix_total;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum_facture() {
		return num_facture;
	}
	public void setNum_facture(String num_facture) {
		this.num_facture = num_facture;
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
	public float getPrix_unitaire() {
		return prix_unitaire;
	}
	public void setPrix_unitaire(float prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}
	public int getRemise() {
		return remise;
	}
	public void setRemise(int remise) {
		this.remise = remise;
	}
	public float getPrix_total() {
		return prix_total;
	}
	public void setPrix_total(float prix_total) {
		this.prix_total = prix_total;
	}
	public devis_produit() {}
	public devis_produit(int id,String num_fact,String designation,int quantite,float prix_unitaire,int remise,float prix_total) {
		this.setId(id);
		this.setNum_facture(num_fact);
		this.setDesignation(designation);
		this.setQuantite(quantite);
		this.setPrix_unitaire(prix_unitaire);
		this.setRemise(remise);
		this.setPrix_total(prix_total);
	}
	
	public devis_produit(String num_fact,String designation,int quantite,float prix_unitaire,int remise,float prix_total) {
		this.setNum_facture(num_fact);
		this.setDesignation(designation);
		this.setQuantite(quantite);
		this.setPrix_unitaire(prix_unitaire);
		this.setRemise(remise);
		this.setPrix_total(prix_total);
	}
	

	
	public static void save(Connection co,devis_produit[] newD) throws Exception {
		PreparedStatement pst=null;
		try{
		
			for(int i=0;i<newD.length;i++) {
			  pst=co.prepareStatement("INSERT INTO devis_produit(num_facture,designation,quantite,prix_unitaire,remise,prix_total) VALUES(?,?,?,?,?,?)");
	          pst.setString(1,newD[i].getNum_facture());
	          pst.setString(2,newD[i].getDesignation());
	          pst.setInt(3,newD[i].getQuantite());
	          pst.setFloat(4,newD[i].getPrix_unitaire());
	          pst.setInt(5,newD[i].getRemise());
	          pst.setFloat(6,newD[i].getPrix_total());
	          System.out.println(pst);
	          pst.executeUpdate();	
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
	
	public static ArrayList<devis_produit> GetAll(Connection co,String facture) throws ClassNotFoundException, Exception{
        String query = "Select * from devis_produit WHERE num_facture='"+facture+"'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<devis_produit> detail = new ArrayList<devis_produit>();
        while(rs.next()){
        	devis_produit temp = new devis_produit();
        	temp.setId(rs.getInt("id"));
        	temp.setNum_facture(rs.getString("num_facture"));
        	temp.setDesignation(rs.getString("designation"));
        	temp.setQuantite(rs.getInt("quantite"));
        	temp.setPrix_unitaire(rs.getFloat("prix_unitaire"));
        	temp.setRemise(rs.getInt("remise"));
        	temp.setPrix_total(rs.getFloat("prix_total"));
            detail.add(temp);
        }
		co.close();
        return detail;
	  }
	
	public static Float GetSumPrice(Connection co,String facture) throws ClassNotFoundException, Exception{
        String query = "Select SUM(prix_total) as montant_total from devis_produit WHERE num_facture='"+facture+"'";
        System.out.println(query);
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        Float sum = (float) 0;
        while(rs.next()){
        	sum = rs.getFloat("montant_total");
        }
        co.close();
        return sum;
	  }
}
