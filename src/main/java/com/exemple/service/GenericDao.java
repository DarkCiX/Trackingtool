package com.exemple.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenericDao {
	public static void save(Object obj,String entity,Connection con) throws IllegalArgumentException, IllegalAccessException{
        try{
            String query = Function.CreateSQL(entity,obj,CRUD_type.INSERT);
            PreparedStatement pst = con.prepareStatement(query);
            Field[] field = obj.getClass().getDeclaredFields();
            ArrayList value = new ArrayList();
            for (Field field1 : field) {
                value.add(field1.get(obj));
            }
            for(int j=0;j<value.size();j++){
                pst.setObject(j+1,value.get(j));
            }
            System.out.println(pst);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
    public static List<Object> FindAll(String entity,Object obj,Connection con) throws InstantiationException, IllegalAccessException{
        List<Object> data = new ArrayList();
        try{
            Field [] field = obj.getClass().getDeclaredFields();
            String query = Function.CreateSQL(entity,obj,CRUD_type.SELECT);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               Object pt = obj.getClass().newInstance();
               for(int i=0;i<field.length;i++){
                   field[i].set(pt,rs.getObject(i+1));
               }
               data.add(pt);
            }
        }catch(SQLException ex){
            System.out.printf(ex.getMessage());
        }
        return data;
    }
    public void Delete(String entity,Object obj,Object id,Connection con){
        try{
            String query = Function.SQL(obj,entity,CRUD_type.DELETE);
            System.out.print(query);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setObject(1,id);
            pst.executeUpdate();
        }catch(Exception ex){
            System.out.printf(ex.getMessage());
        }
    }
    public static Object FindbyId(String entity,Object obj,Object criteria,Connection con) throws InstantiationException, IllegalAccessException{
        Object pt = obj.getClass().newInstance();
        try{
            Field [] field = obj.getClass().getDeclaredFields();
            String pk = Function.getPrimaryKey(entity,obj);
            String query = "SELECT * FROM "+entity+ " WHERE "+pk+"='"+criteria+"'";
            System.out.println(query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               Method []method = pt.getClass().getDeclaredMethods();
               for(int i=0;i<method.length;i++){
            	   if(Function.isSetter(method[i])){
                       for(int j=0;j<field.length;j++){
                          System.out.println(method[i].invoke(pt, rs.getObject(j+1))) ;
                          System.out.println("Method name : " +method[i].getName()) ;
//                           System.out.println(method[i].getName() + " " + rs.getObject(j+1));
                    	   
                       }
                   }
               }
            }
        }catch(Exception ex){
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        } 
        return pt;
    } 
    public Boolean update(String entity,Object obj,String id,Connection con) throws InstantiationException, IllegalAccessException{
        Object pt = obj.getClass().newInstance();
        try{
            Field [] field = obj.getClass().getDeclaredFields();
            String pk = Function.getPrimaryKey(entity,obj);
            String query = Function.SQL(obj,entity,CRUD_type.UPDATE);
            System.out.println("Query "+query);
            PreparedStatement pst = con.prepareStatement(query);
            ArrayList value = new ArrayList();
            for (int o=0;o<field.length-1;o++) {
                value.add(field[o+1].get(obj));
                 System.out.println("Field "+field[o+1].get(obj));
            }
            for(int j=0;j<value.size();j++){
                 pst.setString(value.size()+1, id);
                 pst.setObject(j+1,value.get(j));
            }
            pst.executeUpdate();
            pst.close();
            con.close();
        }catch(Exception ex){
               System.out.print(ex.getMessage());
        }
        return true;
    }

}
