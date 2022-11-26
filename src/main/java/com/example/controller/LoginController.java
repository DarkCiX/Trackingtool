package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Firebasetoken;
import com.example.model.Reclamation;
import com.example.model.U_front;
import com.example.model.Utilisateur;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class LoginController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
	@Autowired
    public JavaMailSender emailSender;
	
	@PostMapping("/login")
	public Response login(@RequestBody Utilisateur data) throws SQLException{
		String token = "";
		Connection co = null;
		Response resp = new Response();
		U_front rep = new U_front();
		Utilisateur u = new Utilisateur();
		try{
			co = DbHelper.connect();
			co.setAutoCommit(true);
			Utilisateur user = new Utilisateur();
			user = user.login(co, data.getId_type_user(),data.getEmail(), data.getMdp());
			user.insertToken(co);
			token = user.getToken(co);
			u = user.getUtilisateur(co);
			rep = new U_front(u.getId(),u.getId_type_user(),token,data.getEmail(),u.getNom(),u.getTelephone());
			resp = new Response(200,"Data successfully received",rep);
			
		}catch(Exception ex){
			resp = new Response(400,"Email ou mot de passe invalide");
		}finally {
			co.close();
		}
		return resp;
	}
	
	@PostMapping("/registre")
	public Response registre(@RequestBody Utilisateur data){
		Connection co = null;
		Response resp = new Response();
		String message ="Cette Compte existe déja, veuillez entrez un autre";
		try{
			co = DbHelper.connect();
			co.setAutoCommit(true);
			Utilisateur user = new Utilisateur();
			message = user.registre(co,data);
			resp = new Response(200,message);
		}catch(Exception ex){
			resp = new Response(400,message, null);
		}finally{
			if(co != null)
			try {
			co.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return resp;
	}

	@DeleteMapping(value="/logout")
	public Response logout(@RequestHeader(value="Authorization") String token){
		
		Utilisateur utilisateur = new Utilisateur();
		Connection co=null;
		Response resp = new Response();
		try {
			utilisateur.logout(token);
			resp = new Response(200,"Success","0");
			co = DbHelper.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp = new Response(400,"Erreur de deconnection","0");
		}
		finally{
			try {
				co.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return resp;
	}
	
	@ResponseBody
	@PostMapping(value="/resetPassword")
    public Response resetPassword(@RequestParam("email") String email) throws IOException, SQLException {
		   Connection con = null;
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();

	    		MimeMessage message = emailSender.createMimeMessage();
				 
		        boolean multipart = true;
		 
		        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
		        String encryptEmail = Utilisateur.encrypt(email);       
		        String url = "http://localhost:4200/reset/motdepasse/"+encryptEmail;

		        helper.setTo("nomenandrianinaantonio@gmail.com");
		        helper.setSubject("Réinitialisation du mot de passe");
		        helper.setText("TRACKING TOOL\r\n"
		        		+ "\r\n"
		        		+ "Quelqu’un (j’espère que vous) a demandé une réinitialisation du mot de passe pour votre compte TRACKING TOOL. Suivez le lien ci-dessous pour définir un nouveau mot de passe :\r\n"
		        		+ "\r\n"
		        		+url+"\r\n"
		        		+ "\r\n"
		        		+ "Si vous ne souhaitez pas réinitialiser votre mot de passe, ignorez cet e-mail et aucune action ne sera entreprise.\r\n"
		        		+ "\r\n"
		        		+ "L’équipe Regitech\r\n"
		        		+ "https://www.regitech-oi.com");
		        		       
		       
		         // Send Message!
		        this.emailSender.send(message);
	    		resp = new Response(status_success,"Merci de nous faire confiance.Un lien a étè envoyé dans votre mail puis cliquez ce lien pour réinitialiser votre mot de passe");
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}finally{
	    		con.close();
	    	}		
		   return resp;
	    }
	
	@PostMapping("/registerToken")
	public Response registerToken(@RequestBody Firebasetoken tokendata) throws SQLException{
		Connection co = null;
		Response resp = new Response();
		try{
			co = DbHelper.connect();
			co.setAutoCommit(true);
			tokendata.insert(tokendata, co);
			resp = new Response(200,"Token Successfully registered",tokendata);
			
		}catch(Exception ex){
			resp = new Response(400,"Error!!");
		}finally {
			co.close();
		}
		return resp;
	}
	
	
	@PostMapping("/firebaseDisconnect")
	public Response removeFBToken(@RequestBody Firebasetoken tokendata) throws SQLException{
		Connection co = null;
		Response resp = new Response();
		try{
			co = DbHelper.connect();
			co.setAutoCommit(true);
			tokendata.deleteToken(tokendata.getToken(), co);
			resp = new Response(200,"Token Successfully removed",tokendata);
			
		}catch(Exception ex){
			resp = new Response(400,"Error!!");
		}finally {
			co.close();
		}
		return resp;
	}
	
}
