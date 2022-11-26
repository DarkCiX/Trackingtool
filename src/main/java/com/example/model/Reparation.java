package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Reparation {
	public int id_reparation;
	public int id_utilisateur;
	public int id_bon_entree;
	public int id_bon_entree_produit;
	public String prevu;
	public String dates;
	public String step;
	public int avancement;
	
	public Reparation(int id_reparation, int id_utilisateur, int id_bon_entree, int id_bon_entree_produit,String prevu,String dates,String step,int avancement) {
		super();
		this.id_reparation = id_reparation;
		this.id_utilisateur = id_utilisateur;
		this.id_bon_entree = id_bon_entree;
		this.id_bon_entree_produit = id_bon_entree_produit;
		this.prevu = prevu;
		this.dates = dates;
		this.step = step;
		this.avancement = avancement;
	}
	public Reparation() {}
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
	public int getAvancement() {
		return avancement;
	}
	public void setAvancement(int avancement) {
		this.avancement = avancement;
	}
	
	public static void Save(Connection co,String id_utilisateur,String identree,String[] idproduit,String date) throws Exception {
		PreparedStatement pst=null;
		try{
          for(int i=0;i<idproduit.length;i++) {
        	  System.out.println("Produit" + idproduit[i]);
        	  pst=co.prepareStatement("INSERT INTO reparation(id_utilisateur,id_bon_entree,id_bon_entree_produit,avancement,prevu,dates) VALUES(?,?,?,0,'0 jours',to_date(?,'YYYY-MM-DD'))");
        	  pst.setInt(1,Integer.parseInt(id_utilisateur));
        	  pst.setInt(2,Integer.parseInt(identree));
        	  pst.setInt(3,Integer.parseInt(idproduit[i]));
        	  pst.setString(4,date);
        	  pst.executeUpdate();	
        	  System.out.println(pst);
			}

        }
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		finally{
			pst.close();
		}
	}
	
	public static void UpdateAvancement(Connection co, int avancement,int prevu,String step, int id) {
		String query = "UPDATE reparation " + "SET avancement= ? ,  prevu= ? ,  step=? "+ "WHERE id_reparation = ?";
		String prevoir = prevu + " jours";
	      try{
	      
		  	 PreparedStatement pst = co.prepareStatement(query);
		  	 System.out.println(pst);
		       pst.setInt(1, avancement);
		       pst.setString(2, prevoir);
		       pst.setString(3, step);
		       pst.setInt(4, id);
		       pst.executeUpdate();
		
		   } catch (SQLException ex) {
		       ex.printStackTrace();
		   }
	}
	
	
	
	public static double StatistiqueMonthlyTerminated(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS termined FROM Reparation  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM now()) AND avancement=100";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("termined");
        }
        return result;
	}
	
	public static double[] StatistiqueTermined(Connection co) throws ClassNotFoundException, Exception {
		int i = 12;
		double[] result = new double[12];
		for(int j =0;j<i;j++) {
			result[j] = StatistiqueMonthlyTerminated(co,j+1);
		}
		return result;
	}
	
	public static double StatistiqueMonthlyEncours(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS progress FROM Reparation  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM now()) AND avancement>0 AND avancement<100";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("progress");
        }
        return result;
	}
	
	public static double[] StatistiqueInprogress(Connection co) throws ClassNotFoundException, Exception {
		int i = 12;
		double[] result = new double[12];
		for(int j =0;j<i;j++) {
			result[j] = StatistiqueMonthlyEncours(co,j+1);
		}
		return result;
	}
	
	public static double StatistiqueMonthlyRepair(Connection co, int month) throws ClassNotFoundException, Exception{
        String query = "SELECT COUNT(*) AS repair FROM Reparation  WHERE EXTRACT(MONTH FROM dates) ="+month+" AND EXTRACT(YEAR FROM dates)= EXTRACT(YEAR FROM now()) AND avancement=0";
        Statement st = co.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println(query);
        int result = 0;
        while(rs.next()){
        	result = rs.getInt("repair");
        }
        return result;
	}
	
	public static double[] StatistiqueRepair(Connection co) throws ClassNotFoundException, Exception {
		int i = 12;
		double[] result = new double[12];
		for(int j =0;j<i;j++) {
			result[j] = StatistiqueMonthlyRepair(co,j+1);
		}
		return result;
	}
	
	
	
	
	
}
