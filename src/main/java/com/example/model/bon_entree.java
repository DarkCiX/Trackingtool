package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class bon_entree {
	public int id_entree;
	public String num_be;
	public String dates; 
	public String fournisseur;
	public String phone;
	public String etat;
	public int id_client;
	public String motif;
	public int reparation;
	public int sortie;
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
	public String getNum_achat() {
		return num_achat;
	}
	public void setNum_achat(String num_achat) {
		this.num_achat = num_achat;
	}
	public int getId_entree() {
		return id_entree;
	}
	public void setId_entree(int id_entree) {
		this.id_entree = id_entree;
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
	
	public bon_entree() {}
	public bon_entree(int id_entree,String num_be,String dates,String fournisseur,String phone,String etat,int reparation,int sortie,int id_client,String motif,String recepteur,String observation,String sign_recepteur,String sign_client,String num_achat) {
		this.setId_entree(id_entree);
		this.setNum_be(num_be);
		this.setDates(dates);
		this.setFournisseur(fournisseur);
		this.setPhone(phone);
		this.setEtat(etat);
		this.setReparation(reparation);
		this.setSortie(sortie);
		this.setId_client(id_client);
		this.setMotif(motif);
		this.setRecepteur(recepteur);	
		this.setObservation(observation);
		this.setSign_recepteur(sign_recepteur);
		this.setSign_client(sign_client);
		this.setNum_achat(num_achat);
	}
	
	public static void Save(Connection co,bon_entree newD,bon_entree_produit[] produit) throws Exception {
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		try{
		  pst=co.prepareStatement("INSERT INTO bon_entree(dates,fournisseur,phone,etat,id_client,motif,recepteur,observation,sign_recepteur,sign_client,num_achat) VALUES(to_date(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?)");
          pst.setString(1,newD.getDates());
          pst.setString(2,newD.getFournisseur());
          pst.setString(3,newD.getPhone());
          pst.setString(4,newD.getEtat());
          pst.setInt(5,newD.getId_client());
          pst.setString(6,newD.getMotif());
          pst.setString(7,newD.getRecepteur());
          pst.setString(8,newD.getObservation());
          pst.setString(9,newD.getSign_recepteur());
          pst.setString(10,newD.getSign_client());
          pst.setString(11,newD.getNum_achat());
          System.out.println(pst);
          pst.executeUpdate();
          
         
          for(int i=0;i<produit.length;i++) {
        	  bon_entree  entree = getMaxId_entree(co);
			  pst2=co.prepareStatement("INSERT INTO bon_entree_produit(id_bon_entre,num_be,reference,designation,quantite) VALUES(?,?,?,?,?)");
			  pst2.setInt(1,entree.getId_entree()); 
	          pst2.setString(2,entree.getNum_be());
	          pst2.setString(3,produit[i].getReference());
	          pst2.setString(4,produit[i].getDesignation());
	          pst2.setInt(5,produit[i].getQuantite());
//	          entree.setId_entree(i);entree.getId_entree() =1;
	          System.out.println(pst2);
	          pst2.executeUpdate();	
			}

        }
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			pst.close();
		}
	}
	
	public static void DeleteBon_entree(Connection con,String id_entre) {
		String query = "DELETE FROM  bon_entree  WHERE id_entree = ?";
		String query2 = "DELETE FROM  bon_entree_produit  WHERE id_bon_entre = ?"; 
        try{
        	int id = Integer.parseInt(id_entre);
        	PreparedStatement pst2 = con.prepareStatement(query2);
            pst2.setInt(1, id);
            pst2.executeUpdate();
            
        	
       	 	PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	public static bon_entree  getMaxId_entree(Connection con) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_entree user = new bon_entree();
	    try{
	      pst=con.prepareStatement("select*from bon_entree where id_entree=(select Max(id_entree) from bon_entree);");
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	    	  user =   new bon_entree(result.getInt("id_entree"),result.getString("num_be"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getInt("reparation"),result.getInt("sortie"),result.getInt("id_client"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_recepteur"),result.getString("sign_client"),result.getString("num_achat"));
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
	public static ArrayList<bon_entree> GetAll(Connection co) throws ClassNotFoundException, Exception{
        String query = "Select * from bon_entree ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<bon_entree> detail = new ArrayList<bon_entree>();
        while(rs.next()){
        	bon_entree temp = new bon_entree();
        	temp.setId_entree(rs.getInt("id_entree"));
        	temp.setNum_be(rs.getString("num_be"));
        	temp.setDates(rs.getDate("dates").toString());
        	temp.setFournisseur(rs.getString("fournisseur")); 
        	temp.setPhone(rs.getString("phone"));
        	temp.setEtat(rs.getString("etat"));
        	temp.setId_client(rs.getInt("id_client"));
        	temp.setMotif(rs.getString("motif"));
        	temp.setRecepteur(rs.getString("recepteur"));
        	temp.setObservation(rs.getString("observation"));
        	temp.setSign_recepteur(rs.getString("sign_recepteur"));
        	temp.setSign_client(rs.getString("sign_client"));
        	temp.setNum_achat(rs.getString("num_achat"));
            detail.add(temp);
        }
        return detail;
	}
	
	public static void UpdateBE(Connection co,int facture) {
		 String query = "UPDATE bon_entree " + "SET etat = ? "+ "WHERE id_entree = ?";
         try{
            
        	 PreparedStatement pst = co.prepareStatement(query);
             pst.setString(1, "1");
             pst.setInt(2, facture);
             System.out.print(pst);
             pst.executeUpdate();

         } catch (SQLException ex) {
             ex.printStackTrace();
         }
	}
	
	public static void setReparationvalide(Connection co,int id) {
		 String query = "UPDATE bon_entree " + "SET reparation = ? "+ "WHERE id_entree = ?";
        try{
       	 	PreparedStatement pst = co.prepareStatement(query);
            pst.setInt(1, 1);
            pst.setInt(2, id);
            System.out.print(pst);
            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	
	public static void setSortievalide(Connection co,String facture) {
		 String query = "UPDATE bon_entree " + "SET sortie = ? "+ "WHERE num_be = ?";
       try{
          
      	   PreparedStatement pst = co.prepareStatement(query);
           pst.setInt(1, 1);
           pst.setString(2, facture);
           System.out.print(pst);
           pst.executeUpdate();

       } catch (SQLException ex) {
           ex.printStackTrace();
       }
	}
	
	public static void  ModifiBE(Connection co,bon_entree newD,bon_entree_produit[] produit){
		
		String SqlUpdateDetail = "UPDATE bon_entree SET dates = to_date(?,'YYYY-MM-DD'), recepteur  = ?, id_client = ? , motif= ? ,observation = ?, num_achat=? WHERE id_entree = ?";
		String SqlUpdateProduit = "UPDATE bon_entree_produit SET reference = ?, designation  = ?, quantite = ?  WHERE id_bon_entre = ?";
		try {
			PreparedStatement pst = co.prepareStatement(SqlUpdateDetail);
            pst.setString(1, newD.getDates());
            pst.setString(2, newD.getRecepteur());
            pst.setInt(3, newD.getId_client());
            pst.setString(4, newD.getMotif());
            pst.setString(5, newD.getObservation());
            pst.setString(6, newD.getNum_achat());
            pst.setInt(7, newD.getId_entree());
            pst.executeUpdate();
            
            for(int i=0;i<produit.length;i++) {
            	PreparedStatement pst2 = co.prepareStatement(SqlUpdateProduit);
                pst2.setString(1, produit[i].getReference());
                pst2.setString(2, produit[i].getDesignation());
                pst2.setInt(3, produit[i].getQuantite());
                pst2.setInt(4, newD.getId_entree());
                pst2.executeUpdate();
            }
            
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static bon_entree getdetail_bon_entree(Connection con,String num_facture) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_entree user = new bon_entree();
	    try{
	      pst=con.prepareStatement("select * from bon_entree where num_be=?");
	      pst.setString(1,num_facture);
	      System.out.println(pst);
	
	      result=pst.executeQuery();
	      while(result.next()){
	          user = new bon_entree(result.getInt("id_entree"),result.getString("num_be"),result.getDate("dates").toString(),result.getString("fournisseur"),result.getString("phone"),result.getString("etat"),result.getInt("reparation"),result.getInt("sortie"),result.getInt("id_client"),result.getString("motif"),result.getString("recepteur"),result.getString("observation"),result.getString("sign_recepteur"),result.getString("sign_client"),result.getString("num_achat"));
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
	
	public static boolean CheckIfExist(Connection con, int id) throws Exception {
		PreparedStatement pst=null;
	    ResultSet result=null;
	    bon_entree user = new bon_entree();
	    boolean rep = false;
	    try{
	      pst=con.prepareStatement("select exists (select id_bon_entree from r_u where id_bon_entree=?)");
	      pst.setInt(1, id);
	
	      result=pst.executeQuery();
	      while(result.next()){
	    	  rep = result.getBoolean("exists");
	      }
	    }
		catch(Exception ex){
		 throw ex;
		}
		finally{
			result.close();
			pst.close();
		}
	  return rep;
	}
	
	public static double StatistiqueBEvalide(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS number FROM bon_entree  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE) AND etat='1'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("number");
        }
        return result;
	}
	
	
	public static double StatistiqueNOBEvalide(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS number FROM bon_entree  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE) AND etat='0'";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("number");
        }
        return result;
	}
	

	public static double StatistiqueTotalBE(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS number FROM bon_entree  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM CURRENT_DATE) ";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("number");
        }
        return result;
	}
}
