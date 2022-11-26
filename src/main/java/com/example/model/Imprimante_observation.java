package com.example.model;

import java.util.ArrayList;

import com.example.helpers.DbHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Imprimante_observation {
	public int id;
	public int id_intervention;
	public String patin_observation;
	public String rouleau_observation;
	public String courroie_observation;
	public String fusion_observation;
	public String tambour_observation;
	public String dvp_observation;
	public String optique_observation;
	public String laser_observation;
	public String rv_observation;
	public String chargeur_observation;
	public String k7_observation;
	public String bypass_observation;
	public String carrosserie_observation;
	public String patin_autre;
	public String rouleau_autre;
	public String courroie_autre;
	public String fusion_autre;
	public String tambour_autre;
	public String dvp_autre;
	public String optique_autre;
	public String laser_autre;
	public String rv_autre;
	public String chargeur_autre;
	public String k7_autre;
	public String bypass_autre;
	public String carrosserie_autre;
	
	public Imprimante_observation(int id, int id_intervention, String patin_observation, String rouleau_observation,
			String courroie_observation, String fusion_observation, String tambour_observation, String dvp_observation,
			String optique_observation, String laser_observation, String rv_observation, String chargeur_observation,
			String k7_observation, String bypass_observation, String carrosserie_observation, String patin_autre,
			String rouleau_autre, String courroie_autre, String fusion_autre, String tambour_autre, String dvp_autre,
			String optique_autre, String laser_autre, String rv_autre, String chargeur_autre, String k7_autre,
			String bypass_autre, String carrosserie_autre) {
		super();
		this.id = id;
		this.id_intervention = id_intervention;
		this.patin_observation = patin_observation;
		this.rouleau_observation = rouleau_observation;
		this.courroie_observation = courroie_observation;
		this.fusion_observation = fusion_observation;
		this.tambour_observation = tambour_observation;
		this.dvp_observation = dvp_observation;
		this.optique_observation = optique_observation;
		this.laser_observation = laser_observation;
		this.rv_observation = rv_observation;
		this.chargeur_observation = chargeur_observation;
		this.k7_observation = k7_observation;
		this.bypass_observation = bypass_observation;
		this.carrosserie_observation = carrosserie_observation;
		this.patin_autre = patin_autre;
		this.rouleau_autre = rouleau_autre;
		this.courroie_autre = courroie_autre;
		this.fusion_autre = fusion_autre;
		this.tambour_autre = tambour_autre;
		this.dvp_autre = dvp_autre;
		this.optique_autre = optique_autre;
		this.laser_autre = laser_autre;
		this.rv_autre = rv_autre;
		this.chargeur_autre = chargeur_autre;
		this.k7_autre = k7_autre;
		this.bypass_autre = bypass_autre;
		this.carrosserie_autre = carrosserie_autre;
	}
	public String getPatin_autre() {
		return patin_autre;
	}
	public void setPatin_autre(String patin_autre) {
		this.patin_autre = patin_autre;
	}
	public String getRouleau_autre() {
		return rouleau_autre;
	}
	public void setRouleau_autre(String rouleau_autre) {
		this.rouleau_autre = rouleau_autre;
	}
	public String getCourroie_autre() {
		return courroie_autre;
	}
	public void setCourroie_autre(String courroie_autre) {
		this.courroie_autre = courroie_autre;
	}
	public String getFusion_autre() {
		return fusion_autre;
	}
	public void setFusion_autre(String fusion_autre) {
		this.fusion_autre = fusion_autre;
	}
	public String getTambour_autre() {
		return tambour_autre;
	}
	public void setTambour_autre(String tambour_autre) {
		this.tambour_autre = tambour_autre;
	}
	public String getDvp_autre() {
		return dvp_autre;
	}
	public void setDvp_autre(String dvp_autre) {
		this.dvp_autre = dvp_autre;
	}
	public String getOptique_autre() {
		return optique_autre;
	}
	public void setOptique_autre(String optique_autre) {
		this.optique_autre = optique_autre;
	}
	public String getLaser_autre() {
		return laser_autre;
	}
	public void setLaser_autre(String laser_autre) {
		this.laser_autre = laser_autre;
	}
	public String getRv_autre() {
		return rv_autre;
	}
	public void setRv_autre(String rv_autre) {
		this.rv_autre = rv_autre;
	}
	public String getChargeur_autre() {
		return chargeur_autre;
	}
	public void setChargeur_autre(String chargeur_autre) {
		this.chargeur_autre = chargeur_autre;
	}
	public String getK7_autre() {
		return k7_autre;
	}
	public void setK7_autre(String k7_autre) {
		this.k7_autre = k7_autre;
	}
	public String getBypass_autre() {
		return bypass_autre;
	}
	public void setBypass_autre(String bypass_autre) {
		this.bypass_autre = bypass_autre;
	}
	public String getCarrosserie_autre() {
		return carrosserie_autre;
	}
	public void setCarrosserie_autre(String carrosserie_autre) {
		this.carrosserie_autre = carrosserie_autre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_intervention() {
		return id_intervention;
	}
	public void setId_intervention(int id_intervention) {
		this.id_intervention = id_intervention;
	}
	public String getPatin_observation() {
		return patin_observation;
	}
	public void setPatin_observation(String patin_observation) {
		this.patin_observation = patin_observation;
	}
	public String getRouleau_observation() {
		return rouleau_observation;
	}
	public void setRouleau_observation(String rouleau_observation) {
		this.rouleau_observation = rouleau_observation;
	}
	public String getCourroie_observation() {
		return courroie_observation;
	}
	public void setCourroie_observation(String courroie_observation) {
		this.courroie_observation = courroie_observation;
	}
	public String getFusion_observation() {
		return fusion_observation;
	}
	public void setFusion_observation(String fusion_observation) {
		this.fusion_observation = fusion_observation;
	}
	public String getTambour_observation() {
		return tambour_observation;
	}
	public void setTambour_observation(String tambour_observation) {
		this.tambour_observation = tambour_observation;
	}
	public String getDvp_observation() {
		return dvp_observation;
	}
	public void setDvp_observation(String dvp_observation) {
		this.dvp_observation = dvp_observation;
	}
	public String getOptique_observation() {
		return optique_observation;
	}
	public void setOptique_observation(String optique_observation) {
		this.optique_observation = optique_observation;
	}
	public String getLaser_observation() {
		return laser_observation;
	}
	public void setLaser_observation(String laser_observation) {
		this.laser_observation = laser_observation;
	}
	public String getRv_observation() {
		return rv_observation;
	}
	public void setRv_observation(String rv_observation) {
		this.rv_observation = rv_observation;
	}
	public String getChargeur_observation() {
		return chargeur_observation;
	}
	public void setChargeur_observation(String chargeur_observation) {
		this.chargeur_observation = chargeur_observation;
	}
	public String getK7_observation() {
		return k7_observation;
	}
	public void setK7_observation(String k7_observation) {
		this.k7_observation = k7_observation;
	}
	public String getBypass_observation() {
		return bypass_observation;
	}
	public void setBypass_observation(String bypass_observation) {
		this.bypass_observation = bypass_observation;
	}
	public String getCarrosserie_observation() {
		return carrosserie_observation;
	}
	public void setCarrosserie_observation(String carrosserie_observation) {
		this.carrosserie_observation = carrosserie_observation;
	}

	public Imprimante_observation() {}
	 public static Imprimante_observation findOne(String entity, String id) throws Exception{
		 MongoClient conex=DbHelper.getConnexionMG();
		 MongoDatabase database = DbHelper.getDB(conex);
		 MongoCollection<Imprimante_observation> dbCollection =  database.getCollection(entity,Imprimante_observation.class);
		 BasicDBObject obj = new BasicDBObject();
		 int ids = Integer.parseInt(id);
		 obj.append("_id", ids);   
		 FindIterable<Imprimante_observation> array =  dbCollection.find(obj);
		 MongoCursor<Imprimante_observation> cursor = array.iterator();
		 Imprimante_observation document = new Imprimante_observation();
		 if (cursor.hasNext()) {
			 document = cursor.next();
//	      document.getId();
	      System.out.println(document.getChargeur_observation());
	     
		
		
		 }
		conex.close();

		  return document;
	 }
}
