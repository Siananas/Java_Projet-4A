package com.epf.rentmanager.model;

public class Vehicle {

    private Integer id ;
    private  String constructeur ;
    private String modele ;
    private Integer nb_places ;

    public Vehicle(){}

    public Vehicle(String constructeur, String modele, Integer nb_places){
        this.constructeur = constructeur ;
        this.modele = modele ;
        this.nb_places = nb_places ;
    }

    public Vehicle(Integer id, String constructeur, String modele, Integer nb_places){
        this.id = id ;
        this.constructeur = constructeur ;
        this.modele = modele ;
        this.nb_places = nb_places ;
    }

    public Integer getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public Integer getNb_places() {
        return nb_places;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(Integer nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }
}
