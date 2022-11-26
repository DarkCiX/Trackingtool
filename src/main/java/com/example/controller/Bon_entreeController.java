package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.example.model.Reparation_Front;
import com.example.model.bon_E;
import com.example.model.bon_S;
import com.example.model.bon_entree;
import com.example.model.bon_entree_produit;
import com.example.model.bon_sortie;
import com.example.model.bon_sortie_produit;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class Bon_entreeController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    public ObjectMapper objectMapper = new ObjectMapper();
	
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/saveBon_entree")
	    public Response insertBonEntree(@RequestParam("info") String info,@RequestParam("product") String product) throws IOException {
		   Connection con = null;
		   bon_entree detail = new ObjectMapper().readValue(info, bon_entree.class);
		   bon_entree_produit[] produit =  objectMapper.readValue(product, bon_entree_produit[].class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();
	    		bon_entree.Save(con,detail,produit);
	    		resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}		
		   return resp;
	    }
	   
	   
	   @PostMapping(value="/saveBon_sortie")
	    public Response insertBonSortie(@RequestParam("detail") String info,@RequestParam("produit") String product) throws IOException, SQLException {
		   Connection con = null;
		   bon_sortie detail = new ObjectMapper().readValue(info, bon_sortie.class);
		   bon_sortie_produit[] prod =  objectMapper.readValue(product, bon_sortie_produit[].class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();

	    		bon_entree.setSortievalide(con,detail.getNum_bon_entree());
	    		bon_sortie.Save(con,detail,prod);
	    		
	    		resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}finally {
	    		con.close();
	    	}		
		   return resp;
	    }
	   
	   
	   
	   @PostMapping(value="/UpdateBon_entree")
	    public Response UpdateBonSortie(@RequestParam("detail") String info,@RequestParam("product") String product) throws IOException {
		   Connection con =null;
		   bon_entree detail = new ObjectMapper().readValue(info, bon_entree.class);
		   bon_entree_produit[] prod =  objectMapper.readValue(product, bon_entree_produit[].class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();

	    		bon_entree.ModifiBE(con,detail,prod);
	    		resp = new Response(status_success,message_success);
	    		
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}		
		   return resp;
	    }
	   
	   @GetMapping("/update/entree/{id}")
		public Response SetValideBonE(@PathVariable String id) throws ClassNotFoundException, Exception {
		  Response resp = new Response();
		  Connection con = DbHelper.connect();
		  int idnew = Integer.parseInt(id);
		  	try {
		  		bon_entree.UpdateBE(con,idnew);
		  		resp = new Response(status_success,"Validation réussite");
		  		
		  	}catch(Exception ex) {
		  		resp = new Response(status_error,"Validation erreur");
		  	}finally {
		  		con.close();
		  	}	
		return resp;
	  }
	   
	   
	   
	   
   
	   @GetMapping("/delete/entree/{id}")
		public Response DeleteBonE(@PathVariable String id) throws ClassNotFoundException, Exception {
		  Response resp = new Response();
		  Connection con = DbHelper.connect();
		  	try {
		  		bon_entree.DeleteBon_entree(con,id);
		  		resp = new Response(status_success,"Suppression réussite");
		  		
		  	}catch(Exception ex) {
		  		resp = new Response(status_error,"Suppression erreur");
		  	}finally {
		  		con.close();
		  	}	
		return resp;
	   }



	   @GetMapping("/bon_entree_detail")
		public Response getAllDetail() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   ArrayList<bon_E> list = bon_E.GetAllBon_E(con);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }

		   return resp;
		}
	   
	   @GetMapping("/bon_sortie_detail")
		public Response getAllDetailSortie() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   ArrayList<bon_S> list = bon_S.GetAllBon_S(con);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }

		   return resp;
	   }
	   
	 
		
	   

	   @GetMapping("/bon_entree/{facture}")
		public ResponseEntity<bon_E> getAllByFacture(@PathVariable String facture) throws ClassNotFoundException, Exception {
		    Connection con = DbHelper.connect();
		    bon_E list = bon_E.getdetail_bon_E(con,facture);
			return new ResponseEntity<>(list,HttpStatus.OK);
	   }
	   
	   @GetMapping("/bon_entree_produit/{facture}")
		public ResponseEntity<ArrayList<bon_entree_produit>> getAllProduit(@PathVariable String facture) throws ClassNotFoundException, Exception {
		    Connection con = DbHelper.connect();
		    ArrayList<bon_entree_produit> list = bon_entree_produit.GetAll(con,facture);
			return new ResponseEntity<>(list,HttpStatus.OK);
	  }
	   
	   
	   @GetMapping("/bon_sortie/{facture}")
		public ResponseEntity<bon_S> getSortieByFacture(@PathVariable String facture) throws ClassNotFoundException, Exception {
		    Connection con = DbHelper.connect();
		    bon_S list = bon_S.getdetail_bon_S(con,facture);
			return new ResponseEntity<>(list,HttpStatus.OK);
		}
	   
	   @GetMapping("/bon_sortie_produit/{facture}")
		public ResponseEntity<ArrayList<bon_sortie_produit>> getSortieProduit(@PathVariable String facture) throws ClassNotFoundException, Exception {
		    Connection con = DbHelper.connect();
		    ArrayList<bon_sortie_produit> list = bon_sortie_produit.GetAll(con,facture);
			return new ResponseEntity<>(list,HttpStatus.OK);
	  }
	   
	   @GetMapping("/bon_entree/non_valide/{month}")
		public Response GetNonvalide(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   double list = bon_entree.StatistiqueNOBEvalide(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	   @GetMapping("/bon_entree/valide/{month}")
		public Response GetValide(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   double list = bon_entree.StatistiqueBEvalide(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	   @GetMapping("/bon_entree/total/{month}")
		public Response GetTotal(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   double list = bon_entree.StatistiqueTotalBE(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	   
}
