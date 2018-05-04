/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectaeroport;

import connect.Connexion;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaseModel;
import model.Billet;
import model.BilletDAO;
import model.Client;
import model.ClientDAO;
import model.Destination;
import model.GeneriqueDAO;
import model.Pagination;
import model.TypeAvion;
import model.TypeClasse;
import outil.Fonction;

/**
 *
 * @author Angelo-KabyLake
 */
public class ProjectAeroport {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
       

        GeneriqueDAO dao = new GeneriqueDAO();
        BilletDAO billetDAO = new BilletDAO();
                
        try {
            Connexion co =  new Connexion();
            Connection con = co.getConnexion();
            Billet test2 = new Billet("", "", "", 0);
            List<BaseModel> lists = dao.findBy(con, test2);
        // GeneriqueDAO    
        System.out.println("\nGeneriqueDAO");
            for( BaseModel list : lists ){
                Billet temp = (Billet) list;
                System.out.println(temp.getId()+" | "+temp.getIdDestination()+" | "+temp.getDaty()+" | "+temp.getPrixUnitaire());
            }
       
        // JDBC DirectDAO  
        System.out.println("\nJDBC DirectDAO ");
            List<Billet> listDirect = billetDAO.find("Billet", "");
            for( Billet list : listDirect ){
                //Billet temp = (Billet) list;
                System.out.println(list.getId()+" | "+list.getIdDestination()+" | "+list.getDaty()+" | "+list.getPrixUnitaire());
             }
        
        // Pagination     
        System.out.println("\nPagination ");
            Pagination pg = new Pagination(new Client(), 2);
            
            List<BaseModel> gg =  pg.getPageActuel();
           
            for( BaseModel list : gg ){
                Client temp = (Client) list;
                System.out.println(temp.getId()+" | "+temp.getNom()+" | "+temp.getPrenom());
            }
        } catch (Exception ex) {
           //System.out.println(ex.getMessage());
           ex.printStackTrace();
        }
        
    }
    
}
