/*
 * BaseModelo change this license header, choose License Headers in Project Properties.
 * BaseModelo change this template file, choose BaseModelools | BaseModelemplates
 * and open the template in the editor.
 */
package dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Angelo-KabyLake
 */
public abstract class AbstractHibernateDao< BaseModel extends Serializable > {
 
   private Class<BaseModel> clazz;
 
   /* @Autowired*/
    SessionFactory sessionFactory;
 
   public final void setClazz( Class<BaseModel> clazzSet ){
      this.clazz = clazzSet;
   }
 
   public BaseModel findOne( long id ){
      return (BaseModel) getCurrentSession().get( clazz, id );
   }
   public List<BaseModel> findAll(){
      return getCurrentSession().createQuery( "from " + clazz.getName() ).list();
   }
 
   public void create(BaseModel entity ){
      getCurrentSession().persist(entity);
   }
 
   public void update(BaseModel entity ){
      getCurrentSession().merge(entity);
   }
 
   public void delete(BaseModel entity ){
      getCurrentSession().delete(entity);
   }
   public void deleteById( long entityId ) {
     BaseModel entity = findOne(entityId);
      delete(entity);
   }
 
   protected final Session getCurrentSession() {
      return sessionFactory.getCurrentSession();
   }
}