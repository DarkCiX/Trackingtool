package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Detail_devis {
	public String num_facture;
	public String dates; 
	public int tech_id;
	public String etat;
	public int client_id;
    public String object;
    public String valide;
    public String disponibilite;
    public String reglement;
    public String preconisation;
    public int garanti;
    
	public String getDates() {
		return dates;
	}
	public void setDates(String date) {
		this.dates = date;
	}
    public String getNum_facture() {
		return num_facture;
	}
	public void setNum_facture(String num_facture) {
		this.num_facture = num_facture;
	}
	public int getTech_id() {
		return tech_id;
	}
    public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public void setTech_id(int tech_id) {
		this.tech_id = tech_id;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
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
	
	public Detail_devis() {}
	
	public Detail_devis(String facture,String date,String etat,int tech_id,int client_id,String object,String valide,String disponibilite,String reglement,String preconisation,int garanti) {
		this.setNum_facture(facture);
		this.setDates(date);
		this.setEtat(etat);
		this.setTech_id(tech_id);
		this.setClient_id(client_id);
		this.setObject(object);
		this.setValide(valide);
		this.setDisponibilite(disponibilite);
		this.setReglement(reglement);
		this.setPreconisation(preconisation);
		this.setGaranti(garanti);
	}
	

	
	public static void save(Connection co,Detail_devis newD,devis_produit[] produit) throws Exception {
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		try{
		  pst=co.prepareStatement("INSERT INTO devis_detail(dates,tech_id,client_id,objet,valide,disponibilite,reglement,preconisaiton,garanti) VALUES(to_date(?,'YYYY-MM-DD'),?,?,?,?,to_date(?,'YYYY-MM-DD'),?,?,?)");
          pst.setString(1,newD.getDates());
          pst.setInt(2,newD.getTech_id());
          pst.setInt(3,newD.getClient_id());
          pst.setString(4,newD.getObject());
          pst.setString(5,newD.getValide());
          pst.setString(6,newD.getDisponibilite());
          pst.setString(7,newD.getReglement());
          pst.setString(8,newD.getPreconisation());
          pst.setInt(9,newD.getGaranti());
          System.out.println(pst);
          pst.executeUpdate();
          
          Detail_devis numero = getMaxId_devis(co);
          
          for(int i=0;i<produit.length;i++) {
			  pst2=co.prepareStatement("INSERT INTO devis_produit(num_facture,designation,quantite,prix_unitaire,remise,prix_total) VALUES(?,?,?,?,?,?)");
			  pst2.setString(1,numero.getNum_facture());
			  pst2.setString(2,produit[i].getDesignation());
	          pst2.setInt(3,produit[i].getQuantite());
	          pst2.setFloat(4,produit[i].getPrix_unitaire());
	          pst2.setInt(5,produit[i].getRemise());
	          pst2.setFloat(6,(produit[i].getQuantite()* produit[i].getPrix_unitaire()));
	          System.out.println(pst2);
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
	
	public static Detail_devis  getMaxId_devis(Connection con) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    Detail_devis user = new Detail_devis();
	    try{
	      pst=con.prepareStatement("select*from devis_detail where id_devis=(select Max(id_devis) from devis_detail);");
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	    	  user =  new Detail_devis(result.getString("num_facture"),result.getDate("dates").toString(),result.getString("etat"),result.getInt("tech_id"),result.getInt("client_id"),result.getString("objet"),result.getString("valide"),result.getDate("disponibilite").toString(),result.getString("reglement"),result.getString("preconisaiton"),result.getInt("garanti"));
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

	public static void Update(Connection co,String facture) {
		 String query = "UPDATE devis_detail " + "SET etat = ? "+ "WHERE num_facture  = ?";
        try{
           
       	 PreparedStatement pst = co.prepareStatement(query);
            pst.setInt(1, 1);
            pst.setString(2, facture);
            System.out.print(pst);
            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
	}
	
	   public static void DeleteDevis(Connection con,String facture) {
				String query = "DELETE FROM  devis_detail  WHERE num_facture = ?";
				String query2 = "DELETE FROM  devis_produit  WHERE num_facture = ?"; 
		        try{
		        	PreparedStatement pst2 = con.prepareStatement(query2);
		            pst2.setString(1, facture);
		            pst2.executeUpdate();
		            
		        	
		       	 	PreparedStatement pst = con.prepareStatement(query);
		            pst.setString(1, facture);
		            pst.executeUpdate();
		            
		            

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
			}

	public static Detail_devis getdetail_devis(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    Detail_devis user = new Detail_devis();
	    try{
	      pst=con.prepareStatement("select * from devis_detail where num_facture=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	          user = new Detail_devis(result.getString("num_facture"),result.getDate("dates").toString(),result.getString("etat"),result.getInt("tech_id"),result.getInt("client_id"),result.getString("objet"),result.getString("valide"),result.getDate("disponibilite").toString(),result.getString("reglement"),result.getString("preconisaiton"),result.getInt("garanti"));
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
	
	public static ArrayList<Detail_devis> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from devis_detail ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Detail_devis> detail = new ArrayList<Detail_devis>();
        while(rs.next()){
        	Detail_devis temp = new Detail_devis();
        	temp.setNum_facture(rs.getString("num_facture"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setEtat(rs.getString("etat"));
        	temp.setTech_id(rs.getInt("tech_id"));
    		temp.setClient_id(rs.getInt("client_id"));
    		temp.setObject(rs.getString("objet"));
    		temp.setValide(rs.getString("valide"));
    		temp.setDisponibilite(rs.getDate("disponibilite").toString());
    		temp.setReglement(rs.getString("reglement"));
    		temp.setPreconisation(rs.getString("preconisaiton"));
    		temp.setGaranti(rs.getInt("garanti"));
            detail.add(temp);
        }
        return detail;
	  }
	
	public static int En_cours(Connection co, int month) throws ClassNotFoundException, Exception{
        String query =   "SELECT count(etat) as en_cours FROM (select etat,dates  FROM devis_client WHERE etat ='0' AND EXTRACT(MONTH FROM dates) ="+month +" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)) AS t";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("en_cours");
        }
        return result;
	}
	
	public static int Valider(Connection co, int month) throws ClassNotFoundException, Exception{
        String query =   "SELECT count(etat) as accept FROM (select etat,dates  FROM devis_client WHERE etat ='1' AND EXTRACT(MONTH FROM dates) ="+month +" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)) AS t";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("accept");
        }
        return result;
	}
	
	public static int Total(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS total FROM devis_client WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE)";
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
