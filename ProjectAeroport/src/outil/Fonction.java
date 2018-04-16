/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outil;

import connect.Connexion;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

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
}
