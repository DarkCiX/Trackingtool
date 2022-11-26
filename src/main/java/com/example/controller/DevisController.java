package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Client;
import com.example.model.Detail_devis;
import com.example.model.Imprimante;
import com.example.model.Interlocuteur;
import com.example.model.Reparation_Front;
import com.example.model.bon_entree;
import com.example.model.devis_client;
import com.example.model.devis_produit;
import com.example.status.Response;
import com.exemple.service.GenericDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class DevisController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    public Gson gson = new Gson();
    
    public ObjectMapper objectMapper = new ObjectMapper();
	
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/saveDevis")
	   public Response insertDevis(@RequestParam("info") String info,@RequestParam("product") String product) throws IOException, SQLException {
		   Connection con = null;
		   Detail_devis detail = new ObjectMapper().readValue(info, Detail_devis.class);
		   devis_produit[] produit =  objectMapper.readValue(product, devis_produit[].class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();
	    		Detail_devis.save(con,detail,produit);
	    		resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    	}finally {
	    		con.close();
	    	}	
		   return resp;
	    }
	   
	   @GetMapping("/delete/devis/{facture}")
		public Response DeleteBonE(@PathVariable String facture) throws ClassNotFoundException, Exception {
		  Response resp = new Response();
		  Connection con = DbHelper.connect();
		  	try {
		  		Detail_devis.DeleteDevis(con,facture);
		  		resp = new Response(status_success,"Suppression réussite");
		  		
		  	}catch(Exception ex) {
		  		resp = new Response(status_error,"Suppression erreur");
		  	}finally {
		  		con.close();
		  	}	
		return resp;
	   }
	   
	   @GetMapping("/devis_detail")
		public ResponseEntity<ArrayList<devis_client>> getAll() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   ArrayList<devis_client> list = devis_client.GetAll(con);
		   con.close();
		   return new ResponseEntity<>(list,HttpStatus.OK);
		}
	   
	   @GetMapping("/detail/{facture}")
		public ResponseEntity<devis_client> getDetailfact(@PathVariable String facture) throws Exception {
			Connection con = DbHelper.connect();
			devis_client client = devis_client.getdetail_devis(con,facture);
			con.close();
		   return new ResponseEntity<>(client,HttpStatus.OK);
		}
		
		@GetMapping("/produit/{facture}")
		public ResponseEntity<ArrayList<devis_produit>> GetProduit(@PathVariable String facture) throws Exception {
			Connection con = DbHelper.connect();
			ArrayList<devis_produit> client = devis_produit.GetAll(con,facture);
			con.close();
		   return new ResponseEntity<>(client,HttpStatus.OK);
		}
		
		@GetMapping("/sum/{facture}")
		public ResponseEntity<Float>  GetProduitSum(@PathVariable String facture) throws Exception {
			Connection con = DbHelper.connect();
			float rep = devis_produit.GetSumPrice(con,facture);
			con.close();
		    return new ResponseEntity<>(rep,HttpStatus.OK);
		}
		
		@GetMapping("/update/devis/{facture}")
		public Response SetValideDevis(@PathVariable String facture) throws ClassNotFoundException, Exception {
		  Response resp = new Response();
		  Connection con = DbHelper.connect();
		  	try {
		  		Detail_devis.Update(con,facture);
		  		resp = new Response(status_success,"Validation réussite");
		  		
		  	}catch(Exception ex) {
		  		resp = new Response(status_error,"Validation erreur");
		  	}finally {
		  		con.close();
		  	}	
		return resp;
	  }
		
		@GetMapping("/devis/en_cours/{month}")
		public Response GetEncours(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Detail_devis.En_cours(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/devis/valider/{month}")
		public Response GetTerminer(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Detail_devis.Valider(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/devis/total/{month}")
		public Response GetTotal(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Detail_devis.Total(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
}
