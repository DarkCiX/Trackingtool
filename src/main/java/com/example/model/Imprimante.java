package com.example.model;

import java.util.ArrayList;

import org.bson.Document;

import com.example.helpers.DbHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Imprimante {
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
	public int noirblanc;
	public int couleur;
	public String resume;
	public boolean garantie;
	public String ce;
	public String check;
	public String panne;
	public String observation;
	public String fournir;
	public String fournis;
	public String sign_responsable;
	public String sign_technicien;
	public String sign_client;
	public Imprimante(int id, String date_intervention, String debut_intervention,String etat, String fin_intervention,
			String client, String materiel, String num_serie, String date_achat, String ref_facture, String sollicitant,
			int noirblanc,int couleur,String resume, boolean garantie, String ce,String check, String panne, String observation, String fournir,
			String fournis, String sign_responsable, String sign_technicien, String sign_client) {
		this.id = id;
		this.date_intervention = date_intervention;
		this.debut_intervention = debut_intervention;
		this.etat = etat;
		this.fin_intervention = fin_intervention;
		this.client = client;
		this.materiel = materiel;
		this.num_serie = num_serie;
		this.date_achat = date_achat;
		this.ref_facture = ref_facture;
		this.sollicitant = sollicitant;
		this.noirblanc = noirblanc;
		this.couleur = couleur;
		this.resume = resume;
		this.garantie = garantie;
		this.ce = ce;
		this.check = check;
		this.panne = panne;
		this.observation = observation;
		this.fournir = fournir;
		this.fournis = fournis;
		this.sign_responsable = sign_responsable;
		this.sign_technicien = sign_technicien;
		this.sign_client = sign_client;
	}
	public Imprimante() {}
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
	public String getFin_intervention() {
		return fin_intervention;
	}
	public void setFin_intervention(String fin_intervention) {
		this.fin_intervention = fin_intervention;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
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
	public int getNoirblanc() {
		return noirblanc;
	}
	public void setNoirblanc(int noirblanc) {
		this.noirblanc = noirblanc;
	}
	public int getCouleur() {
		return couleur;
	}
	public void setCouleur(int couleur) {
		this.couleur = couleur;
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
	public String getCe() {
		return ce;
	}
	public void setCe(String ce) {
		this.ce = ce;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
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
	
	 public static void save(String entity1,Imprimante imp,String entity2,Imprimante_observation observation) throws Exception {
		  	 MongoClient conex=DbHelper.getConnexionMG();
		 	MongoDatabase database = DbHelper.getDB(conex);
		  	try{
			  	MongoCollection<Imprimante> dbCollection =  database.getCollection(entity1,Imprimante.class);
//				int id=(int)dbCollection.countDocuments();
				int id = Imprimante.getMaxId("Imprimante");
				System.out.println(id);
				int newId = id +1;
				System.out.println(newId);
				imp.setId(newId);
			  	dbCollection.insertOne(imp);

			  	MongoCollection<Imprimante_observation> dbCollection2 =  database.getCollection(entity2,Imprimante_observation.class);
				int id_observation =(int)dbCollection2.countDocuments();
				int newId_observation = id +1;
				observation.setId(newId_observation);
				observation.setId_intervention(imp.getId());
				dbCollection2.insertOne(observation);
				conex.close();
		  	}catch(Exception ex) {
		  		System.out.println(ex.getMessage());
		  	}
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
	 
	 public static void updateRow(String entity,String entity2,Imprimante imp,Imprimante_observation observation) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 try {
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("date_intervention", imp.getDate_intervention())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("fin_intervention", imp.getFin_intervention())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("etat", imp.getEtat())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("client", imp.getClient())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("materiel", imp.getMateriel())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("num_serie", imp.getNum_serie())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("date_achat", imp.getDate_achat())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("ref_facture", imp.getRef_facture())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("sollicitant", imp.getSollicitant())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("noirblanc", imp.getNoirblanc())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("couleur", imp.getCouleur())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("resume", imp.getResume())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("garantie", imp.getGarantie())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("ce", imp.getCe())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("panne", imp.getPanne())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("observation", imp.getObservation())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("fournir", imp.getFournir())));
			 database.getCollection(entity).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("fournis", imp.getFournis())));

			 
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("patin_observation", observation.getPatin_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("rouleau_observation", observation.getRouleau_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("courroie_observation", observation.getCourroie_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("fusion_observation", observation.getFusion_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("tambour_observation", observation.getTambour_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("dvp_observation", observation.getDvp_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("optique_observation", observation.getOptique_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("laser_observation", observation.getLaser_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("rv_observation", observation.getRv_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("chargeur_observation", observation.getChargeur_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("k7_observation", observation.getK7_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("bypass_observation", observation.getBypass_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("carrosserie_observation", observation.getCarrosserie_observation())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("patin_autre", observation.getPatin_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("rouleau_autre", observation.getRouleau_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("courroie_autre", observation.getCourroie_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("fusion_autre", observation.getFusion_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("dvp_autre", observation.getDvp_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("optique_autre", observation.getOptique_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("laser_autre", observation.getLaser_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("rv_autre", observation.getRv_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("chargeur_autre", observation.getChargeur_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("k7_autre", observation.getK7_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("bypass_autre", observation.getBypass_autre())));
			 database.getCollection(entity2).updateOne(new BasicDBObject("_id", imp.getId()),new BasicDBObject("$set", new BasicDBObject("carrosserie_autre", observation.getCarrosserie_autre())));
			 conex.close();
		 }catch(Exception ex){
			 ex.getMessage();
		 }
		 
	 }
	 
	 public static int getMaxId(String entity) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Imprimante> dbCollection =  database.getCollection(entity,Imprimante.class);
		 int result = 0;
		 BasicDBObject obj = new BasicDBObject();
		 obj.put("_id",-1);
		 FindIterable<Imprimante> array = dbCollection.find().sort(obj).limit(1);
		 MongoCursor<Imprimante> cursor = array.iterator();
		 Imprimante document = new Imprimante();
		 if (cursor.hasNext()) {
			 document = cursor.next();
			 result = document.getId();
		 } 
		conex.close();

		 return result;
	 }
	 public static void Delete(String entity,String entity2,int id) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 try {
			 database.getCollection(entity).deleteOne(new Document("_id", id));
			 database.getCollection(entity2).deleteOne(new Document("_id", id));
			 		conex.close();

		 }catch(Exception ex){
			 ex.getMessage();
		 }
	 }
//	 ArrayList<Autres>
	 public static ArrayList<Imprimante> findAll(String entity) throws Exception{
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Imprimante> dbCollection =  database.getCollection(entity,Imprimante.class);
		 FindIterable<Imprimante> array =  dbCollection.find();
		 MongoCursor<Imprimante> cursor = array.iterator();
		 ArrayList<Imprimante> rep = new ArrayList<Imprimante>();
		 int count  = 0;
		 while (cursor.hasNext()) {
			 Imprimante document = cursor.next();
			 rep.add(document);
			 count++;
		 } 
		 		conex.close();

	      return rep;
	 }
	 
	 public static Imprimante getdetail_Intervention_Imprimante(String entity,String ids) throws Exception {
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Imprimante> dbCollection =  database.getCollection(entity,Imprimante.class);
		 BasicDBObject obj = new BasicDBObject();
		 int id = Integer.parseInt(ids);
		 obj.append("_id", id);   
		 FindIterable<Imprimante> array =  dbCollection.find(obj);
		 MongoCursor<Imprimante> cursor = array.iterator();
		 Imprimante document = new Imprimante();
		 if (cursor.hasNext()) {
			 document = cursor.next();
	     
		 } 
		conex.close();

		 return document;
	 }
}
