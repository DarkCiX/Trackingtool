package com.example.controller;

import com.example.model.Note;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Client;
import com.example.model.DemStats;
import com.example.model.Demande;
import com.example.model.Detail_devis;
import com.example.model.EnguageMessage;
import com.example.model.Firebasetoken;
import com.example.model.Messages;
import com.example.model.Note;
import com.example.model.Personel;
import com.example.model.Reparation_Front;
import com.example.model.Utilisateur;
import com.example.model.bon_E;
import com.example.model.bon_S;
import com.example.model.bon_entree;
import com.example.model.bon_entree_produit;
import com.example.model.bon_sortie;
import com.example.model.bon_sortie_produit;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.PushOptions;
import com.mongodb.client.model.Updates;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class DemandeController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    
    
        
    public ObjectMapper objectMapper = new ObjectMapper();
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/Demandes/Insert")
	    public Response insertDemande(@RequestBody Demande dem) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
	    	try {
	    			conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	
	 		    	LocalDateTime myObj = LocalDateTime.now();

	 		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss.SSS");

	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	myObj=myObj.plusHours(3);
	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	String sssformat=formatter.format(myObj);
	 		    	
	 		    	String serial=sssformat.replaceAll("[/ :.]", "");
	 		    	
	 		    	
	 		    	Messages mess=new Messages(dem.getEmail(),dem.getText(),format,dem.getFileName(),"0","");
	 		    	
	 		    	List<Messages> messList=new ArrayList<Messages>();
	 		    	messList.add(mess);
	 		    	
	 		    	Document demande = new Document("serial", serial)
	 		    			.append("nomclient",dem.getNomClient())
	 		    			.append("objet", dem.getObject())
	 		    			.append("supportMail", "none")
	 		    			.append("supportName", "none")
	 		    			.append("mailClient", dem.getEmail())
	 		    			.append("statut","1")
	 		    			.append("filename", "")
                            .append("datedemande", format)
                            .append("messages", messList);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
	 		    	collection.insertOne(demande);
	 		    	
	 		    	conex.close();
	 		    	Connection co=DbHelper.connect();
	 		    	Utilisateur temp=new Utilisateur();
	 		    	List<Utilisateur> techs=temp.getTechniciens(co);
	 		   	Note note=new Note();
 		    	note.setSubject("Demande d'assistance.");
 		    	note.setContent("Un client a besoin de vous!");
 		    
 		    	String path="trackingtooladmin-firebase-adminsdk-xi62m-a1bedbb4fc.json";
 
 		    	
 		    	for(Utilisateur tech:techs) {
 		    		this.sendNotification(note, tech.getMdp(),path);
 		    	}
 		    	
 		    	
 		    	resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return resp;
	    }
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/Demandes/Add/Message/{id}")
	    public Response ajoutMessage(@PathVariable String id,@RequestBody Messages mess,@RequestParam("shift") String shift ) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Connection sqlCon=null;
		   Response resp = new Response();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();

	 		    	
	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh "+ normalformatter.format(myObj));
	 		    	
	 		    	myObj=myObj.plusHours(3);
	 		    	
	 		    	System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh "+ normalformatter.format(myObj));


	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	
	 		    	
	 		    	mess.setDate(format);
	 		    	mess.setVue("0");
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
	 		  
//	 		    	Document doc=new Document("serial",id);
	 		    			
	 		    	
	 		    	collection.updateOne(
	 		    		    Filters.eq("serial", id),
	 		    		    
	 		    		    Updates.pushEach("messages",
	 		    		        Arrays.asList(mess), new PushOptions().position(0))
	 		    		);
	 		    	
	 		    	Firebasetoken token=new Firebasetoken("","");
	 		    	sqlCon=DbHelper.connect();
	 		    	List<Firebasetoken> tokens=token.Find(sqlCon, mess.getDestinataire());
	 		    	
	 		    	sqlCon.close();
	 		    	Note note=new Note();
	 		    	note.setSubject("Message");
	 		    	note.setContent("Vous avez un nouveau message.");
	 		    
	 		    	String path="n";
	 		    	
	 		    	if(Integer.valueOf(shift)==0) {
	 		    		path="trackingtool-e2610-firebase-adminsdk-c2g7x-b0a7a5483a.json";

	 		    	}
	 		    	else {
	 		    		path="trackingtooladmin-firebase-adminsdk-xi62m-a1bedbb4fc.json";



	 		    	}
	 		    	for(Firebasetoken tok:tokens) {
	 		    		
	 		    		this.sendNotification(note, tok.getToken(),path);
	 		    	}
	 		    
	 		    	
	 		    	conex.close();
	 		    	

	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		ex.printStackTrace();
	    	}		
	    	
		   return resp;
	    }
	   
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/Demandes/EnguageMessage")
	    public Response engageDemande(@RequestBody EnguageMessage enguageMess) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();
	 		    	myObj=myObj.plusHours(3);

	 		    	
	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	
	 		    	
	 		    	Messages mess=new Messages(enguageMess.getEmail(),enguageMess.getText(),format,"","0","");
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
	 		  
