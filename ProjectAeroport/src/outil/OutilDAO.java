/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outil;

import dao.hibernate.*;
import annotations.NomTable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import model.BaseModel;
import outil.Fonction;
/**
 *
 * @author Angelo-KabyLake
 */
public class OutilDAO {
    Fonction fonction = new Fonction();
    public String getSequenceId(BaseModel bm) throws Exception{
        Class c = bm.getClass();
        String sequence = "seq_";
        String pret = "";
        String id= "";
        try{
            NomTable annot = (NomTable)c.getAnnotation(NomTable.class);
            sequence+= annot.value();
            pret = annot.predicat();
            if(annot.predicat().equals("")){
                throw new Exception("Erreur : l'annotation  'predicat' à cette n' a pas été assignée!");
            }
            id = fonction.getSeq(pret, sequence);
            if(id.equals("")){
               throw new Exception("Erreur : la récupeartion de l' ID de l'objet : "+c.getSimpleName()+" n'a pas aboutit!");
            }
        }catch(Exception e){
            throw e;
        }
        return id;
    }
    public Method getSetter(BaseModel bm, Field f) throws Exception{
        String set = ""; 
        Method method = null;
        try{
            
            Class type = f.getType();
            set = "set";
            set += capitalize(f);
            method = bm.getClass().getMethod(set, type);  //setNom(String
        
        }catch(Exception e){
            throw e;
        }
        return method;
    }
    public String capitalize(Field f){
        String ret = "";
        ret += f.getName().substring(0, 1).toUpperCase();
        ret += f.getName().substring(1);
        return ret;
    }
    public Method getGetter(BaseModel bm, Field f) throws Exception{
        String get = ""; 
        Method method = null;
        try{
            get = "get";
            get += capitalize(f);
            method = bm.getClass().getMethod(get);  //setNom(String
        }catch(Exception e){
            throw e;
        }
        return method;
    }
    
    
}
