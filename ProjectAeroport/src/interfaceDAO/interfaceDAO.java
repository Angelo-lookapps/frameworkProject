/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceDAO;

import java.sql.Connection;
import java.util.List;
import model.BaseModel;

/**
 *
 * @author ITU
 */
public interface interfaceDAO {
    List find(Connection con, String table, String where) throws Exception;

    List find(String table, String where) throws Exception;

    void insert(Connection con, BaseModel baseModel) throws Exception;

    void insert(BaseModel baseModel) throws Exception;

    void update(Connection con, BaseModel nouveau, String condition) throws Exception;

    void update(BaseModel nouveau, String condition) throws Exception;
    
    void delete(Connection con, String id) throws Exception;

    void delete(String id) throws Exception;
}