//	 		    	Document doc=new Document("serial",id);
	 		    			
	 		    	
	 		    	collection.updateOne(
	 		    		    Filters.eq("serial", enguageMess.getSerial()),
	 		    		    
	 		    		    Updates.pushEach("messages",
	 		    		        Arrays.asList(mess), new PushOptions().position(0)
	 		    		    )
	 		    		  
	 		    		);
	 		    	collection.updateOne(
	 		    		    Filters.eq("serial", enguageMess.getSerial()),
	 		    		    Updates.set("supportMail",enguageMess.getEmail()) 
	 		    
	 		    		  
	 		    		);
	 		    	collection.updateOne(
	 		    		    Filters.eq("serial", enguageMess.getSerial()),
			    		    Updates.set("supportName", enguageMess.getName())
	 		    		);
	 		    	conex.close();
	 		    	
	 		    	Firebasetoken token=new Firebasetoken("","");
	 		    	Connection sqlCon=DbHelper.connect();
	 		    	List<Firebasetoken> tokens=token.Find(sqlCon, enguageMess.getDestinataire());
	 		    	
	 		    	Note note=new Note();
	 		    	note.setSubject("Demande d'assistance.");
	 		    	note.setContent("Un technicien vous envoie son aide! Veuillez vous rendre sur l'application.");
	 		    
	 		    	String path="trackingtool-e2610-firebase-adminsdk-c2g7x-b0a7a5483a.json";
	 		    	

	 		    	
	 		    	
	 		    	for(Firebasetoken tok:tokens) {
	 		    		
	 		    		this.sendNotification(note, tok.getToken(),path);
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
	   @GetMapping(value="/Demandes")
	    public String getMessageClient(@RequestParam("email") String mail) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   String response="";
		   Response resp = new Response();
		  List<Document> liste=new ArrayList<Document>();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();
	 		    	myObj=myObj.plusHours(3);


	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	String serial=format.replaceAll("[/ :]", "");
	 		    	
//	 		    	
//	 		    	Message mess=new Message(mail,messagetext,format);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  
	 		    	Document doc=new Document("mailClient",mail);
	 		    	
	 		    	 FindIterable<Document> docs = collection.find(doc);
	 		    	
	 	             for(Document d : docs) {
	 	            	 System.out.println("id= "+d.get("_id"));
	 	            	 liste.add(d);
	 	             }
	 		    	
	 	             conex.close();
	 	             
	 	             response=new Gson().toJson(liste);
	 	             
//	 		    	resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
//	    		resp = new Response(status_error,message_error);
//	    		System.out.println(ex.getMessage());
	    		ex.printStackTrace();
//	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return response;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Demandes/Stats")
	    public String getStatsDemandes(@RequestParam("year") int year) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   String response="";
		   Response resp = new Response();
		  List<Document> liste=new ArrayList<Document>();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	
	 		    	
//	 		    	
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  
	 		    	
	 		    	
	 		    	 FindIterable<Document> docs = collection.find();
	 		    	 
	 		    	 
	 		    	
	 		    	
	 	             
	 	             
	 	             response=new Gson().toJson(getStats(docs,year));
	 	             conex.close();
	 	             
//	 		    	resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
//	    		resp = new Response(status_error,message_error);
//	    		System.out.println(ex.getMessage());
	    		ex.printStackTrace();
//	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return response;
	    }
	   
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/Demandes/Admin")
	    public List<Document> getMessageAdmins(@RequestBody Personel personel) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		  List<Document> liste=new ArrayList<Document>();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande"); 		  
	 		    	Document doc=new Document("supportMail",personel.getEmail())
	 		    								.append("supportName", personel.getName());
	 		    	 FindIterable<Document> docs = collection.find(doc);
	 	             for(Document d : docs) {
	 	            	 System.out.println("id= "+d.get("_id"));
	 	            	 liste.add(d);
	 	             }
	 	            conex.close();
	    		
	    	}catch(Exception ex) {
	    	}		
	    	
		   return liste;
	    }
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Demandes/SuperAdmin")
	    public List<Document> getMessageAdmins() throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		  List<Document> liste=new ArrayList<Document>();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande"); 		  
	 		    
	 		    	 FindIterable<Document> docs = collection.find();
	 	             for(Document d : docs) {
	 	            	 liste.add(d);
	 	             }
	 	             
	 	            conex.close();
	    		
	    	}catch(Exception ex) {
	    	}		
	    	
		   return liste;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Demandes/{id}")
	    public Document getDemandeDetails(@PathVariable String id,@RequestParam("shift") String number) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		   Document liste=new Document();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();
	 		    	myObj=myObj.plusHours(3);


	 		    	
	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	String serial=format.replaceAll("[/ :]", "");
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  
	 		    	Document doc=new Document("serial",id);
	 		    	
	 		    	 FindIterable<Document> docs = collection.find(doc);
	 		    	 System.out.println("shift= "+number);
	 	             for(Document d : docs) {
	 	            	 Messages temp=new Messages();
	 	            	 System.out.println("okok");
						List<Document> list=d.getList("messages",Document.class);
						List<Messages> mess=new ArrayList<Messages>();
						for(Document obj:list) {
							
							mess.add(new Messages(String.valueOf(obj.get("mail")),String.valueOf(obj.get("text")),String.valueOf(obj.get("date")),String.valueOf(obj.get("fileName")),String.valueOf(obj.get("vue")),""));
						}
						
	 	            	 System.out.println(list.get(0).get("mail"));

	 	            	 temp.updateMessageArray(d.getString("mailClient"),Integer.valueOf(number),mess);
	 	            	 System.out.println(mess);
	 	            	 collection.updateOne(
		 		    		    Filters.eq("serial", id),
		 		    		    Updates.set("messages",mess)
		 		    		  
		 		    		);
	 	            	 
//	 	            	 liste=d;
	 	             }
	 	            FindIterable<Document> docsFinal = collection.find(doc);
	 	            for(Document d:docsFinal) {
	 	            	liste=d;
	 	            }
	 	           conex.close();
	 	             
