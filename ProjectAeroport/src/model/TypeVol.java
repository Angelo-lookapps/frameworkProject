/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotations.NomTable;

/**
 *
 * @author Ryan
 */

@NomTable(value = "TypeVol", predicat = "TYPV")
public class TypeVol extends BaseModel{
    private String valeur;
    
    public TypeVol(String id) {
        super(id);
    }

    public TypeVol( String id, String valeur) {
        super(id);
        this.valeur = valeur;
    }
    public TypeVol(){}
 
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}

