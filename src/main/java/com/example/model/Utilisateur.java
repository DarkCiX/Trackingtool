package com.example.model;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


import com.example.helpers.DbHelper;

public class Utilisateur {
	public int id;
	public int id_type_user;
	public int id_client;
	public String nom;
	public String email;
	public String mdp;
	public String telephone;
	
	
	private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
  
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_type_user() {
		return id_type_user;
	}
	public void setId_type_user(int id_type_user) {
		this.id_type_user = id_type_user;
	}
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Utilisateur() {}
	public Utilisateur(int id,int id_type,int id_client,String nom,String email,String mdp,String telephone) {
		this.setId(id);
		this.setId_type_user(id_type);
		this.setId_client(id_client);
		this.setNom(nom);
		this.setEmail(email);
		this.setMdp(mdp);
		this.setTelephone(telephone);
	}
	
	public static ArrayList<Utilisateur> getUtilisateurByType(Connection con) throws Exception {
		PreparedStatement pst=null;
        ResultSet result=null;
        ArrayList<Utilisateur> user = new ArrayList<Utilisateur>();
        try{
          pst=con.prepareStatement("select * from utilisateur where id_type_user =?");
          pst.setInt(1,4);

          result=pst.executeQuery();
          while(result.next()){
        	  Utilisateur temp = new Utilisateur();	  
            		 
	           temp.setId(result.getInt("id"));
	           temp.setId_type_user(result.getInt("id_type_user"));;
	           temp.setId_client(result.getInt("id_client"));
	           temp.setNom(result.getString("nom"));
	           temp.setEmail(result.getString("email"));
	           temp.setMdp(result.getString("mdp"));
	           temp.setTelephone(result.getString("telephone"));
	           user.add(temp);
           
          }
        }
		catch(Exception ex){
		 throw ex;
		}
		finally{
			result.close();
			pst.close();
			con.close();
		}
      return user;
	} 
	public  Utilisateur getUtilisateur(Connection con) throws Exception {
		PreparedStatement pst=null;
        ResultSet result=null;
        Utilisateur user = new Utilisateur();
        try{
          pst=con.prepareStatement("select * from utilisateur where id=?");
          pst.setInt(1,this.getId());

          result=pst.executeQuery();
          while(result.next()){
              user = new Utilisateur(result.getInt("id"),result.getInt("id_type_user"),result.getInt("id_client"),result.getString("nom"),result.getString("email"),result.getString("mdp"),result.getString("telephone"));
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
	
	
	public List<Utilisateur> getTechniciens(Connection con ) {
		PreparedStatement pst=null;
		ResultSet result=null;
		List<Utilisateur> list=new ArrayList<Utilisateur>();
		try {
			pst=con.prepareStatement("select utilisateur.email , firebasetoken.token from utilisateur join firebasetoken on utilisateur.email=firebasetoken.email where id_type_user=4");
			result=pst.executeQuery();
			while(result.next()) {
				Utilisateur temp = new Utilisateur();
				temp.setEmail(result.getString("email"));
				temp.setMdp(result.getString("token"));
				list.add(temp);
				
			}
			pst.close();
			result.close();
			con.close();
			
		}
		catch(Exception e ) {
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return list;
	}
	
	
	public Utilisateur getUtilisateurById(Connection con,String nom) throws Exception {
		PreparedStatement pst=null;
        ResultSet result=null;
        Utilisateur user = new Utilisateur();
        try{
          pst=con.prepareStatement("select * from utilisateur where nom=?");
          pst.setString(1,nom);
          System.out.println(pst);

          result=pst.executeQuery();
          while(result.next()){
              user = new Utilisateur(result.getInt("id"),result.getInt("id_type_user"),result.getInt("id_client"),result.getString("nom"),result.getString("email"),result.getString("mdp"),result.getString("telephone"));
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
	
	public Utilisateur getUtilisateurByMail(Connection con,String mail) throws Exception {
		PreparedStatement pst=null;
        ResultSet result=null;
        Utilisateur user = new Utilisateur();
        try{
          pst=con.prepareStatement("select * from utilisateur where email=?");
          pst.setString(1,mail);
          
          System.out.println(pst);

          result=pst.executeQuery();
          while(result.next()){
              user = new Utilisateur(result.getInt("id"),result.getInt("id_type_user"),result.getInt("id_client"),result.getString("nom"),result.getString("email"),result.getString("mdp"),result.getString("telephone"));
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
	
	public String registre(Connection co,Utilisateur newU) throws Exception {
		PreparedStatement pst=null;
		ResultSet result=null; 
		String rep ="";
		try{
			Utilisateur ins = getUtilisateurById(co,newU.getNom());
			System.out.println("INS "+ins.getNom());
			System.out.println("NewUs "+newU.getNom());
			if(ins.getNom() != newU.getNom()){
				System.out.println("Tafiditra ato");
				 pst=co.prepareStatement("INSERT INTO utilisateur(id_type_user,id_client,nom,email,mdp,telephone) VALUES(?,?,?,?,md5(?),?)");
		         
		          pst.setInt(1,2);
		          pst.setInt(2,newU.getId_client());
		          pst.setString(3,newU.getNom());
		          pst.setString(4,newU.getEmail());
		          pst.setString(5,newU.getMdp());
		          pst.setString(6,newU.getTelephone());
		          System.out.println(pst);
		          pst.executeUpdate();	
		          rep ="Inscription réussi!";
			}else{
				rep = "l'email existe déja, veuillez entrez un autre";
			}
          
          
        }
		catch(Exception ex){
		 throw ex;
		}
		finally{
		 result.close();
		 pst.close();
		 co.close();
		}
		return rep;
	}
	public Utilisateur login(Connection co,int id_type_user, String mail, String pwd) throws Exception{
		PreparedStatement pst=null;
		ResultSet result=null;
		Utilisateur temp = new Utilisateur();
		try{
          pst=co.prepareStatement("select*from utilisateur where id_type_user=? and email=? and mdp=md5(?)");
          pst.setInt(1, id_type_user);
          pst.setString(2,mail);
          pst.setString(3,pwd);
          System.out.println(pst);
          result=pst.executeQuery();
          while(result.next())
          {
	  		temp.setId(result.getInt("id"));
	  		temp.setId_type_user(result.getInt("id_type_user"));
	  		temp.setId_client(result.getInt("id_client"));
	  		temp.setNom(result.getString("nom"));
	  		temp.setEmail(result.getString("email"));
	  		temp.setMdp(result.getString("mdp"));
	  		temp.setTelephone(result.getString("telephone"));
	          
          }
          if(temp.getEmail()==null && temp.getMdp()==null) {
        	  throw new Exception("");
          }
        }
		catch(Exception ex){
		 throw ex;
		}
		finally{
		 result.close();
		 pst.close();
		}
		return temp;
	}

	public void insertToken(Connection co) throws Exception{
	  PreparedStatement pst=null;
      try{
    	  String token = this.getEmail()+(new Date()).toString();
          pst=co.prepareStatement("insert into token values (?, md5(?), current_timestamp)");
          pst.setInt(1,this.getId());
          pst.setString(2,token);
          pst.executeUpdate();
      }
	  catch(Exception ex){
		throw ex;
	  }
	  finally{
		pst.close();
	  }
  }

	public String getToken(Connection co) throws Exception {
		PreparedStatement pst=null;
        ResultSet result=null;
        String token = "";
        try{
          pst=co.prepareStatement("select token from token where id=? order by dateco desc limit 1");
          
          pst.setInt(1,this.getId());
          System.out.println("ID is = "+this.getId());

          result=pst.executeQuery();
          while(result.next()){
              token = result.getString("token");
          }
        }
		catch(Exception ex){
		throw ex;
		}
		finally{
			result.close();
			pst.close();
		}
      return token;
	}

	public void logout(String token) throws Exception{
		Connection co = null;
		PreparedStatement pst=null;
		ResultSet result=null;
		try{
			co = DbHelper.connect();
			pst=co.prepareStatement("delete from token where token=?");
            pst.setString(1,token);
            pst.executeUpdate();
//            System.out.println("Tafavoaka tato");
		}catch(Exception ex){
			throw ex;
		}
		finally{
			if(result != null)
				result.close();
			if(pst != null)
				pst.close();
			if(co != null)
				co.close();
		}
	}
	
	public static String generateRandomPassword(int len)
    {
            final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder();


            for (int i=0; i<len; i++){
                    int randomIndex = random.nextInt(chars.length());
                    sb.append(chars.charAt(randomIndex));
            }

            return sb.toString();
    }
	
	public static void updatePassword(Connection con,String email,String mdp) throws Exception {
		  PreparedStatement pst=null;
	      try{
	          pst=con.prepareStatement("UPDATE Utilisateur set mdp= md5(?) WHERE email= ?");
	          pst.setString(1,mdp);
	          pst.setString(2,email);
	          pst.executeUpdate();
	      }
		  catch(Exception ex){
			throw ex;
		  }
		  finally{
			pst.close();
		  }
	}
	

	 public static String encrypt(String unencryptedString)throws Exception {
		 byte[] bytesEncoded = Base64.encodeBase64(unencryptedString.getBytes());
		 System.out.println("encoded value is " + new String(bytesEncoded));
		 return new String(bytesEncoded);
	 }

    public static String decrypt(String encryptedString)throws Exception {
    	byte[] valueDecoded = Base64.decodeBase64(encryptedString);
    	System.out.println("Decoded value is " + new String(valueDecoded));
    	return new String(valueDecoded);
    }
	
}
