/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outil;

import java.sql.Connection;
import java.util.ArrayList;
import model.BaseModel;

/**
 *
 * @author Angelo-KabyLake
 */
public interface GeneriqueService {
    public BaseModel[] findAll(Connection con, BaseModel bm)throws Exception;
    public BaseModel[] find(Connection con, BaseModel bm, String condition)throws Exception;
    public BaseModel[] find(BaseModel bm, String condition)throws Exception;
    
    public void insert(Connection con, BaseModel bm)throws Exception;
    public void insert(BaseModel bm)throws Exception;
    
    public void update(Connection con, BaseModel bm, String condition)throws Exception;
    public void update(BaseModel bm, String condition)throws Exception;
    
    public void delete(Connection con, BaseModel bm)throws Exception;
    public void delete( BaseModel bm)throws Exception;
}