//	 	            FindIterable<Document> docs2 = collection.find(doc);
	 	             
	 		    	
	 		 		  
//	 		    	Document doc=new Document("serial",id);
	 		    			
	 		    	
	 		    	
	    	}catch(Exception ex) {	 
	    		ex.printStackTrace();
	    	}		
	    	
		   return liste;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Admin/Demandes/{id}")
	    public Document getDemandeDetailsAdmin(@PathVariable String id) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		   Document liste=new Document();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  
	 		    	Document doc=new Document("serial",id);
	 		    		 		    	 
	 	            FindIterable<Document> docsFinal = collection.find(doc);
	 	            for(Document d:docsFinal) {
	 	            	liste=d;
	 	            }
	 	           conex.close();
	    	}catch(Exception ex) {	 
	    		ex.printStackTrace();
	    	}		
	    	
		   return liste;
	    }
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PutMapping(value="/Demandes/close/{id}")
	    public Document getDemandeDetails(@PathVariable String id) throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		   Document liste=new Document();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();

	 		    	
	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	String serial=format.replaceAll("[/ :]", "");
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  
	 		    	Document doc=new Document("serial",id);
	 		    	
	 		    	FindIterable<Document> docs = collection.find(doc);
	 	             
	 		    	collection.updateOne(
 		    		    Filters.eq("serial", id),
 		    		    
 		    		    Updates.set("statut",'0')
 		    		  
 		    		);
	 		    	conex.close();
	 		    	
	    	}catch(Exception ex) {	 
	    		ex.printStackTrace();
	    	}		
	    	
		   return liste;
	    }
	   
	   
	   
	   
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Demandes/EnAttente")
	    public List<Document> getDemandeEnAttente() throws IOException {
		   MongoClient conex=null;
		   MongoDatabase con=null;
		   Response resp = new Response();
		  List<Document> liste=new ArrayList<Document>();
	    	try {
		    		conex= DbHelper.getConnexionMG();
	 		    	con = DbHelper.getDB(conex);
	 		    	LocalDateTime myObj = LocalDateTime.now();

	 		    	
	 		    	DateTimeFormatter normalformatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

	 		    	String format = normalformatter.format(myObj);
	 		    	
	 		    	String serial=format.replaceAll("[/ :]", "");
	 		    	
//	 		    	
//	 		    	Message mess=new Message(mail,messagetext,format);
	 		    	MongoCollection<Document> collection = con.getCollection("Demande");
//	 		  

	 		    	Document doc = new Document("supportMail", "none")
	 		    							.append("supportName", "none");
	 		    	
	 		    	 FindIterable<Document> docs = collection.find(doc);
	 		    	
	 	             for(Document d : docs) {
	 	            	 liste.add(d);
	 	             }
	 		    	
	 	             conex.close();
	 	             
//	 		    	resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
//	    		resp = new Response(status_error,message_error);
//	    		System.out.println(ex.getMessage());
//	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return liste;
	    }
	   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @GetMapping(value="/Verifytoken")
	    public boolean verifytoken(@RequestParam("email") String email,@RequestHeader(value="Authorization") String token) throws IOException {
	    	try {
		    		if(this.verifyToken(email, token)==true) {
		    			return true;
		    		}
//	 		    	resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
//	    		resp = new Response(status_error,message_error);
//	    		System.out.println(ex.getMessage());
//	    		System.out.println("Ato am erreur");
	    	}		
	    	
		   return false;
	    }
	   
	  

	      
	       


	       public void sendNotification(Note note, String token,String path) throws FirebaseMessagingException {
	    	   FirebaseMessaging firebaseMessaging=null;
	    	   FirebaseApp app =null;
	    	   try {
	    		 	    GoogleCredentials googleCredentials = GoogleCredentials
	    	   	            .fromStream(new ClassPathResource(path).getInputStream());
	    	   	    	FirebaseOptions firebaseOptions = FirebaseOptions
	    	   	            .builder()
	    	   	            .setCredentials(googleCredentials)
	    	   	            .build();
	    		   	    app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
	    		   	     firebaseMessaging= FirebaseMessaging.getInstance(app);
	    		   	     
	    	   }
	    	   catch(Exception e ) {
	    		   System.out.println("ok");
	    		   e.printStackTrace();
	    	   }
	    	  
	           Notification notification = Notification
	                   .builder()
	                   .setTitle(note.getSubject())
	                   .setBody(note.getContent())
	                   .build();

	           Message message = Message
	                   .builder()
	                   .setToken(token)
	                   .setNotification(notification)
	                   .build();
	           

	            firebaseMessaging.send(message);
	            
	            app.delete();
	       }
	       
	       
	       public boolean verifyToken(String email,String token) {
	    	   	
	    	   
	    	   Utilisateur util=new Utilisateur();
	    	   Connection co = null;
	    	   try {
	    		   co=DbHelper.connect();
	    		   util=util.getUtilisateurByMail(co, email);
	    		   String tok=util.getToken(co);
	    		  
	    		   if(tok.compareTo("")==0) {
	    			   return false;
	    		   }
	    		   if(token.compareTo(tok)==0) {
	    			   return true;
	    		   }
	    		   
	    	   }
	    	   catch(Exception e) {
	    		   e.printStackTrace();
	    	   }
	    	   
	    	   
	    	   
	    	   return false;
	       }
	       
	       
	       public int[] getDMY(String datetime) {
	    	   int[] retour= new int[3];
	    	   
	    	   String[] splitted1=datetime.split(" ");
	    	   String[] splitted2= splitted1[0].split("/");
	    	   
	    	   for(int i=0;i<retour.length;i++) {
	    		   retour[i]=Integer.valueOf(splitted2[i]);
	    		   
	    	   }
	    	 
	    	   return retour;
	    	   
	       }
	       
	       
	       
	       
	       public int[] getStats(FindIterable<Document> doc,int year) {
	    	   
	    	   List<DemStats> stats=new ArrayList<DemStats>();
	    	   int m=1;
	    	   String[] monthNames=new String[13];
	    	   int[] retour=new int[12];
	    	   int i=0;
	    	   monthNames[1]="Janvier";
	    	   monthNames[2]="Fevrier";
	    	   monthNames[3]="Mars";
	    	   monthNames[4]="Avril";
	    	   monthNames[5]="Mai";
	    	   monthNames[6]="Juin";
	    	   monthNames[7]="Juillet";
	    	   monthNames[8]="Aout";
	    	   monthNames[9]="Septembre";
	    	   monthNames[10]="Octobre";
	    	   monthNames[11]="Novembre";
	    	   monthNames[12]="Decembre";
	    	   
	    	   
	    	   while(m<13) {
	    		   int value=countRecurrence(doc,year,m);
	    		   retour[i]=value;
	    		   m++;
	    		   i++;
	    	   }
	    	   return retour;
	       }
	       
	       public int countRecurrence(FindIterable<Document> doc,int year,int month) {
	    	   int retour=0;
	    	   for(Document d:doc) {
	    		   int[] dmy=getDMY(d.getString("datedemande"));
	    		   
	    		   if(dmy[1]==month && dmy[2]==year) {
	    			   retour++;
	    		   }
	    	   }
	    	   return retour;
	       }
	       
	      
	       
	       

	   
	   
	   
	   
	  
	   
	   
	   
	   
   

	   

	  
	  
	   
}
