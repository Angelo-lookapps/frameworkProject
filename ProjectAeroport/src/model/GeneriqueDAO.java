/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.NomTable;
import connect.Connexion;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import outil.Fonction;
import outil.GeneriqueService;

/**
 *
 * @author Angelo-KabyLake
 */
public class GeneriqueDAO implements GeneriqueService{

    /*  
    Class c = o.getClass();
        Field temp;
        Field[] tous = c.getDeclaredFields();
        
        for(Field f: tous){
            if(f.getName().compareTo(att)==0){
                temp = f;break;
            }
        }
        
        Method myFonction;
        
        String get = "get";
        get += att.substring(0, 1).toUpperCase();
        get += att.substring(1);
        Object[] vao = {};
        try{
            myFonction = c.getDeclaredMethod(get, null);  //getTest
            ret = (int)myFonction.invoke(o, vao);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return ret;
    */
    public Method[] getMethodSet(Method[] getM, Field[] taille){
        Method[] ret = new Method[taille.length+1];//+setId
        int i = 0;
        for(Method m : getM){
            if(m.getName().substring(0, 3).equals("set")){
                ret[i] = m;
//System.out.println("Mes Methods = "+m.getName());
                i++;
            }
        }
        return ret;
    }
    @Override
    public BaseModel[] findAll(Connection con, BaseModel bm) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from ";
        BaseModel[] ret = null;
        Vector vect = new Vector();
        
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String set = "set";
        try{
            stmt = con.createStatement();
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
        //  System.out.println("sql = "+sql);
        //  sql += c.getSimpleName();
            rs = stmt.executeQuery(sql);
            Boolean exist = rs.next();
             
            while(exist){
                
                BaseModel temp = (BaseModel)c.newInstance();
                Method method = null;
                Method[] getM = getMethodSet(c.getMethods(), attributs);
                Object[] premier = {rs.getString(1)};
                
                method = temp.getClass().getMethod("setId", String.class);  //setNom(String
                method.invoke( temp, premier);
               int compteur = 2;
                for(Field f: attributs){
                    
                    
                    Class type = f.getType();
                    Object[] vao = {rs.getString(compteur)};
                    set = "set";
                    set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);
                    method = temp.getClass().getMethod(set, type);  //setNom(String
                  //  System.out.println("method = "+method.getName());
                   // System.out.println("value = "+vao[0]);
                    method.invoke( temp, vao);
                    compteur++;
                }
                
               // System.out.println("temp = "+temp);
              
                vect.add(temp);
                exist = rs.next(); 
            }
            ret = new Client[vect.size()];
            vect.copyInto(ret);
                    
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
        finally{
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
            if(con!=null){
                con.close();
            }
        }
        
        return ret; 
    }

    @Override
    public BaseModel[] find(Connection con, BaseModel bm, String condition) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from ";
        BaseModel[] ret = null;
        Vector vect = new Vector();
        
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String set = "set";
        try{
            stmt = con.createStatement();
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sql+=annot.value();
            if(condition.equals("") || condition.equals("null") || condition.equals("*")){
                condition = "3<10";
            }
            sql+=" where 1<9 AND "+condition;
            
          System.out.println("sql = "+sql);
        ///  sql += c.getSimpleName();
            rs = stmt.executeQuery(sql);
            Boolean exist = rs.next();
             
            while(exist){
                
                BaseModel temp = (BaseModel)c.newInstance();
                Method method = null;
                Method[] getM = getMethodSet(c.getMethods(), attributs);
                Object[] premier = {rs.getString(1)};
                
                method = temp.getClass().getMethod("setId", String.class);  //setNom(String
                method.invoke( temp, premier);
               int compteur = 2;
                for(Field f: attributs){
                    
                    
                    Class type = f.getType();
                    Object[] vao = {rs.getString(compteur)};
                    set = "set";
                    set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);
                    method = temp.getClass().getMethod(set, type);  //setNom(String
                 
                    //System.out.println("method = "+method.getName());
                   // System.out.println("value = "+vao[0]);
                    method.invoke( temp, vao);
                    compteur++;
                }
                
               // System.out.println("temp = "+temp);
              
                vect.add(temp);
                exist = rs.next(); 
            }
            ret = new Client[vect.size()];
            vect.copyInto(ret);
                    
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
        finally{
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
            if(con!=null){
                con.close();
            }
        }
        
        return ret; 
    }

    @Override
    public BaseModel[] find(BaseModel bm, String condition) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        BaseModel[] ret=null;
        try{
            ret = find(con, bm, condition);
        } catch(Exception e){
            throw new Exception("Insertion echoué!!!");
        }
        finally{
            
            if(con!=null){
                con.close();
            };
        }
        return ret;
    }

    @Override
    public void insert(Connection con, BaseModel bm) throws Exception {
        Fonction fon = new Fonction();
        PreparedStatement stmt = null;
        
        String sql = "INSERT INTO Client(id,";
        String insert = " VALUES(";
        Class c = bm.getClass();
        
        Field[] attributs = c.getDeclaredFields();
       
        String get = "get";
        String sequence = "seq_";
        String pret = "";
        try{
       
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sequence+=annot.value();
            pret = annot.predicat();
            
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
            Method method = null;
            Object[] paramater = {};
            stmt = con.prepareStatement(sql);
            stmt.setObject(1, fon.getSeq( pret, sequence));
            int i = 2;
            for(Field f: attributs){
                
                get = "get";
                get += f.getName().substring(0, 1).toUpperCase();
                get += f.getName().substring(1);
               // System.out.println("get = "+get);
                
                method = c.getMethod(get, null);  //setNom(String
                System.out.println("method = "+method.getName());
               // System.out.println("method.invoke( c, paramater).toString() = "+method.invoke( c, paramater));
                stmt.setObject(i, method.invoke( bm, paramater).toString());
                //System.out.println("getName = "+method.invoke( bm, paramater));
                i++;
            }
            stmt.executeUpdate();
            
                    
        }catch(Exception e){
            //throw new Exception("Insertion echoué!!!");
            e.printStackTrace();
        }
        finally{
            if(stmt!=null){
                stmt.close();
            }
            if(con!=null){
                con.close();
            }
        }
           
    }

    @Override
    public void insert(BaseModel bm) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        BaseModel[] ret=null;
        try{
            insert(con, bm);
        } catch(Exception e){
            //throw new Exception("Insertion echoué!!!");
            e.printStackTrace();
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
    }

    @Override
    public void update(Connection con, BaseModel bm, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(BaseModel bm, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection con, BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
