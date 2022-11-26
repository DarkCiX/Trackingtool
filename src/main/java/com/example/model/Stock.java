package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.helpers.DbHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Stock {
	private ObjectId _id;
	private int id;
	private int id_produit;
	private int qtt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_produit() {
		return id_produit;
	}
	public void setId_produit(int id_produit) {
		this.id_produit = id_produit;
	}
	public int getQtt() {
		return qtt;
	}
	public void setQtt(int qtt) {
		this.qtt = qtt;
	}
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public Stock() {}
	
	public Stock(int id, int id_produit, int qtt) {
		this.setId(id);
		this.setId_produit(id_produit);
		this.setQtt(qtt);
	}
	
	public Stock(int id_produit, int qtt) {
		this.setId_produit(id_produit);
		this.setQtt(qtt);
	}
	
	public Stock(int qtt) {
		this.setQtt(qtt);
	}
	

	
//	public static ArrayList<Stock> findAll() throws Exception{
//		MongoDatabase db= DbHelper.getConnexionHoby();
//		MongoCollection<Document> coll=db.getCollection("stock");
//		ArrayList<Stock> cl=new ArrayList<Stock>();
//		MongoCursor<Document> curs=coll.find().iterator();
//		while(curs.hasNext())
//	    {
//			Document d=curs.next();
//			int id= d.getInteger("id");
//			int id_produit= d.getInteger("id_produit");
//			int qtt= d.getInteger("qtt");
//	        Stock c=new Stock(id,id_produit,qtt);
//	        cl.add(c);
//	    }
//		curs.close();
//		return cl;
//	}
	
}
