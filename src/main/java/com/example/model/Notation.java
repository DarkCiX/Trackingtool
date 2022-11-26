package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.helpers.DbHelper;

public class Notation {

	Integer id;
	Integer idutilisateur;
	Integer note;
	String commentaire;
	String datenotation;
	String email;
	Client client;
	
	
	
	
	
	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public Notation(Integer id, Integer idutilisateur, Integer note, String commentaire, String datenotation) {
		super();
		this.id = id;
		this.idutilisateur = idutilisateur;
		this.note = note;
		this.commentaire = commentaire;
		this.datenotation = datenotation;
		
	}
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Notation() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdutilisateur() {
		return idutilisateur;
	}
	public void setIdutilisateur(Integer idutilisateur) {
		this.idutilisateur = idutilisateur;
		try {
			
			Connection con = DbHelper.connect();
			this.client=new Client();
			this.client=this.client.FindClient(String.valueOf(this.idutilisateur), con);
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
	}
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getDatenotation() {
		return datenotation;
	}
	public void setDatenotation(String datenotation) {
		this.datenotation = datenotation;
	}
	
	
	public void insert(Notation note, Connection con) throws Exception {
		PreparedStatement pst=null;
		try{
			
		  Utilisateur util=new Utilisateur();
		  util=util.getUtilisateurByMail(DbHelper.connect(), this.email);
		  
		  pst=con.prepareStatement("INSERT INTO notation(idutilisateur,note,commentaire) VALUES(?,?,?)");
		  if(note.getIdutilisateur()==null) {
			  pst.setInt(1,util.getId_client());

		  }
		  else {
			  pst.setInt(1,note.getIdutilisateur());

		  }
		  pst.setInt(2,note.getNote());
		  pst.setString(3,note.getCommentaire());
        
          System.out.println(pst);
          pst.executeUpdate();
          
        }
		catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			pst.close();
			con.close();
		}
		
	}
	
	public List<Notation> Find(Connection con) {
		List<Notation> note=new ArrayList<Notation>();
		  Statement st=null;
		try {
				String query = "SELECT * from notationView order by datenotation desc";
		        st = con.createStatement();
		        ResultSet rs = st.executeQuery(query);
		        while(rs.next()){
		        	Notation temp=new Notation();
		        	temp.setIdutilisateur(rs.getInt("idutilisateur"));
		        	temp.setId(rs.getInt("id"));
		        	temp.setNote(rs.getInt("note"));
		        	temp.setCommentaire(rs.getString("commentaire"));
		        	temp.setDatenotation(rs.getDate("datenotation").toString());
		    		note.add(temp);
		        }
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return note;
	}
	
	public Notation FindByMail(Connection con,String mail) {
		Notation note=new Notation();
		Statement st=null;
		try {
				Utilisateur util=new Utilisateur();
				util=util.getUtilisateurByMail(con, mail);
				
				String query = "SELECT * from notation where idutilisateur='"+util.getId_client()+"' order by datenotation desc limit 1";
		        st = con.createStatement();
		        ResultSet rs = st.executeQuery(query);
		        while(rs.next()){
		        	note.setIdutilisateur(rs.getInt("idutilisateur"));
		        	note.setId(rs.getInt("id"));
		        	note.setNote(rs.getInt("note"));
		        	note.setCommentaire(rs.getString("commentaire"));
		        	note.setDatenotation(rs.getDate("datenotation").toString());
		        }
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		finally {
			
			try {
				st.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return note;
	}
	
	
	
	
	
	
	
}
