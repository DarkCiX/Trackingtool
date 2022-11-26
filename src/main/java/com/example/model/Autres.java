package com.example.model;

import java.util.ArrayList;

import org.bson.Document;

import com.example.helpers.DbHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Autres {
	public int id;
	public String date_intervention;
	public String debut_intervention;
	public String etat;
	public String fin_intervention;
	public String client;
	public String materiel;
	public String num_serie;
	public String date_achat;
	public String ref_facture;
	public String sollicitant;
	public String resume;
	public boolean garantie;
	public String panne;
	public String observation;
	public String fournir;
	public String fournis;
	public String sign_responsable;
	public String sign_technicien;
	public String sign_client;
	
	public Autres(int id, String date_inetevention, String debut_inetevention,String etat, String fin_inetevention, String client,
			String materiel, String num_serie, String date_achat, String ref_facture, String sollicitant, String resume,
			boolean garantie, String panne, String observation, String fournir, String fournis, String sign_responsable,
			String sign_technicien, String sign_client) {
		this.date_intervention = date_inetevention;
		this.debut_intervention = debut_inetevention;
		this.etat = etat;
		this.fin_intervention = fin_inetevention;
		this.client = client;
		this.materiel = materiel;
		this.num_serie = num_serie;
		this.date_achat = date_achat;
		this.ref_facture = ref_facture;
		this.sollicitant = sollicitant;
		this.resume = resume;
		this.garantie = garantie;
		this.panne = panne;
		this.observation = observation;
		this.fournir = fournir;
		this.fournis = fournis;
		this.sign_responsable = sign_responsable;
		this.sign_technicien = sign_technicien;
		this.sign_client = sign_client;
	}
	
	public Autres() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate_intervention() {
		return date_intervention;
	}
	public void setDate_intervention(String date_intervention) {
		this.date_intervention = date_intervention;
	}
	public String getDebut_intervention() {
		return debut_intervention;
	}
	public void setDebut_intervention(String debut_intervention) {
		this.debut_intervention = debut_intervention;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getFin_intervention() {
		return fin_intervention;
	}
	public void setFin_intervention(String fin_intervention) {
		this.fin_intervention = fin_intervention;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	public String getNum_serie() {
		return num_serie;
	}
	public void setNum_serie(String num_serie) {
		this.num_serie = num_serie;
	}
	public String getDate_achat() {
		return date_achat;
	}
	public void setDate_achat(String date_achat) {
		this.date_achat = date_achat;
	}
	public String getRef_facture() {
		return ref_facture;
	}
	public void setRef_facture(String ref_facture) {
		this.ref_facture = ref_facture;
	}
	public String getSollicitant() {
		return sollicitant;
	}
	public void setSollicitant(String sollicitant) {
		this.sollicitant = sollicitant;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public boolean getGarantie() {
		return garantie;
	}
	public void setGarantie(boolean garantie) {
		this.garantie = garantie;
	}
	public String getPanne() {
		return panne;
	}
	public void setPanne(String panne) {
		this.panne = panne;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getFournir() {
		return fournir;
	}
	public void setFournir(String fournir) {
		this.fournir = fournir;
	}
	public String getFournis() {
		return fournis;
	}
	public void setFournis(String fournis) {
		this.fournis = fournis;
	}
	public String getSign_responsable() {
		return sign_responsable;
	}
	public void setSign_responsable(String sign_responsable) {
		this.sign_responsable = sign_responsable;
	}
	public String getSign_technicien() {
		return sign_technicien;
	}
	public void setSign_technicien(String sign_technicien) {
		this.sign_technicien = sign_technicien;
	}
	public String getSign_client() {
		return sign_client;
	}
	public void setSign_client(String sign_client) {
		this.sign_client = sign_client;
	}


	 public static void save(String entity,Autres autre) throws Exception {
			 MongoClient conex=DbHelper.getConnexionMG();
			 MongoDatabase database = DbHelper.getDB(conex);
		  	MongoCollection<Autres> dbCollection =  database.getCollection(entity,Autres.class);
		  	int id = Autres.getMaxId("Autres");
			int newId = id +1;
			autre.setId(newId);
		  	dbCollection.insertOne(autre);
			   conex.close();
	  }
	 
	 public static int getMaxId(String entity) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Autres> dbCollection =  database.getCollection(entity,Autres.class);
		 int result = 0;
		 BasicDBObject obj = new BasicDBObject();
		 obj.put("_id",-1);
		 FindIterable<Autres> array = dbCollection.find().sort(obj).limit(1);
		 MongoCursor<Autres> cursor = array.iterator();
		 Autres document = new Autres();
		 if (cursor.hasNext()) {
			 document = cursor.next();
			 result = document.getId();
			  conex.close();
		 }
		  conex.close();
	      return result;

	 }

	 public static ArrayList<Autres> findAll(String entity) throws Exception{
		  MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Autres> dbCollection =  database.getCollection(entity,Autres.class);
		 FindIterable<Autres> array =  dbCollection.find();
		 MongoCursor<Autres> cursor = array.iterator();
		 ArrayList<Autres> rep = new ArrayList<Autres>();
		 int count =0;
		 while (cursor.hasNext()) {
	      Autres document = cursor.next();
	      rep.add(document);
	      count++;
		 }
		  conex.close();
	     return rep;
	 }
	 
	 public static void update(String entity,int id) throws Exception {
		  MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);

		 try {
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", id),new BasicDBObject("$set", new BasicDBObject("etat", "validé")));
			  conex.close();
		 }catch(Exception ex){
			 ex.getMessage();
		 }
		 
	 }
	 
	 public static void Delete(String entity,int id) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 try {
			 database.getCollection(entity).deleteOne(new Document("_id", id));
			  conex.close();
		 }catch(Exception ex){
			 ex.getMessage();
		 }
	 }
	 
	 public static void updateRow(String entity,Autres autre) throws Exception {
		  MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 try {
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("date_intervention", autre.getDate_intervention())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("fin_intervention", autre.getFin_intervention())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("etat", autre.getEtat())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("client", autre.getClient())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("materiel", autre.getMateriel())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("num_serie", autre.getNum_serie())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("date_achat", autre.getDate_achat())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("ref_facture", autre.getRef_facture())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("sollicitant", autre.getSollicitant())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("resume", autre.getResume())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("garantie", autre.getGarantie())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("panne", autre.getPanne())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("observation", autre.getObservation())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("fournir", autre.getFournir())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", autre.getId()),new BasicDBObject("$set", new BasicDBObject("fournis", autre.getFournis())));
			 
		 }catch(Exception ex){
			 ex.getMessage();
		 }
		  conex.close();
		 
	 }
	 
	 public static Autres getdetail_Intervention_Autres(String entity,String ids) throws Exception {
		  MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Autres> dbCollection =  database.getCollection(entity,Autres.class);
		 BasicDBObject obj = new BasicDBObject();
		 int id = Integer.parseInt(ids);
		 obj.append("_id", id);   
		 FindIterable<Autres> array =  dbCollection.find(obj);
		 MongoCursor<Autres> cursor = array.iterator();
		 Autres document = new Autres();
		 if (cursor.hasNext()) {
			 document = cursor.next();
			 
		 }
		 conex.close();
		 return document;
	 }
	
}
