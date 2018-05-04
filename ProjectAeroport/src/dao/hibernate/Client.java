/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.hibernate;

import annotations.NomTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import model.BaseModel;

/**
 *
 * @author Angelo-KabyLake
 */
@Entity
@Table(name="Client")
@NomTable(value = "Client", predicat = "CLT")
public class Client extends BaseModel { 
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="nom")
    private String nom;
    
    @Column(name="prenom")
    private String prenom;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    
    //Constructeur
    public Client(){}
    public Client(String id,String nom, String prenom) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
    }
}
