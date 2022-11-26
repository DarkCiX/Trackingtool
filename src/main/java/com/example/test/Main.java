//package com.example.test;
//
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.zip.Deflater;
//
//import org.bson.codecs.configuration.CodecRegistry;
//import org.bson.codecs.pojo.PojoCodecProvider;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//
//import com.example.helpers.DbHelper;
//import com.example.model.Autres;
//import com.example.model.BaseObject;
//import com.example.model.Client;
//import com.example.model.Detail_devis;
//import com.example.model.Imprimante;
//import com.example.model.Imprimante_observation;
//import com.example.model.Interlocuteur;
//import com.example.model.Onduleur;
//import com.example.model.Reclamation;
//import com.example.model.Reparation_Front;
//import com.example.model.Stock;
//import com.example.model.Utilisateur;
//import com.example.model.bon_entree;
//import com.example.model.devis_client;
//import com.exemple.service.Function;
//import com.exemple.service.GenericDao;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//@SpringBootApplication
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
//public class Main {
//	public static void main(String[] args) throws Exception {
////		String facture="BE_025348636";
////		Connection con = DbHelper.connect();
////		MongoDatabase mongo =  DbHelper.getConnexionHoby();
////	    bon_entree list = bon_entree.getdetail_bon_entree(con,facture);
////	    System.out.println(list.getFournisseur());
////		Connection con =DbHelper.connect();
////		Interlocuteur client = Interlocuteur.FindById(id, con);
////		System.out.println(client.getId());
////		System.out.println(client.getNom());
////		Interlocuteur ints = new Interlocuteur();
////		ints.setId(2);
////		ints.setId_client(1);
////		ints.setContact("135435453");
////		ints.setEmail("fanoa@gmail.com");
////		ints.setNom("Fanoa");
////	    MongoDatabase mongo = DbHelper.getConnexionMongodb();
////	    MongoDatabase database = DbHelper.getConnexionMongodb();
////	  	MongoCollection<Interlocuteur> dbCollection =  database.getCollection("Interlocuteur",Interlocuteur.class);
////	  	dbCollection.insertOne(ints);
////	  	Function.insertObject("Interlocuteur",ints);
////		ArrayList<Autres> re = Autres.findAll("Autres");
////		for(int i=0;i<re.size();i++) {
////			System.out.println(re.get(i).getFin_intervention());
////		}
//		
////		Imprimante_observation im = Imprimante_observation.findOne("Imprimante_observation","1");
////		System.out.println(im.getFusion_observation());
//		
////		ArrayList<Onduleur> ond= Onduleur.findAll("Onduleur");
////		for(int i=0;i<ond.size();i++) {
////			System.out.println(ond.get(i).getId());
////		}
////		Utilisateur fo = new Utilisateur();
////		Utilisateur u = fo.getUtilisateur(con);		
////		System.out.print(u.id_type_user);
//		
////		boolean cs = bon_entree.CheckIfExist(con, 1);
////		System.out.println(cs);
////		
//		
////		ArrayList<Reparation_Front> one = Reparation_Front.GetClientReparation(con,"ETP");
////			for(int i=0;i<one.size();i++) {
////			System.out.println(one.get(i).getClient());
////		}
//		
//		
////		String text = "kjhkjfhgdkughfduhgkufghkuwefrghjukasfghjuykagwferkjutkjultrahgfkjugfhrkjulrhguiraghlkedrijhtgeagukhrksjhgkdhsufgufdhksudhgruhgrke;ujhgrkjuhuhgftdfshuiphsgkjhsfgdkjlgdhsjskj";
////		String  front = Reparation_Front.compressAndReturnB64(text);
////		System.out.println(front);
////		ArrayList<Reparation_Front> list = new ArrayList<Reparation_Front>();
////        list = Reparation_Front.GetAllWithEtat(con);
////        System.out.println(list.size());
////        for(Reparation_Front s : list) {
////        	System.out.println(s.getEtat());
////        }
////        
////        int moi = 11;
////        int encours = Detail_devis.En_cours(con,moi);
////        int termine = Detail_devis.Valider(con,moi);
////        int total = Detail_devis.Total(con,moi);
////        System.out.println(encours);
////        System.out.println(termine);
////        System.out.println(total);
//        
////        int result = 0;
////        result = Imprimante.getMaxId("Imprimante");
////        System.out.println(result);
//	}		
//	
//	    
//}
