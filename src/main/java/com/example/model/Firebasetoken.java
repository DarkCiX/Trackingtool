package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.helpers.DbHelper;

public class Firebasetoken {

	String email;
	String token;
	
	public Firebasetoken(String email, String token) {
		super();
		this.email = email;
		this.token = token;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	public void insert(Firebasetoken note, Connection con) throws Exception {
		PreparedStatement pst=null;
		try{
			
		  Utilisateur util=new Utilisateur();
		  util=util.getUtilisateurByMail(DbHelper.connect(), this.email);
		  if(FindByMT(con,note.getEmail(),getToken()).size()==0) {
			  pst=con.prepareStatement("INSERT INTO firebasetoken(email,token) VALUES(?,?)");
			  pst.setString(1,note.getEmail());
			  pst.setString(2,note.getToken());
	          pst.executeUpdate();
		  }
		
          
        }
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			pst.close();
			con.close();
		}
		
	}
	
	
	public void deleteToken(String token,Connection con) {
		PreparedStatement pst=null;
		try{
			
		  Utilisateur util=new Utilisateur();
		  util=util.getUtilisateurByMail(DbHelper.connect(), this.email);
		 
		  pst=con.prepareStatement("delete from firebasetoken where token=?");
		  pst.setString(1,token);
          pst.executeUpdate();
          
        }
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				pst.close();
				con.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public List<Firebasetoken> Find(Connection con,String email) {
		List<Firebasetoken> fbt=new ArrayList<Firebasetoken>();
		  Statement st=null;
		try {
				String query = "SELECT * from firebasetoken where email='"+email+"'";
		        st = con.createStatement();
		        ResultSet rs = st.executeQuery(query);
		        while(rs.next()){
		        	Firebasetoken temp=new Firebasetoken(rs.getString("email"),rs.getString("token"));
		    		fbt.add(temp);
		        }
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return fbt;
	}
	
	public List<Firebasetoken> FindByMT(Connection con,String email,String token) {
		List<Firebasetoken> fbt=new ArrayList<Firebasetoken>();
		  Statement st=null;
		try {
				String query = "SELECT * from firebasetoken where email='"+email+"' and token='"+token+"'";
		        st = con.createStatement();
		        ResultSet rs = st.executeQuery(query);
		        while(rs.next()){
		        	Firebasetoken temp=new Firebasetoken(rs.getString("email"),rs.getString("token"));
		    		fbt.add(temp);
		        }
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return fbt;
	}
}
