/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.hibernate;

import annotations.NomTable;
import interfaces.GeneriqueService;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.Table;
import model.BaseModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import outil.Fonction;

/**
 *
 * @author Angelo-KabyLake
 */
public class HibernateDAO implements GeneriqueService{
    SessionFactory factoryGenerale = null;
    Fonction fonction = new Fonction();
    OutilDAO outilDAO = new OutilDAO();
    
    public HibernateDAO(){}
    public HibernateDAO(BaseModel bm){
        Class c = bm.getClass();
        this.factoryGenerale = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(c)
                            .buildSessionFactory();
    }
    
    public List<BaseModel> findAll(BaseModel bm){
        Class c = bm.getClass();
        List<BaseModel> lists = null;
        if(factoryGenerale==null){
        this.factoryGenerale = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(c)
                        .buildSessionFactory();
        }
        SessionFactory factory = this.factoryGenerale;
        //create session
        Session session = factory.getCurrentSession();
        try{ 
            //create a client object
           
            //start a transaction
            session.beginTransaction();
            Table annot = (Table)c.getAnnotation(Table.class);
            lists = session.createQuery( "from "+annot.name()+" ").list();
            //commit transaction
            session.getTransaction().commit();
            
            System.out.println("Done!!!");
        }catch(Exception e ){
            throw e;
        }finally{
            factory.close();
        }
        return lists;
    }

    @Override
    public List<BaseModel> findAll(Connection con, BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BaseModel> findBy(Connection con, BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BaseModel> find(BaseModel bm, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseModel findById2(BaseModel bm) throws Exception {
        Class c = bm.getClass();
        List<BaseModel> lists = null;
        BaseModel basemodel = null;
         controllSessionFactory(c);
       
        SessionFactory factory = this.factoryGenerale;
        //create session
        Session session = factory.getCurrentSession();
        try{ 
            //create a client object
           
            //start a transaction
            session.beginTransaction();
            
            //lists = session.createQuery( "from "+annot.name()+" ").list();
            basemodel = session.find(c , bm.getId());
            bm = basemodel;
            //commit transaction
            session.getTransaction().commit();
            
            System.out.println("Done!!!");
        }catch(Exception e ){
            throw e;
        }finally{
            factory.close();
        }
        return basemodel;
        //return basemodel;
    }

    @Override
    public List<BaseModel> pagination(BaseModel bm, int page, int rows) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BaseModel> find(Connection con, BaseModel bm, String condition) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection con, BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void controllSessionFactory(Class c){
        try{
            if(factoryGenerale == null || factoryGenerale.isClosed()){
                this.factoryGenerale = new Configuration()
                                        .configure("hibernate.cfg.xml")
                                        .addAnnotatedClass(c)
                                        .buildSessionFactory();
            }
        }catch(Exception e){
            throw e;
        }
    }
    public String ifIdIsValide(BaseModel bm) throws Exception{
        String idBm = bm.getId();
        try{
            
            if(bm.getId().equals("")){
                idBm = outilDAO.getSequenceId(bm);
            }
            
        }catch(Exception e){
            throw e;
        }
        return idBm;
    }
    @Override
    public void insert(BaseModel bm) throws Exception {
        Class c = bm.getClass();
        controllSessionFactory(c);
       
        SessionFactory factory = this.factoryGenerale;
        //create session
        
        Session session = factory.getCurrentSession();
        try{ 
            //start a transaction
            String test = "";
            test = ifIdIsValide(bm);
            //System.out.println("ID = "+test);
            bm.setId(test);
            
            session.beginTransaction();
            //save a object
            session.save(bm);
            //commit transaction
            session.getTransaction().commit();
            
            System.out.println("Done!!!");
        }catch(Exception e ){
            throw e;
        }finally{
            factory.close();
        }
    }

    @Override
    public void update(Connection con, BaseModel bm, String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Object doGetter(BaseModel cible, Field f) throws Exception{
        Object ret;
        Method getter = null;
        try {
            //Object[] vao = {rs.getString(compteur)};
            getter = outilDAO.getGetter(cible, f);
            ret = getter.invoke(cible.getClass(), null);

        }catch (Exception ex) {
            throw ex;
        }
        return ret;
    }
    public BaseModel doUpdate(BaseModel update, BaseModel cible) throws Exception{
        Field[] fields = update.getClass().getDeclaredFields();
            String test = "";
            test = ifIdIsValide(cible);
            cible.setId(test);
                
        try{
                for(Field f : fields){
                //get l'objet attribut de update
                Object updateObject = outilDAO.getGetter(cible, f).invoke(update, null);
                //On set l'object vers cible
                outilDAO.getSetter(cible, f).invoke(cible, updateObject);
                }
        }catch(Exception e){
            throw e;
        }
        return cible;
    }
    @Override
    public void update(BaseModel bm, String id) throws Exception {
        Class c = bm.getClass();
        controllSessionFactory(c);
        SessionFactory factory = this.factoryGenerale;
        //create session
        Session session = factory.getCurrentSession();
        try{ 
            //start a transaction
            BaseModel cible = this.findById2(bm);
            //BaseModel cible = (BaseModel) session.get(c, id);
            //System.out.println("ID = "+test);
            cible = this.doUpdate(bm, cible); 
            controllSessionFactory(c);
            factory = this.factoryGenerale;
            //create session
            session = factory.getCurrentSession();
            session.beginTransaction();
            //update a object
            session.update(cible);
            //commit transaction
            session.getTransaction().commit();
            
            System.out.println("Update is done!!!");
        }catch (Exception e){
            throw e;
        }finally{
            factory.close();
        }
    }

    @Override
    public void delete(Connection con, BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BaseModel bm) throws Exception {
        Class c = bm.getClass();
        List<BaseModel> lists = null;
        BaseModel basemodel = null;
        controllSessionFactory(c);
        SessionFactory factory = this.factoryGenerale;
        //create session
        Session session = factory.getCurrentSession();
        try{ 
            //create a client object
            BaseModel cible = this.findById2(bm);
            controllSessionFactory(c);
            factory = this.factoryGenerale;
            session = factory.getCurrentSession();
            //start a transaction
            session.beginTransaction();
            
            session.delete(cible);
           
            //commit transaction
            session.getTransaction().commit();
            
            System.out.println("Done!!!");
        }catch(Exception e ){
            throw e;
        }finally{
            factory.close();
        }
    }

    @Override
    public void findById(BaseModel bm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
