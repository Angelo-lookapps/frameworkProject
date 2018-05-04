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
public abstract class BilletDAO implements interfaceDAO{
    String pre = "BLT";
    
    
    public Billet[] find(Connection con, String table, String where) throws Exception{
		Billet[] tabMed;
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

		tabMed = new Billet[listMed.size()];
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
   
    
    
     public Billet[] find(String table,String where) throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
        Billet[] ret = null;
      
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
    
    public void insert(Connection con, Billet billet)throws Exception{
      
        Fonction fon = new Fonction();
        PreparedStatement stmt = null;
        int ret = 0;
        try{

            stmt = con.prepareStatement("INSERT INTO Billet(id, idDestination,daty, prixUnitaire) VALUES(?,?,?,?)");

            stmt.setString(1, fon.getSeq(pre, "seq_Billet"));
            stmt.setString(2, billet.getIdDestination());
            stmt.setString(3, billet.getDaty());
            stmt.setFloat(4, billet.getPrixUnitaire());
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
    
    public void insert(Billet billet)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
  
        try{

           this.insert(con, billet);

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
   
    
    public void update(Connection con, Billet nouveau, String condition)throws Exception{
        
        try(PreparedStatement stmt = con.prepareStatement("UPDATE Billet SET id=? , idDestination=?, daty=? , prixUnitaire=? where id=?")) {


            stmt.setString(1, nouveau.getId());
            stmt.setString(2, nouveau.getIdDestination());
            stmt.setString(3, nouveau.getDaty());
            stmt.setFloat(4, nouveau.getPrixUnitaire());
            stmt.executeUpdate();

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
    
    public void update(Billet nouveau, String condition)throws Exception{
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
    
    
    public void delete(Connection con,String id)throws Exception{
           try(PreparedStatement stmt = con.prepareStatement("DELETE FROM Billet where id=? ")) {
            stmt.setString(1,id);
            stmt.executeUpdate();

            }
        catch(Exception e){
            throw new Exception("Suppression echoué!!!");
        }
        finally{
            if(con!=null){
                con.close(); 
            }
        }
    }
    
    
    public void delete(String id)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
    
        try{

           this.delete(con, id);

        }
        catch(Exception e){
           throw new Exception("Suppression echoué!!!");
        }
        finally{
           
           if(con!=null){
               con.close();
           }
        }
    }
}
