package com.example.helpers;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.sql.DriverManager;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DbHelper {
    
	    
     public static Connection connect() throws ClassNotFoundException {
        Connection conn = null;
        try {
        	 Class.forName("org.postgresql.Driver");
        	 String url = "jdbc:postgresql://ec2-34-235-198-25.compute-1.amazonaws.com/d7i6bg089dk5c0";
        	 String user = "kdfikqjjenbivs";
             String password = "8be015f78487a80e4acb8a44a95af91792ab7d9d0bd5632a037c128173d0c56b";

            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
     
     public static MongoDatabase getConnexionMongodb() throws Exception {
    	 
         String url = "mongodb+srv://chantony:neverdie1122@cluster0.exkat.mongodb.net/?retryWrites=true&w=majority&maxIdleTimeMS=15000";
         MongoClient mongo = null;
         MongoDatabase database = null;
         try {
         	mongo = MongoClients.create(url);
         	CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
         	database = mongo.getDatabase("test").withCodecRegistry(pojoCodecRegistry);;
         	System.out.println("Opened MongoDb database successfully");
         } catch(Exception ex) {
 	    	throw ex;
 	    }
 	    return database;
     }
     
     
 public static MongoClient getConnexionMG() throws Exception {
    	 
         String url = "mongodb+srv://chantony:neverdie1122@cluster0.exkat.mongodb.net/?retryWrites=true&w=majority&maxIdleTimeMS=15000";
         MongoClient mongo = null;
         try {
         	mongo = MongoClients.create(url);
         	System.out.println("Opened MongoDb database successfully");
         } catch(Exception ex) {
 	    	throw ex;
 	    }
 	    return mongo;
     }
 
 public static MongoDatabase getDB(MongoClient con) throws Exception{
	 MongoDatabase mongo=null;
	 try {
      	CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
      	mongo = con.getDatabase("test").withCodecRegistry(pojoCodecRegistry);;

	 }
	 catch(Exception e ) {
		 e.printStackTrace();
	 }
	 
	 return mongo;
 }
 
 
     
     

}
