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
@NomTable(value = "Reservation", predicat = "RSV")
public class Reservation extends BaseModel{
    private String idClient;
    private String idBillet;
    private String idTypeClasse;
    private String dateReservation;
    
    public Reservation(String id) {
        super(id);
    }

    public Reservation( String id, String idClient, String idBillet, String idTypeClasse, String dateReservation) {
        super(id);
        this.idClient = idClient;
        this.idBillet = idBillet;
        this.idTypeClasse = idTypeClasse;
        this.dateReservation = dateReservation;
    }
    public Reservation(){}
    
    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(String idBillet) {
        this.idBillet = idBillet;
    }

    public String getIdTypeClasse() {
        return idTypeClasse;
    }

    public void setIdTypeClasse(String idTypeClasse) {
        this.idTypeClasse = idTypeClasse;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }
}

