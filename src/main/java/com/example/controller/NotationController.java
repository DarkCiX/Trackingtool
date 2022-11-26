package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;

import com.example.model.Notation;
import com.example.model.Utilisateur;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class NotationController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    public ObjectMapper objectMapper = new ObjectMapper();
	
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/Notations/Insert")
	    public Response insertNotation(@RequestBody Notation note) throws IOException {
		   Response resp = new Response();
	    	try {
	    		
	    			if(note.getNote()<=0) {
	    	    		resp = new Response(status_error,"Rating value not allowed!!");

	    			}
	    			else {
	    				note.insert(note, DbHelper.connect());
			    		resp = new Response(status_success,message_success);
	    			}
	    			

	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return resp;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Notations/{mail}")
	    public Notation getNotationsByMail(@PathVariable String mail) throws IOException {
		   	Notation note=new Notation();
	    	try {
	 		    	note=note.FindByMail(DbHelper.connect(), mail);
	 		    	
	    		
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    	}		
	    	
		   return note;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Notations")
	    public List<Notation> getNotations() throws IOException {
		   	List<Notation> notes=new ArrayList<Notation>();
	    	try {
	    			Notation note=new Notation();
	 		    	notes= note.Find(DbHelper.connect());
	    		
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    	}		
	    	
		   return notes;
	    }
	   
	
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PutMapping(value="/Notations/update")
	    public Response updateNotation(@RequestBody Notation note) throws IOException {
		   Response resp = new Response();
		   
	    	try {
	    		note.insert(note, DbHelper.connect());
	    		resp = new Response(status_success,message_success);

	    	}catch(Exception ex) {	 
	    		ex.printStackTrace();
	    		resp = new Response(status_error,message_error);

	    	}		
	    	return resp;
	    	
	    }
	   
	   
	   
	   
	   
	  
	   
	   
	   
	  
	   
	   
	   
	   
   

	   

	  
	  
	   
}
