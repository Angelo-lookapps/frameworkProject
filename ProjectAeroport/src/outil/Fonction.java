/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outil;

import annotations.NomTable;
import connect.Connexion;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import model.BaseModel;
import model.Client;

/**
 *
 * @author Angelo-KabyLake
 */
public class Fonction {
    
    public String concat(String pre, String valeur)
        {
            int val = Integer.parseInt(valeur);
            String retour = null;
            if (val < 10)
            {
                retour = pre + "000" + val;
            }
            else if (val >= 10 && val < 100)
            {
                retour = pre + "00" + val;
            }
            else if (val >= 100 && val < 1000)
            {
                retour = pre + "0" + val;
            }
            return retour;
        } 
    public String getSeq(Connection con, String pre, String seq)throws Exception
        {
            String sql = null;
            int retour = 0;
            String retur1 = null;
            try
            {
            Statement stmt = (Statement) con.createStatement();
            
            sql = "select next value for " + seq;
            
            ResultSet rs = stmt.executeQuery(sql);
            Boolean exist = rs.next();
                while (exist)
                {
                    retour = rs.getInt(1);
                    exist = rs.next();
                }
                
                retur1 = concat(pre ,""+retour);    
            
            }catch(Exception e){
                throw e;
            }
            finally{
                if(con!=null){
                    con.close();
                }
            }
            return retur1;
        }
    public String getSeq(String pre, String seq)throws Exception
        {
            String retour = null;
       
            Connexion temp = new Connexion();
           
            try
            {
                Connection con = temp.getConnexion();
                retour = getSeq(con, pre, seq);//Console.WriteLine("NY Tokony ho azo === " +retour);
            }
            catch(Exception e){
                throw e;
            }
            return retour;
        }
    public String getQueryString(BaseModel bm, String typeQuery, String condition)throws Exception{
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String get = "get";
        //String set = "set";
        Method method = null;
        String sql = "";
        Object[] paramater = {};
        if(typeQuery.equalsIgnoreCase("findBy") && condition.equals("")){
            
            String variable = "";
            String id = "";
            sql = "select * from ";
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
            
            sql+=" where 1<9 AND ";
            method = c.getMethod("getId");
            id = method.invoke( bm, paramater).toString();
            if(!id.equals("")){
                sql+=" id = '"+ id +"' AND ";
            }
            Boolean update = false; 
            for(int i=0;i<attributs.length;i++){
                get = "get";
                get += attributs[i].getName().substring(0, 1).toUpperCase();
                get += attributs[i].getName().substring(1);
                method = c.getMethod(get);  //setNom(Strin
                variable =  method.invoke( bm ).toString();
                if(!variable.equals("")){
                    if(i<(attributs.length-1)){
                        sql+= attributs[i].getName()+" LIKE '%"+variable+"%' AND ";
                        update = true;
                    }
                    else if(i==(attributs.length-1)){
                        sql+= attributs[i].getName()+" LIKE '%"+variable+"%' AND ";
                        update = false;
                    }
                }
            }
            if(update==false){
                sql+="4<9";
            }
        }
        else if(typeQuery.equalsIgnoreCase("findById") && condition.equals("") ){
            sql = "select * from ";
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
            sql+=" where id = '" + bm.getId() + "'";
        }
        else if(typeQuery.equalsIgnoreCase("findAll") && condition.equals("")){
            sql = "select * from ";
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
            
        }
        else if(typeQuery.equalsIgnoreCase("find") ) {
            sql = "select * from ";
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
            if(condition.equals("") || condition.equals("null") || condition.equals("*")){
                condition = "3<10";
            }
            sql+=" where 1<9 AND "+condition;
        }
        else if(typeQuery.equalsIgnoreCase("insert") && condition.equals("")){
            //String sequence = "seq_";
            String insert = " VALUES(";
            //String pret = "";
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            //sequence+=annot.value();
            sql +="INSERT INTO "+annot.value()+" (id,";
            // pret = annot.predicat();
            
            for(int i=0;i<attributs.length;i++){
                if(attributs.length-1==i){
                    insert+="?)";
                    sql+= attributs[i].getName()+")";
                }
                else{
                    sql+= attributs[i].getName()+",";
                    insert+="?,?,";
                }
            }
            sql+=insert;
        }
        else if(typeQuery.equalsIgnoreCase("update") && condition.equals("")){
            //String sequence = "seq_";
            //String pret = ""; 
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            //sequence+=annot.value();
            sql+= "UPDATE " + annot.value()+" SET id=? ,";
           // pret = annot.predicat();
            
            for(int i=0;i<attributs.length;i++){
                if(attributs.length-1==i){
                    
                    sql+= attributs[i].getName()+"=? ";
                }
                else{
                    sql+= attributs[i].getName()+"=? ,";
                   
                }
            }
            sql+= "where id=? ";
        }
        else if(typeQuery.equalsIgnoreCase("delete") && condition.equals("")){
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+= "DELETE from " + annot.value()+" where id=? ";
        }
        else{
            throw new Exception("Erreur : QueryString introuvable!!!");
        }
        return sql;
    }
    public BaseModel[] excute(BaseModel bm, ResultSet rs) throws Exception{
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields(); 
        BaseModel[] ret = null;
        Vector vect = new Vector();
        String set="";
    try{
        Boolean exist = rs.next();

        while(exist){

            BaseModel temp = (BaseModel)c.newInstance();
            Method method2 = null;
            Object[] premier = {rs.getString(1)};
            method2 = temp.getClass().getMethod("setId", String.class);  //setNom(String
            method2.invoke( temp, premier);
           int compteur = 2;
            for(Field f: attributs){
                Class type = f.getType();
                Object[] vao = {rs.getString(compteur)};
                set = "set";
                set += f.getName().substring(0, 1).toUpperCase();
                set += f.getName().substring(1);
                method2 = temp.getClass().getMethod(set, type);  //setNom(String
                method2.invoke( temp, vao);
                compteur++;
            }
            vect.add(temp);
            exist = rs.next(); 
        }
        ret = new Client[vect.size()];
        vect.copyInto(ret);
    }catch(Exception e){
        throw new Exception("Erreur : Excute query !!!");
    }finally{
        if(rs!=null){
            rs.close();
        }
    }
        return ret;
    }
}
