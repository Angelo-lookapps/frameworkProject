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
        
        try {
            Connexion co =  new Connexion();
            Connection con = co.getConnexion();
            
            Billet test2 = new Billet("", "", "");
            
            List<BaseModel> lists = dao.find(con, test2, "2<5");
            //System.out.println("Taille ==== "+lists.length);
            for( BaseModel list : lists ){
                Billet temp = (Billet) list;
                System.out.println(temp.getId()+" | "+temp.getIdDestination()+" | "+temp.getPrixUnitaire()+" | "+temp.getDaty());
            }
           /* System.out.println("HELLO!!!");
            Client test = new Client("CLT0033","","");
            dao.findById(test);
            
            System.out.println("gg = "+test.getId()+" | "+test.getNom()+" | "+test.getPrenom());
            Client insert = new Client("CLT0034", "ANGE","WELL 222");*/
            //ClientDAO ddd = new ClientDAO();
           // Client[] gg = (Client[])dao.findBy(con, test);
            
            //dao.delete(insert);
/*Fonction font = new Fonction();
String query = font.getQueryString(insert, "findall","");
System.out.println("queryString = "+query);
System.out.println("PAGE 1 ");*/
         /*   Client[] list2 = (Client[]) dao.pagination(test, 1, 3);
            for(int i=0;i<list2.length;i++){
               System.out.println(list2[i].getId()+" | "+list2[i].getNom()+" | "+list2[i].getPrenom());
            }
            System.out.println("PAGE 2 ");
            Client[] list3 = (Client[]) dao.pagination(test, 2, 3);
            for(int i=0;i<list3.length;i++){
               System.out.println(list3[i].getId()+" | "+list3[i].getNom()+" | "+list3[i].getPrenom());
            }*/
           
            Pagination pg = new Pagination(new Client(), 2);
            
            List<BaseModel> gg =  pg.getPageActuel();
           
            for( BaseModel list : gg ){
                Client temp = (Client) list;
                System.out.println(temp.getId()+" | "+temp.getNom()+" | "+temp.getPrenom());
            }
            
            /*pg.getPage(2);
            gg =  pg.getPageActuel();
            for( BaseModel list : gg ){
                Client temp = (Client) list;
                System.out.println(temp.getId()+" | "+temp.getNom()+" | "+temp.getPrenom());
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
