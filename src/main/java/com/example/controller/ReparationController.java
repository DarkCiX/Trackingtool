package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.helpers.DbHelper;
import com.example.model.Reclamation;
import com.example.model.Reparation;
import com.example.model.Reparation_Front;
import com.example.model.bon_E;
import com.example.model.bon_entree;
import com.example.model.bon_sortie;
import com.example.model.bon_sortie_produit;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class ReparationController {
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Saved";
    private String message_error = "Error";
    
    public ObjectMapper objectMapper = new ObjectMapper();
	 @PostMapping(value="/saveReparation")
	    public Response insertReparation(@RequestParam("id_utilisateur") String id_utilisateur,@RequestParam("id_entree") String id_entree,@RequestParam("id_produit") String id_produit,@RequestParam("date") String date) throws IOException, SQLException {
		   Connection con = null;
		   String[] prod =  objectMapper.readValue(id_produit, String[].class);
		   Response resp = new Response();
	    	try {
	    		con=DbHelper.connect();

	    
	    		int id = Integer.parseInt(id_entree);
	    		boolean list = bon_entree.CheckIfExist(con,id);
	    		if(list == true) {
	    			String message = "La réparation existe déjà!";
	    			resp = new Response(status_success,message);
	    		}else {
	    			bon_entree.setReparationvalide(con,id);
	    			Reparation.Save(con,id_utilisateur,id_entree,prod,date);
		    		resp = new Response(status_success,message_success);
	    		}
	    	}catch(Exception ex) {
	    		resp = new Response(status_error,message_error);
	    		System.out.println(ex.getMessage());
	    		System.out.println("Ato am erreur");
	    	}finally {
	    		con.close();
	    	}
		   return resp;
	    }
	 
	 @PostMapping(value="/UpdateReparation")
	    public Response UpdateReparation(@RequestParam("avancement") int avancement,@RequestParam("prevu") int prevu,@RequestParam("step") String step,@RequestParam("id") int id) throws IOException, SQLException {
		   Connection con = null;
		   Response resp = new Response();
	    	try {	
	    		con=DbHelper.connect();

    			Reparation.UpdateAvancement(con,avancement,prevu,step,id);
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
	 
	 	@GetMapping("/liste_reparation")
		public Response getAllReparation() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   ArrayList<Reparation_Front> list = Reparation_Front.GetAllWithEtat(con);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	
	 
	 	@GetMapping("/client/liste/tracking/{client}")
		public Response GetTracking(@PathVariable String client) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   ArrayList<Reparation_Front> list = Reparation_Front.GetClientReparation(con,client);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	}
	 

	 
	 	@GetMapping("/reparation/{id}")
		public Response getReparation(@PathVariable int id) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   Reparation_Front list = Reparation_Front.GetDetailReparation(con,id);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/reparation/bs/{facture}")
		public Response getReparationByreparation(@PathVariable String facture) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   Reparation_Front list = Reparation_Front.GetDetailReparationByFacture(con,facture);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/reparation/en_cours/{month}")
		public Response GetEncours(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Reparation_Front.En_cours(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/reparation/terminer/{month}")
		public Response GetTerminer(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Reparation_Front.Terminer(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/reparation/total/{month}")
		public Response GetTotal(@PathVariable String month) throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   int mois = Integer.parseInt(month);
		   try {
			   int list = Reparation_Front.Total(con,mois);
			   resp = new Response(200,"Succes",list);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	@GetMapping("/statistique/reparation/encours")
		public Response getStatistiqueEncours() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   double[] result = Reparation.StatistiqueInprogress(con);
			   resp = new Response(200,"Succes",result);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/statistique/reparation/repair")
		public Response getStatistiqueRepair() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   double[] result = Reparation.StatistiqueRepair(con);
			   resp = new Response(200,"Succes",result);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
	 	
	 	@GetMapping("/statistique/reparation/termined")
		public Response getStatistiqueTermined() throws ClassNotFoundException, Exception {
		   Connection con = DbHelper.connect();
		   Response resp = new Response();
		   try {
			   double[] result = Reparation.StatistiqueTermined(con);
			   resp = new Response(200,"Succes",result);
		   }catch(Exception ex) {
			   resp = new Response(status_error,message_error);
			   System.out.println(ex.getMessage());
		   }finally {
			   con.close();
		   }

		   return resp;
	 	}
}

