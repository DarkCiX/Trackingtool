package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Autres;
import com.example.model.Imprimante;
import com.example.model.Imprimante_observation;
import com.example.model.Onduleur;
import com.example.model.Regulateur;
import com.example.model.bon_E;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class InterventionController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    
    
	@PostMapping(value="/saveIntervention/Autres")
    public Response insertInterventionAutres(@RequestParam("autre") String autre) throws IOException {
	   Autres detail = new ObjectMapper().readValue(autre, Autres.class);
	   Response resp = new Response();
    	try {
    		Autres.save("Autres",detail);
//    		System.out.println(autre);
    		
    		resp = new Response(status_success,message_success);
    		
    	}catch(Exception ex) {
    		resp = new Response(status_error,message_error);
    		System.out.println(ex.getMessage());
    		System.out.println("Ato am erreur");
    	}		
	   return resp;
    }
	
  @GetMapping("/liste/fiche/intervention/autres")
	public Response getAllAutres() throws ClassNotFoundException, Exception {
	   Response resp = new Response();
	   try {
		   ArrayList<Autres> list = Autres.findAll("Autres");
		   resp = new Response(200,"Succes",list);
	   }catch(Exception ex) {
		   resp = new Response(status_error,message_error);
		   System.out.println(ex.getMessage());
	   }

	   return resp;
	}
  
	@PostMapping(value="/saveRegulateur/Regulateur")
    public Response insertInterventionRegulateur(@RequestParam("regulateur") String regulateur) throws IOException {
		Regulateur detail = new ObjectMapper().readValue(regulateur, Regulateur.class);
	   Response resp = new Response();
    	try {
    		Regulateur.save("Regulateur",detail);
//    		System.out.println(regulateur);
    		
    		resp = new Response(status_success,message_success);
    		
    	}catch(Exception ex) {
    		resp = new Response(status_error,message_error);
    		System.out.println(ex.getMessage());
    		System.out.println("Ato am erreur");
    	}		
	   return resp;
    }
	
  @GetMapping("/liste/fiche/intervention/regulateur")
	public Response getAllRegulateur() throws ClassNotFoundException, Exception {
	   Response resp = new Response();
	   try {
		   ArrayList<Regulateur> list = Regulateur.findAll("Regulateur");
		   resp = new Response(200,"Succes",list);
	   }catch(Exception ex) {
		   resp = new Response(status_error,message_error);
		   System.out.println(ex.getMessage());
	   }

	   return resp;
	}
  
  
	@PostMapping(value="/saveOnduleur/Onduleur")
    public Response insertInterventionOnduleur(@RequestParam("onduleur") String onduleur) throws IOException {
		Onduleur detail = new ObjectMapper().readValue(onduleur, Onduleur.class);
	   Response resp = new Response();
    	try {
    		Onduleur.save("Onduleur",detail);
//    		System.out.println(onduleur);
    		resp = new Response(status_success,message_success);
    	}catch(Exception ex) {
    		resp = new Response(status_error,message_error);
    		System.out.println(ex.getMessage());
    		System.out.println("Ato am erreur");
    	}		
	   return resp;
    }
	
  @GetMapping("/liste/fiche/intervention/onduleur")
	public Response getAllOndueleur() throws ClassNotFoundException, Exception {
	   Response resp = new Response();
	   try {
		   ArrayList<Onduleur> list = Onduleur.findAll("Onduleur");
		   resp = new Response(200,"Succes",list);
	   }catch(Exception ex) {
		   resp = new Response(status_error,message_error);
		   System.out.println(ex.getMessage());
	   }

	   return resp;
	}
  
  @GetMapping("/intervention/detail/regulateur/{id}")
 	public ResponseEntity<Regulateur> getDetailInterventionRegulateur(@PathVariable String id) throws ClassNotFoundException, Exception {
 	  Regulateur list = Regulateur.getdetail_Intervention_Regulateur("Regulateur",id);
 	  return new ResponseEntity<>(list,HttpStatus.OK);
  }
  
  @GetMapping("/update/detail/imprimante/{id}")
	public Response SetValideInterventionImprimante(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Response resp = new Response();
	  int idobj = Integer.parseInt(id);
	  	try {
	  		Imprimante.update("Imprimante",idobj);
	  		resp = new Response(status_success,message_success);
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,message_error);
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
	return resp;
  }
  
  @GetMapping("/update/detail/autres/{id}")
 	public Response SetValideInterventionAutres(@PathVariable String id) throws ClassNotFoundException, Exception {
 	  Response resp = new Response();
 	  int idobj = Integer.parseInt(id);
 	  	try {
 	  		Autres.update("Autres",idobj);
 	  		resp = new Response(status_success,message_success);
 	  		
 	  	}catch(Exception ex) {
 	  		resp = new Response(status_error,message_error);
 	  		System.out.println(ex.getMessage());
 	  		System.out.println("Ato am erreur");
 	  	}		
 	return resp;
   }
  
  @GetMapping("/delete/intervention/autres/{id}")
	public Response DeleteInterventionAutres(@PathVariable String id) throws ClassNotFoundException, Exception {
		Response resp = new Response();
		int idobj = Integer.parseInt(id);
	  	try {
	  		Autres.Delete("Autres",idobj);
	  		resp = new Response(status_success,"Supression éffectuée");
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,"Supression non éfffectué");
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
		   return resp;
	}
  
  @GetMapping("/delete/intervention/onduleur/{id}")
	public Response DeleteInterventionOnduleur(@PathVariable String id) throws ClassNotFoundException, Exception {
		Response resp = new Response();
		int idobj = Integer.parseInt(id);
	  	try {
	  		Onduleur.Delete("Onduleur",idobj);
	  		resp = new Response(status_success,"Supression éffectuée");
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,"Supression non éfffectué");
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
		   return resp;
	}
  
  @GetMapping("/update/detail/onduleur/{id}")
	public Response SetValideInterventionOnduleur(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Response resp = new Response();
	  int idobj = Integer.parseInt(id);
	  	try {
	  		Onduleur.update("Onduleur",idobj);
	  		resp = new Response(status_success,message_success);
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,message_error);
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
	return resp;
 }
  
  @GetMapping("/update/detail/regulateur/{id}")
	public Response SetValideInterventionRegulateur(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Response resp = new Response();
	  int idobj = Integer.parseInt(id);
	  	try {
	  		Regulateur.update("Regulateur",idobj);
	  		resp = new Response(status_success,message_success);
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,message_error);
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
	return resp;
}


  
  
  @PostMapping(value="/saveIntervention/Imprimante")
  public Response insertInterventionImprimante(@RequestParam("imprimante") String imprimante, @RequestParam("observation") String observation) throws IOException {
	  	System.out.println(imprimante);
	  	Imprimante detail = new ObjectMapper().readValue(imprimante, Imprimante.class);
	  	Imprimante_observation observ = new ObjectMapper().readValue(observation, Imprimante_observation.class);
	  	Response resp = new Response();
  	try {
  		Imprimante.save("Imprimante",detail,"Imprimante_observation",observ);
  		resp = new Response(status_success,message_success);
  		
  	}catch(Exception ex) {
  		resp = new Response(status_error,message_error);
  		System.out.println(ex.getMessage());
  		System.out.println("Ato am erreur");
  	}		
	   return resp;
  }
  
  
  @PostMapping(value="/updateIntervention/Imprimante")
  public Response updateInterventionImprimante(@RequestParam("imprimante") String imprimante, @RequestParam("observation") String observation) throws IOException {
	  	Imprimante detail = new ObjectMapper().readValue(imprimante, Imprimante.class);
	  	Imprimante_observation observ = new ObjectMapper().readValue(observation, Imprimante_observation.class);
	  	Response resp = new Response();
  	try {
  		Imprimante.updateRow("Imprimante","Imprimante_observation",detail,observ);
  		resp = new Response(status_success,message_success);
  		
  	}catch(Exception ex) {
  		resp = new Response(status_error,message_error);
  		System.out.println(ex.getMessage());
  		System.out.println("Ato am erreur");
  	}		
	   return resp;
  }
  
  @PostMapping(value="/updateIntervention/Onduleur")
  public Response updateInterventionOnduleur(@RequestParam("onduleur") String onduleur) throws IOException {
	  System.out.println(onduleur);
	  Onduleur detail = new ObjectMapper().readValue(onduleur, Onduleur.class);
  	  Response resp = new Response();
  	try {
  		Onduleur.updateRow("Onduleur",detail);
  		resp = new Response(status_success,message_success);
  	}catch(Exception ex) {
  		resp = new Response(status_error,message_error);
  		System.out.println(ex.getMessage());
  	}		
	   return resp;
  }
  
  @PostMapping(value="/updateIntervention/Autres")
  public Response updateInterventionAutres(@RequestParam("autres") String autres) throws IOException {
	  	Autres detail = new ObjectMapper().readValue(autres, Autres.class);
	  	Response resp = new Response();
  	try {
  		Autres.updateRow("Autres",detail);
  		resp = new Response(status_success,message_success);
  		
  	}catch(Exception ex) {
  		resp = new Response(status_error,message_error);
  		System.out.println(ex.getMessage());
  		System.out.println("Ato am erreur");
  	}		
	   return resp;
  }
  
  
  @GetMapping("/delete/intervention/imprimante/{id}")
	public Response DeleteInterventionImprimante(@PathVariable String id) throws ClassNotFoundException, Exception {
		Response resp = new Response();
		int idobj = Integer.parseInt(id);
	  	try {
	  		Imprimante.Delete("Imprimante","Imprimante_observation",idobj);
	  		resp = new Response(status_success,"Supression éffectuée");
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,"Supression non éfffectué");
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
		   return resp;
	}
  
  
  @PostMapping(value="/updateIntervention/Regulateur")
  public Response updateInterventionRegulateur(@RequestParam("regulateur") String regulateur) throws IOException {
	  	Regulateur detail = new ObjectMapper().readValue(regulateur, Regulateur.class);
	  	Response resp = new Response();
	  	System.out.println(regulateur);
  	try {
  		Regulateur.updateRow("Regulateur",detail);
  		resp = new Response(status_success,message_success);
  		
  	}catch(Exception ex) {
  		resp = new Response(status_error,message_error);
  		System.out.println(ex.getMessage());
  		System.out.println("Ato am erreur");
  	}		
	   return resp;
  }
  @GetMapping("/delete/intervention/regulateur/{id}")
	public Response DeleteInterventionRegulateur(@PathVariable String id) throws ClassNotFoundException, Exception {
		Response resp = new Response();
		int idobj = Integer.parseInt(id);
	  	try {
	  		Regulateur.Delete("Regulateur",idobj);
	  		resp = new Response(status_success,"Supression éffectuée");
	  		
	  	}catch(Exception ex) {
	  		resp = new Response(status_error,"Supression non éfffectué");
	  		System.out.println(ex.getMessage());
	  		System.out.println("Ato am erreur");
	  	}		
		   return resp;
	}
  
  
	
	
  @GetMapping("/liste/fiche/intervention/imprimante")
	public Response getAllImprimante() throws ClassNotFoundException, Exception {
	   Response resp = new Response();
	   try {
		   ArrayList<Imprimante> list = Imprimante.findAll("Imprimante");
		   resp = new Response(200,"Succes",list);
	   }catch(Exception ex) {
		   resp = new Response(status_error,message_error);
		   System.out.println(ex.getMessage());
	   }

	   return resp;
	}
  
  @GetMapping("/intervention/detail/onduleur/{id}")
	public ResponseEntity<Onduleur> getDetailInterventionOnduleur(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Onduleur list = Onduleur.getdetail_Intervention_Onduleur("Onduleur",id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
  
  @GetMapping("/detail/fiche/intervention/imprimante/{id}")
	public ResponseEntity<Imprimante> getDetailInterventionImprimante(@PathVariable String id) throws ClassNotFoundException, Exception {
	  	Imprimante list = Imprimante.getdetail_Intervention_Imprimante("Imprimante",id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
  
  @GetMapping("/imprimante/intervention/observation/{id}")
	public ResponseEntity<Imprimante_observation> getDetailInterventionObservation(@PathVariable String id) throws ClassNotFoundException, Exception {
	  Imprimante_observation list = Imprimante_observation.findOne("Imprimante_observation",id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/detail/intervention/autres/{id}")
	public ResponseEntity<Autres> getDetailInterventionAutres(@PathVariable String id) throws ClassNotFoundException, Exception {
		Autres list = Autres.getdetail_Intervention_Autres("Autres",id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
