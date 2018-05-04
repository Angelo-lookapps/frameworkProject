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
import java.util.List;
import java.util.Vector;
import outil.Fonction;

/**
 *
 * @author Ryan
 */
public class ReservationDAO implements interfaceDAO{
    String pre = "RSV";
    
    @Override
    public List find(Connection con, String table, String where) throws Exception{
		Reservation[] tabMed;
		List listMed = new Vector();
                Statement stmt = null;
                ResultSet rs = null;
                String sql = "select * from "+table;
                
		try{
                if(!where.equals("")){
                    sql = "select * from "+table+" where 1<2 AND "+where;
                }
                stmt = con.createStatement();
                
		rs = stmt.executeQuery(sql);
                
		Boolean exist = rs.next();
                while(exist){
                        Reservation temporaire = new Reservation();
                        temporaire.setId(rs.getString(1));
                        temporaire.setIdClient(rs.getString(2));
                        temporaire.setIdBillet(rs.getString(3));
                        temporaire.setIdTypeClasse(rs.getString(4));
                        temporaire.setDateReservation(rs.getString(5));
                        listMed.add(temporaire);
                        exist = rs.next();
                } 
		
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
		return listMed;
		
    }
   
    
    @Override
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
    
    public void insert(Connection con, Reservation reservation)throws Exception{
      
        Fonction fon = new Fonction();
        PreparedStatement stmt = null;
        int ret = 0;
        try{

            stmt = con.prepareStatement("INSERT INTO Reservation(id, idCLient, idBillet, idTypeClasse, dateReservation) VALUES(?,?,?)");

            stmt.setString(1, fon.getSeq(pre, "seq_Reservation"));
            stmt.setString(2, reservation.getIdClient());
            stmt.setString(3, reservation.getIdBillet());
            stmt.setString(4, reservation.getIdTypeClasse());
            stmt.setString(5, reservation.getDateReservation());
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
    public void insert(Reservation reservation)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
  
        try{

           this.insert(con, reservation);

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
   
    public void update(Connection con, Reservation nouveau, String condition)throws Exception{
        
        PreparedStatement stmt = null;
        try{

            stmt = con.prepareStatement("UPDATE Reservation SET id=? , idClient=?, idBillet=?, idTypeClasse=?, dateReservation=? where id=?");

            stmt.setString(1, nouveau.getId());
            stmt.setString(2, nouveau.getIdClient());
            stmt.setString(3, nouveau.getIdBillet());
            stmt.setString(4, nouveau.getIdTypeClasse());
            stmt.setString(5, nouveau.getDateReservation());

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
    public void update(Reservation nouveau, String condition)throws Exception{
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
    
    public void delete(Connection con, String id)throws Exception{
        
        PreparedStatement stmt = null;
        try{

            stmt = con.prepareStatement("DELETE FROM Reservation WHERE id=?");

            stmt.setString(1, id);

            stmt.executeUpdate();

        }
        catch(Exception e){
            throw new Exception("Supression echoué!!!");
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
    public void delete(String id)throws Exception{
        Connexion connex = new Connexion();
        Connection con = connex.getConnexion();
    
        try{

           this.delete(con, id);

        }
        catch(Exception e){
           throw new Exception("Supression echoué!!!");
        }
        finally{
           
           if(con!=null){
               con.close();
           };
        }
    }

    @Override
    public void insert(Connection con, BaseModel baseModel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(BaseModel baseModel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Connection con, BaseModel nouveau, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(BaseModel nouveau, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
