package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Detail_devis;
import com.example.model.Reclamation;
import com.example.model.devis_client;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class ReclamationController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    
    @Autowired
    public JavaMailSender emailSender;
    
    
	@ResponseBody
	@PostMapping(value="/saveReclamation")
    public Response insertDevis(@RequestParam("reclamation") String reclamation) throws IOException, SQLException {
		   Connection con = null;
		   Reclamation detail = new ObjectMapper().readValue(reclamation, Reclamation.class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();

	    		System.out.println(reclamation);
	    		MimeMessage message = emailSender.createMimeMessage();
				 
		        boolean multipart = true;
		 
		        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
		        
		        helper.setFrom("nomenandrianinaantonio@gmail.com");
		        helper.setTo("nomenandrianinaantonio@gmail.com");
		        helper.setCc("nomenandrianinaantonio@gmail.com");
		        helper.setSubject(detail.getObjet());
		        helper.setText(detail.getMessage());
		        		       
		       
		         // Send Message!
		        this.emailSender.send(message);
		    	Reclamation.save(con,detail);
	    		resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}finally{
	    		con.close();
	    	}		
		   return resp;
	    }
	
	@GetMapping("/liste/reclamation")
	public Response getAll() throws ClassNotFoundException, Exception {
		Response resp = new Response();
		Connection con = DbHelper.connect();
		try {
			ArrayList<Reclamation> list = Reclamation.GetAll(con);
			resp = new Response(status_success,message_success,list);
		}catch(Exception ex) {
			resp = new Response(status_error,message_error);
		}finally {
			con.close();
		}
	   return resp;
	}
	
	@GetMapping("/detail/reclamation/{id}")
	public Response GetOne(@PathVariable String id) throws ClassNotFoundException, Exception {
		Response resp = new Response();
		Connection con = DbHelper.connect();
		int idr = Integer.parseInt(id);
		try {
			Reclamation list = Reclamation.GetReclamation(con,idr);
			resp = new Response(status_success,message_success,list);
		}catch(Exception ex) {
			resp = new Response(status_error,message_error);
		}finally {
			con.close();
		}
	   return resp;
	}
	
	@GetMapping("/statistique/reclamation")
	public Response getStatistique() throws ClassNotFoundException, Exception {
	   Connection con = DbHelper.connect();
	   Response resp = new Response();
	   try {
		   double[] result = Reclamation.Statistique(con);
		   resp = new Response(200,"Succes",result);
	   }catch(Exception ex) {
		   resp = new Response(status_error,message_error);
		   System.out.println(ex.getMessage());
	   }finally {
		   con.close();
	   }

	   return resp;
 	}
	@GetMapping("/update/reclamation/{id}")
	public Response SetLu(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Response resp = new Response();
	  Connection con = DbHelper.connect();
	  int idrec = Integer.parseInt(id);
	  	try {
	  		Reclamation.Update(con,idrec);
	  		resp = new Response(status_success,"Validation réussite");
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,"Validation erreur");
	  	}finally {
	  		con.close();
	  	}	
	return resp;
  }
}
