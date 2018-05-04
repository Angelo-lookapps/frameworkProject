/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connect.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import outil.Fonction;

/**
 *
 * @author Angelo-KabyLake
 */
public class ClientDAO {
    String pre = "CLT";
    
    public List find(Connection con, String table, String where) throws Exception{
            List<Client> listMed = new Vector();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select * from "+table;

            try{
            if(!where.equals("")){
                stmt = con.prepareStatement("select * from "+table+" where 1<2 AND "+where);
            }
            stmt = con.prepareStatement(sql);

            rs = stmt.executeQuery();

            Boolean exist = rs.next();
            while(exist){
                    Client temporaire = new Client();
                    temporaire.setId(rs.getString(1));
                    temporaire.setNom(rs.getString(2));
                    temporaire.setPrenom(rs.getString(3));
                    listMed.add(temporaire);
                    exist = rs.next();
            } 
           // System.out.println("ICI Taille = "+listMed.get(0).getPrixUnitaire());
            
            }catch(Exception e){
                //throw e;
                e.printStackTrace();
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
            return listMed;
		
    }
   
    
     public List find(String table,String where) throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        List ret = null;
      
        try{
           ret = this.find(con, table, where);
        }catch(Exception e){
            throw e;
        }
        
        return ret; 
    }
    

   /* public void insert(Connection con, Client a)throws Exception{
       Fonction fon = new Fonction();
       PreparedStatement stmt = null;
       try{

           stmt = con.prepareStatement("INSERT INTO Client(id, nom, prenom) VALUES(?,?,?)");

           stmt.setString(1, fon.getSeq(pre, "seq_Client"));
           stmt.setString(2, a.getNom());
           stmt.setString(3, a.getPrenom());

           stmt.executeUpdate();

       }
       catch(Exception e){
           throw new Exception("Insertion echoué!!!");
       }
       finally{
           if(stmt!=null){
               stmt.close();
           }
           if(con!=null){
               con.close();
           };
       }
    } */
    public void insert(Connection con, Client client)throws Exception{
      
        Fonction fon = new Fonction();
        PreparedStatement stmt = null;
        int ret = 0;
        try{

            stmt = con.prepareStatement("INSERT INTO Client(id, nom, prenom) VALUES(?,?,?)");

            stmt.setString(1, fon.getSeq(pre, "seq_Client"));
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.executeUpdate();

        }
        catch(Exception e){
            //throw new Exception("Insertion echoué!!!");
            e.printStackTrace();
        }
        finally{
            if(stmt!=null){
                stmt.close();
            }
            if(con!=null){
                con.close();
            };
        }
    }
    public void insert(Client client)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
  
        try{

           this.insert(con, client);

        }
        catch(Exception e){
           throw new Exception("Insertion echoué!!!");
        }
        finally{
           
           if(con!=null){
               con.close();
           };
        }
       
    } 
   
    public void update(Connection con, Client nouveau, String condition)throws Exception{
        
        PreparedStatement stmt = null;
        try{

            stmt = con.prepareStatement("UPDATE Client SET id=? , nom=?, prenom=? where id=?");

            stmt.setString(1, nouveau.getId());
            stmt.setString(2, nouveau.getNom());
            stmt.setString(3, nouveau.getPrenom());

            stmt.setString(4, condition);

            stmt.executeUpdate();

        }
        catch(Exception e){
            throw new Exception("Insertion echoué!!!");
        }
        finally{
            if(stmt!=null){
                stmt.close();
            }
            if(con!=null){
                con.close();
            };
        }
    }
    public void update(Client nouveau, String condition)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
    
        try{

           this.update(con, nouveau, condition);

        }
        catch(Exception e){
           throw new Exception("Insertion echoué!!!");
        }
        finally{
           
           if(con!=null){
               con.close();
           };
        }
    }
    
}
