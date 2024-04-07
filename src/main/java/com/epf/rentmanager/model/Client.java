package com.epf.rentmanager.model;
import java.time.LocalDate ;
import java.time.Year;

import static java.time.temporal.ChronoUnit.YEARS;

public class Client {
    private  Integer id ;
    private String nom ;
    private String prenom ;
    private String email ;
    private LocalDate naissance ;

    public Client() {}
    public Client(int id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id ;
        this.nom = nom ;
        this.prenom = prenom ;
        this.email = email ;
        this.naissance = naissance ;
    }

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom ;
        this.prenom = prenom ;
        this.email = email ;
        this.naissance = naissance ;
    }

    public Client(String nom, String prenom, String email) {
        this.nom = nom ;
        this.prenom = prenom ;
        this.email = email ;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client {" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance='" + naissance + '\'' +
                '}';
    }

    public boolean verifAge(LocalDate naissance) {
        LocalDate dateMaintenant = LocalDate.now();
        long age = YEARS.between(naissance, dateMaintenant);
        System.out.println(age);
        return age>18;
    }
}
