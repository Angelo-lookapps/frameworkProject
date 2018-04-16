/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.NomTable;

/**
 *
 * @author Angelo-KabyLake
 */
@NomTable(value = "Client", predicat = "CLT")
public class Client extends BaseModel{
    //private String id;
    private String nom;
    private String prenom;

    //Constructeur
  
    public Client( String id, String nom, String prenom) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
    }
    public Client(){}  
    
    ///SETTERS && GETTERS
  
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
  
}
