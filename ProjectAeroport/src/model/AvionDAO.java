/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connect.Connexion;
import interfaceDAO.interfaceDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import outil.Fonction;

/**
 *
 * @author ITU
 */
public abstract class AvionDAO implements interfaceDAO{
    String pre = "AVI";
    
    public Avion[] find(Connection con, String table, String where) throws Exception{
		Avion[] tabMed;
		Vector listMed = new Vector();
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

		tabMed = new Avion[listMed.size()];
		listMed.copyInto(tabMed);
		
		}catch(Exception e){
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
		return tabMed;
		
    }
   
    
     public Avion[] find(String table,String where) throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        Avion[] ret = null;
      
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
    public void insert(Connection con, Avion avion)throws Exception{
      
        Fonction fon = new Fonction();
        PreparedStatement stmt = null;
        int ret = 0;
        try{

            stmt = con.prepareStatement("INSERT INTO Avion(id, idTypeAvion,idTypeVol, nom) VALUES(?,?,?,?)");

            stmt.setString(1, fon.getSeq(pre, "seq_Avion"));
            stmt.setInt(2, avion.getIdTypeAvion());
            stmt.setInt(3, avion.getIdTypeVol());
            stmt.setString(4, avion.getNom());
            stmt.executeUpdate();

        }
        catch(Exception e){
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
    public void insert(Avion avion)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
  
        try{

           this.insert(con, avion);

        }
        catch(Exception e){
           throw new Exception("Insertion echoué!!!");
        }
        finally{
           
           if(con!=null){
               con.close();
           }
        }
       
    } 
   
    public void update(Connection con, Avion nouveau, String condition)throws Exception{
        
        try(PreparedStatement stmt = con.prepareStatement("UPDATE Avion SET id=? , idTypeAvion=?, idTypeVol=? , nom=? where id=?")) {


            stmt.setString(1, nouveau.getId());
            stmt.setInt(2, nouveau.getIdTypeAvion());
            stmt.setInt(3, nouveau.getIdTypeVol());
            stmt.setString(4,nouveau.getNom());

            stmt.setString(5, condition);

            stmt.executeUpdate();

        }
        catch(Exception e){
            throw new Exception("Insertion echoué!!!");
        }
        finally{
            if(con!=null){
                con.close(); 
            }
        }
    }
    public void update(Avion nouveau, String condition)throws Exception{
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
           }
        }
    }
    
}
