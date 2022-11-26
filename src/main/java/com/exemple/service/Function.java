package com.exemple.service;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

import com.example.helpers.DbHelper;
import com.example.model.BaseObject;
import com.example.model.Interlocuteur;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Function {
	 public static String CreateSQL(String entity,Object obj,CRUD_type type) throws SQLException{
	        Field all[] = obj.getClass().getDeclaredFields();
	        List<String> list = getColumndb(obj);
	        String query="";
	        if(type == CRUD_type.INSERT){
	             query = "INSERT INTO "+entity + "(";
	             for (int j = 0; j < list.size(); j++)
	            {
	                if (list.size() - 1 != j)
	                {
	                    query = query  + list.get(j) + ",";
	                }
	                else
	                {
	                    query = query  + list.get(j) + ") VALUES(";
	                    for(int i=0;i<all.length;i++){
	                        if(all.length-1 !=i){
	                            query = query + "?,";
	                        }
	                        else{
	                            query = query + "?)";
	                        }
	                    }
	                }
	            }
	          }
	          if(type == CRUD_type.SELECT){
	              query = "SELECT * FROM "+entity;
	          }
	          if(type == CRUD_type.DELETE){
	              query = "DELETE FROM "+entity;
	          }
	          return query;
	    }
	    
	  public static String getPrimaryKey(String entity,Object obj){
	        String key="";
	        try{
	            Connection con = DbHelper.connect();
	            String query = "SELECT a.attname, format_type(a.atttypid, a.atttypmod) AS data_type\n" +
	            "FROM   pg_index i\n" +
	            "JOIN   pg_attribute a ON a.attrelid = i.indrelid\n" +
	            "                     AND a.attnum = ANY(i.indkey)\n" +
	            "WHERE  i.indrelid ='"+entity+"' ::regclass\n" +
	            "AND    i.indisprimary;";
	            Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(query);
	            while(rs.next()){
	               key = (String)rs.getObject(1);
	            }
	        }catch(Exception ex){
	            System.out.print(ex.getMessage());
	        }
	        return key;
	    }
	     
	  public static boolean isGetter(Method method){
	    if((method.getName().startsWith("get") || method.getName().startsWith("is")) 
	        && method.getParameterCount() == 0 && !method.getReturnType().equals(void.class)){
	      return true;
	    }
	    return false; 
	  }

	  public  static boolean isSetter(Method method){
	    if(method.getName().startsWith("set") && method.getParameterCount() == 1 
	        && method.getReturnType().equals(void.class)){
	      return true;
	    }
	    return false; 
	  }
	  
	  public static List<String> getColumndb(Object obj) throws SQLException{
	        List<String> list = new ArrayList<String>();
	        Field[] field = obj.getClass().getDeclaredFields();
	        for (Field fields : field) {
	            list.add(fields.getName());
	        }
	        return list; 
	    }
	    
	  public static List<String> removePk(List<String> field,String entity,Object obj){
	        List<String> rep = new ArrayList<String>();
	        String pk = getPrimaryKey(entity,obj);
	        for(int t=0;t<field.size();t++){
	            if(field.get(t).contains(pk)==true){
	                field.remove(t);
	                rep.add(field.get(t));
	            }else{
	                rep.add(field.get(t));
	            }
	        }
	        return rep;
	    }
	    
	  public static String SQL(Object obj,String entity,CRUD_type type) throws SQLException{
	        String pk = getPrimaryKey(entity,obj);
	        String query ="";
	        if(type == CRUD_type.UPDATE){
	            query= "UPDATE "+entity + " set ";
	            List<String> column = getColumndb(obj);
	            List<String> field = removePk(column,entity,obj);
	            for(int j=0;j<field.size();j++){
	                if(field.size() - 1 != j){
	                    query = query + field.get(j) + " = ? , ";
	                }else{
	                    query = query + field.get(j) + " =? WHERE " + pk + " = ?";
	                }
	            }
	        }
	        if(type==CRUD_type.DELETE){
	            query = "DELETE FROM "+entity+ " WHERE "+pk+" =?";
	        }
//	        System.out.println(query);
	        
	        return query;
	    }
	     
	  public static Method getSetters(Method[] method){
	      List<String> list = new ArrayList<String>();
	      Method m =null;
	      for(int i=0;i<method.length;i++){
	          if(method[i].getName().startsWith("setId")){
	              list.add(method[i].getName());
	              m = method[i];
	          }
	      }
	      return m;
	  }
	  public static void insertObject(String entity,BaseObject obj) throws Exception {
		  	MongoDatabase database = DbHelper.getConnexionMongodb();
		  	MongoCollection<BaseObject> dbCollection =  database.getCollection(entity,BaseObject.class);
		  	dbCollection.insertOne(obj);
	  }
}
