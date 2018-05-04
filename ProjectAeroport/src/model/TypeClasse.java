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
@NomTable(value = "TypeClasse", predicat = "TYPC")
public class TypeClasse extends BaseModel{
    private String valeur;
    
    public TypeClasse(String id) {
        super(id);
    }

    public TypeClasse( String id, String valeur) {
        super(id);
        this.valeur = valeur;
    }
    public TypeClasse(){}
 
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}

