package com.epf.rentmanager.model;
import java.time.LocalDate ;

import java.time.LocalDate;

public class Reservation {

    private Integer id ;
    private Integer client_id ;
    private Integer vehicule_id ;
    private LocalDate debut ;
    private LocalDate fin ;
    private String client_nom;
    private String vehicle_contructeur_modele;

    public Reservation (){}

    public Reservation(Integer client_id, Integer vehicule_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Integer id, Integer client_id, Integer vehicule_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin ;
    }

    public Integer getId() {
        return id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public Integer getVehicule_id() {
        return vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public String getClient_nom() {
        return client_nom;
    }

    public String getVehicle_contructeur_modele() {
        return vehicle_contructeur_modele;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public void setVehicule_id(Integer vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) { this.fin = fin; }

    public void setClient_nom(String client_nom) {
        this.client_nom = client_nom;
    }

    public void setVehicle_contructeur_modele(String vehicle_contructeur_modele) {
        this.vehicle_contructeur_modele = vehicle_contructeur_modele;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicule_id=" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
