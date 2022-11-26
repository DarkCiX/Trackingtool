package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.helpers.DbHelper;
import com.example.model.*;
import com.example.status.Response;
import com.exemple.service.GenericDao;



@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class ClientController {
//	private Connection con = DbHelper.connect();
	private int status_error = 404;
    private int status_success = 200;
    private String message_success = "Data receveid";
    private String message_error = "Error";

	@GetMapping("/client")
	public ResponseEntity<List<Client>> getAll() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String entity = "Client";
		Client p = new Client();
		Connection con = DbHelper.connect();
	    List<Object> pos = GenericDao.FindAll(entity,p,con);
        List<Client> list = new ArrayList<Client>();
        for(int i=0;i<pos.size();i++){
            list.add((Client) pos.get(i));
        }
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/technicien")
	public Response getAllUtilisateur() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String entity = "Client";
		Connection con = DbHelper.connect();
		Response rep = new Response();
		ArrayList<Utilisateur> list = new ArrayList<Utilisateur>();
		try {
			list = Utilisateur.getUtilisateurByType(con);
			rep = new Response(status_success,message_success,list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rep = new Response(status_error,message_error);
		}finally {
			
		}
		return rep;
	}
	


//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@GetMapping("/client/{id}")
	public ResponseEntity<Interlocuteur> getByInterlocuteurId(@PathVariable String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection con = DbHelper.connect();
		Interlocuteur client = Interlocuteur.FindById(id, con);
		return new ResponseEntity<>(client,HttpStatus.OK);
	}
	
	@GetMapping("/particulier/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable String id) throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException {
		Connection con = DbHelper.connect();
		Client client = Client.Find(id, con);
		return new ResponseEntity<>(client,HttpStatus.OK);
	}
}
