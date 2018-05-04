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
@NomTable(value = "Destination", predicat = "DES")
public class Destination extends BaseModel{
    private String valeur;
    private Double prix;

    public Destination(String id) {
        super(id);
    }

    public Destination( String id, String valeur, Double prix) {
        super(id);
        this.valeur = valeur;
        this.prix = prix;
    }
    public Destination(){}
    
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
    
}
