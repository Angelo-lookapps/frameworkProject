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
@NomTable(value = "TypeAvion", predicat = "TYPA")
public class TypeAvion extends BaseModel{
    private String valeur;
    
    public TypeAvion(String id) {
        super(id);
    }

    public TypeAvion( String id, String valeur) {
        super(id);
        this.valeur = valeur;
    }
    public TypeAvion(){}
 
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
