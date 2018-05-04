/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.NomTable;
import connect.Connexion;
import dao.hibernate.OutilDAO;
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
import interfaces.GeneriqueService;
import java.util.List;

/**
 *
 * @author Angelo-KabyLake
 */
public class GeneriqueDAO implements GeneriqueService{
    private Fonction fonction = new Fonction();
    private OutilDAO outilDAO = new OutilDAO();
    @Override
    public void findById(BaseModel bm) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "";
        List<BaseModel> ret = null;
        Vector vect = new Vector();
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        //String set = "set";
        try{
            sql+= fonction.getQueryString(bm, "findById", "");
            
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            Boolean exist = rs.next();
             
            while(exist){
                //BaseModel temp = bm;
                Method method = null;
                Method[] getM = getMethodSet(c.getMethods(), attributs);
                Object[] premier = {rs.getString(1)};
                
                method = bm.getClass().getMethod("setId", String.class);  //setNom(String
                method.invoke( bm, premier);
                int compteur = 2;
                for(Field f: attributs){
                    Class type = f.getType();
                    Object[] vao = {rs.getString(compteur)};
                    /*set = "set";
                    set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);*/
                    method = outilDAO.getSetter(bm, f); //setNom(String
                    method.invoke( bm, vao);
                    compteur++;
                }
                vect.add(bm);
                exist = rs.next(); 
            }
            ret = vect;
            bm = ret.get(0);
        }catch(Exception e){
               // System.out.println(e.getMessage());
            throw e;
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
        
    } 
    public Method[] getMethodSet(Method[] getM, Field[] taille){
        Method[] ret = new Method[taille.length+1];//+setId
        int i = 0;
        for(Method m : getM){
            if(m.getName().substring(0, 3).equals("set")){
                ret[i] = m;
                i++;
            }
        }
        return ret;
    }
    @Override
    public List<BaseModel> findAll(Connection con, BaseModel bm) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = " ";
        List<BaseModel> ret = null;
        Vector vect = new Vector();
        
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String set = "set";
        try{
            sql += fonction.getQueryString(bm, "findAll", "");
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
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
                    /*set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);*/
                    set += outilDAO.capitalize(f);
                    method = temp.getClass().getMethod(set, type);  //setNom(String
                    //method =  outilDAO.getSetter(bm, f);
                    method.invoke( temp, vao);
                    compteur++;
                }
                vect.add(temp);
                exist = rs.next(); 
            }
            ret = vect;
        }catch(Exception e){
           // System.out.println(e.getMessage());
           throw e;
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
    public List<BaseModel> find(Connection con, BaseModel bm, String condition) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "";
        List<BaseModel> ret = null;
        Vector<BaseModel> vect = new Vector<BaseModel>();
        
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String set = "set";
        try{
            sql += fonction.getQueryString(bm, "find", condition);
        
            stmt = con.prepareStatement(sql);;
            rs = stmt.executeQuery();
            Boolean exist = rs.next();
            System.out.println(sql);
            while(exist){
                
                BaseModel temp = (BaseModel)c.newInstance();
                Method method = null;
                Method[] getM = getMethodSet(c.getMethods(), attributs);
                Object[] premier = {rs.getObject(1)};
                
                method = temp.getClass().getMethod("setId", String.class);  //setNom(String
                method.invoke( temp, premier);
               int compteur = 2;
                for(Field f: attributs){
                    Class type = f.getType();
                    Object[] vao = {rs.getObject(compteur)};
                    set = "set";
                    /*set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);*/
                    set += outilDAO.capitalize(f);
                    //System.out.println("TAILLE = "+type);
                    method = temp.getClass().getMethod(set, type); //setNom(String
                    
                    //System.out.println("Object = "+rs.getObject(compteur));
                   
                    //method.invoke(temp ,rs.getObject(compteur, type));
                    
                    if(method.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("double")){
                        method.invoke(temp, rs.getDouble(compteur));
                    }
                    else if(method.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("string")){
                        method.invoke(temp, rs.getString(compteur));
                    }
                    else if(method.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("int")){
                        method.invoke(temp, rs.getInt(compteur));
                    }
                    else if(method.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("float")){
                        method.invoke(temp, rs.getFloat(compteur));
                    }
                    else{
                        method.invoke(temp, rs.getObject(compteur));
                    }
                    compteur++;
                }
                vect.add(temp);
                exist = rs.next(); 
            }
            ret = vect;
            /* System.out.println("Valeur = "+rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getString(4));
                exist = rs.next(); 
            }*/
        }catch(Exception e){
               // e.printStackTrace();
            throw e;
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
    public List<BaseModel> findBy(Connection con, BaseModel bm) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "";
        List<BaseModel> ret = null;
        Vector vect = new Vector();
        
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
       
        String set = "set";
        try{
            sql += fonction.getQueryString(bm, "findBy", "");
            //System.out.println("My sql = "+sql);
           
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            Boolean exist = rs.next();
             
            while(exist){
                
                BaseModel temp = (BaseModel)c.newInstance();
                Method method2 = null;
                Method[] getM = getMethodSet(c.getMethods(), attributs);
                Object[] premier = {rs.getString(1)};
                
                method2 = temp.getClass().getMethod("setId", String.class);  //setNom(String
                method2.invoke( temp, premier);
               int compteur = 2;
                for(Field f: attributs){
                    Class type = f.getType();
                    Object[] vao = {rs.getString(compteur)};
                    set = "set";
                    /*set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);*/
                    set += outilDAO.capitalize(f);
                    method2 = temp.getClass().getMethod(set, type);  //setNom(String
                  //  method2.invoke(temp, vao);
                    if(method2.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("double")){
                        method2.invoke(temp, rs.getDouble(compteur));
                    }
                    else if(method2.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("string")){
                        method2.invoke(temp, rs.getString(compteur));
                    }
                    else if(method2.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("int")){
                        method2.invoke(temp, rs.getInt(compteur));
                    }
                    else if(method2.getParameterTypes()[0].getSimpleName().equalsIgnoreCase("float")){
                        method2.invoke(temp, rs.getFloat(compteur));
                    }
                    else{
                        method2.invoke(temp, rs.getObject(compteur));
                    }
                    compteur++;
                }
                vect.add((BaseModel)temp);
                exist = rs.next(); 
            }
            ret = vect;
                    
        }catch(Exception e){
                //e.printStackTrace();
            throw e;
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
    public List<BaseModel> find(BaseModel bm, String condition) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        List<BaseModel> ret=null;
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
        
        PreparedStatement stmt = null;
        String sql = "";
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        String get = "get";
        String sequence = "seq_";
        String pret = "";
        
        Method method = null;
        Object[] paramater = {};
        try{
       
            sql+= fonction.getQueryString(bm, "insert", "");
           /* NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            pret = annot.predicat();
            stmt = con.prepareStatement(sql);
            stmt.setObject(1, fonction.getSeq( pret, sequence));*/
            stmt.setObject(1, outilDAO.getSequenceId(bm));
            int i = 2;
            for(Field f: attributs){
                get = "get";
               /* get += f.getName().substring(0, 1).toUpperCase();
                get += f.getName().substring(1);*/
                get += outilDAO.capitalize(f);
                method = c.getMethod(get);  //setNom(String
                //System.out.println("method = "+method.getName());
                stmt.setObject(i, method.invoke( bm, paramater).toString());
                i++;
            }
            int e = stmt.executeUpdate();
            System.out.println("Row insert : "+e);
        }catch(Exception e){
            //e.printStackTrace();
            throw e;
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
        List<BaseModel> ret=null;
        try{
            insert(con, bm);
        } catch(Exception e){
            //e.printStackTrace();
            throw e;
        }
        finally{
            if(con!=null){
                con.close();
            }
        }
    }
    @Override
    public void update(Connection con, BaseModel bm, String condition) throws Exception {
        
        PreparedStatement stmt = null;
        
        String sql = "";
        Class c = bm.getClass();
        
        Field[] attributs = c.getDeclaredFields();
       
        String get = "get";
        try{
            sql+= fonction.getQueryString(bm, "update", "");
            
            //System.out.println("SQL UPDATE = "+sql);
            Method method = null;
            Object[] paramater = {};
            stmt = con.prepareStatement(sql);
            method = c.getMethod("getId");
            stmt.setObject(1, method.invoke( bm, paramater).toString());
            int i = 2;
            for(Field f: attributs){
                get = "get";
                /*
                get += f.getName().substring(0, 1).toUpperCase();
                get += f.getName().substring(1);*/
                get += outilDAO.capitalize(f);
                method = c.getMethod(get);  //setNom(String
               // System.out.println("method = "+method.getName());
                stmt.setObject(i, method.invoke( bm, paramater).toString());
                i++;
            }
            method = c.getMethod("getId");
            //System.out.println("index = "+i);
            stmt.setObject(i, method.invoke( bm, paramater).toString());           
            int e = stmt.executeUpdate();
            System.out.println("Row modify : "+e);
        }catch(Exception e){
            //e.printStackTrace();
            throw e;
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
    public void update(BaseModel bm, String condition) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        try{
            update(con, bm, condition);
        } catch(Exception e){
            //e.printStackTrace();
            throw e;
        }
        finally{
            if(con!=null){
                con.close();
            }
        }

    }
    @Override
    public void delete(Connection con, BaseModel bm) throws Exception {
        
        PreparedStatement stmt = null;
        String sql = "";
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields();
        Object[] paramater = {};
        try{
            sql += fonction.getQueryString(bm, "delete", "");
            Method method = null;
            stmt = con.prepareStatement(sql);
            method = c.getMethod("getId");
            stmt.setObject(1, method.invoke( bm, paramater).toString());
            int ee = stmt.executeUpdate();
            System.out.println("Row deleted : "+ee);
        }catch(Exception e){
            //e.printStackTrace();
            throw e;
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
    public void delete(BaseModel bm) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        List<BaseModel> ret=null;
        try{
            delete(con, bm);
        } catch(Exception e){
            throw e;    
        // e.printStackTrace();
        }
        finally{
            if(con!=null){
                con.close();
            }
        }  
    }
    @Override
    public List<BaseModel> pagination(BaseModel bm, int page, int rows) throws Exception {
        Connexion conn = new Connexion();
        Connection con = conn.getConnexion();
        PreparedStatement stmt = null;
        Class c = bm.getClass();
        Field[] attributs = c.getDeclaredFields(); 
        String sql = "select * from ";
        int offset = (page-1)*rows;
        ResultSet rs = null;
        List<BaseModel> ret = null;
        Vector vect = new Vector();
        String set="set";
       try{
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            
            sql+=annot.value();
           
            sql+=" ORDER BY id OFFSET "+offset+" ROWS FETCH NEXT "+rows+" ROWS ONLY";
            //System.out.println("MY sql = "+sql);
           
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
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
                    /*set += f.getName().substring(0, 1).toUpperCase();
                    set += f.getName().substring(1);*/
                    set += outilDAO.capitalize(f);
                    method = temp.getClass().getMethod(set, type);  //setNom(String
                    method.invoke( temp, vao);
                    compteur++;
                } 
               vect.add(temp);
                exist = rs.next(); 
            }
            ret = vect;
            
       }catch(Exception e){
           //e.printStackTrace();
           throw e;
       }
      return ret;
    }
    @Override
    public List<BaseModel> findAll(BaseModel bm) throws Exception {
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        List<BaseModel> ret=null;
        try{
            ret = findAll(con, bm);
        } catch(Exception e){
            throw new Exception("FindAll echoué!!!");
        }
        finally{
            
            if(con!=null){
                con.close();
            };
        }
        return ret;
    }

    @Override
    public BaseModel findById2(BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
