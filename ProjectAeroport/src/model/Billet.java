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
@NomTable(value = "Billet", predicat = "BLT")
public class Billet extends BaseModel{
    private String idDestination;
    private String daty;
    private float prixUnitaire;

    public Billet(String id) {
        super(id);
    }

    public Billet(String id, String idDestination, String daty, float prixUnitaire) {
        super(id);
        this.idDestination = idDestination;
        this.daty = daty;
        this.prixUnitaire = prixUnitaire;
    }
    public Billet(String id, String idDestination, String daty) {
        super(id);
        this.idDestination = idDestination;
        this.daty = daty;
    }
    public Billet(){}
    
    public String getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    public String getDaty() {
        return daty;
    }

    public void setDaty(String daty) {
        this.daty = daty;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
