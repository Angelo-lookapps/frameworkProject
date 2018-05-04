/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.hibernate;

import java.util.List;
import model.BaseModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Angelo-KabyLake
 */
public class TestHibernate {
    public static void main(String[] args)  {
        HibernateDAO dao = new HibernateDAO(new Client("", "", ""));
        
        try{ 
            List<BaseModel> lists = dao.findAll(new Client("", "", ""));
            for(BaseModel temp : lists){
                Client list = (Client)temp;
                System.out.println(list.getId()+" | "+list.getNom()+" | "+list.getPrenom());
            }
            
            Client clt = new Client("test0001","","");
            dao.delete(clt);
            //dao.update(clt, "test0001");
            //Client temp = (Client) dao.findById2(clt);
            //System.out.println(clt.getId()+" | "+clt.getNom()+" | "+clt.getPrenom());
            
            System.out.println("FIN \n");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
