/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.hibernate;

import model.BaseModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Angelo-KabyLake
 */
public class HbUtils {
    static SessionFactory session;

    public HbUtils(SessionFactory session) {
        this.session = session;
    }
    public SessionFactory initSession(BaseModel bm){
        SessionFactory ret = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(Client.class)
                            .buildSessionFactory();
        return ret;
    }
 
    
}
