/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Angelo-KabyLake
 */
public class Connexion {
    Connection con;
        private String database="";
	public Connexion(){}
        public Connexion(String test){
            this.setDatabase(test);
        }
        public String getDatabase(){
            return this.database;
        }
        public void setDatabase(String dat){
            this.database = dat;
        }
	public Connection getConnexion(String user,String mdp)throws Exception{
		String urlDB = "jdbc:sqlserver://localhost:1433;databaseName="+this.getDatabase()+";";
                if(user.equals("")){
                    throw new Exception("Erreur de user et mdp: getConnexion");
                }
                if(database.equals("")){
                    urlDB = "jdbc:sqlserver://localhost:1433;databaseName=aeroport;";
                } 
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(urlDB,user,mdp);
			//System.out.println("Connexion etablish!!!");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return con;
	}
	public Connection getConnexion()throws Exception{

            String user = "sa";
            String mdp = "itu";
            try{
                    this.getConnexion(user, mdp);
                    //System.out.println("Connexion has been established !");
            }catch(Exception e){
                e.printStackTrace();
            }
            return con;
	}
}
