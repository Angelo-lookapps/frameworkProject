/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.NomTable;

/**
 *
 * @author ITU
 */
@NomTable(value = "Avion", predicat = "AVI")
public class Avion extends BaseModel{
    private int idTypeAvion;
    private int idTypeVol;
    private String nom;

    public Avion(String id) {
        super(id);
    }

    public Avion( String id, int idTypeAvion, int idTypeVol, String nom) {
        super(id);
        this.idTypeAvion = idTypeAvion;
        this.idTypeVol = idTypeVol;
        this.nom = nom;
    }
    public Avion(){}
    
    public int getIdTypeAvion() {
        return idTypeAvion;
    }

    public void setIdTypeAvion(int idTypeAvion) {
        this.idTypeAvion = idTypeAvion;
    }

    public int getIdTypeVol() {
        return idTypeVol;
    }

    public void setIdTypeVol(int idTypeVol) {
        this.idTypeVol = idTypeVol;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
