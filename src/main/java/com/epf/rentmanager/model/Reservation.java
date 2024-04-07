package com.epf.rentmanager.model;
import com.epf.rentmanager.exception.ServiceException;

import java.time.LocalDate ;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {

    private Integer id ;
    private Integer client_id ;
    private Integer vehicule_id ;
    private LocalDate debut ;
    private LocalDate fin ;
    private String client_nom;
    private String vehicle_contructeur_modele;
    private String client_prenom_nom ;

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

    public String getClient_prenom_nom() { return client_prenom_nom; }

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

    public void setClient_prenom_nom(String client_prenom_nom) {
        this.client_prenom_nom = client_prenom_nom;
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

    public boolean verifDureeReservation(LocalDate debutReservation, LocalDate finReservation) {
        long dureeReservation = DAYS.between(debutReservation, finReservation);
        return dureeReservation>7;
    }

    public boolean verifDisponibiliteVehicle(Reservation reservation, List <Reservation> listeReservation ){
        for (Reservation autreReservation : listeReservation) {
            if(!autreReservation.getDebut().isBefore(reservation.getDebut())
                    && !autreReservation.getDebut().isAfter(reservation.getFin())
                    || !autreReservation.getFin().isBefore(reservation.getDebut())
                    && !autreReservation.getFin().isAfter(reservation.getFin())) {
                return true ;
            }
        }
        return false;
    }

    public boolean calculDureeLocationVoitureDepasse30Jours(List<Reservation> listeReservation){
        long dureeReservationVoiture = DAYS.between(this.getDebut(), this.getFin());
        listeReservation.sort(Comparator.comparing(Reservation::getDebut));

        Reservation reservationTraitee = this;
        for (int i=0 ; i < listeReservation.size(); i++){
            while(reservationTraitee.getDebut().minusDays(1).isEqual(listeReservation.get(i).getFin())){
                dureeReservationVoiture += DAYS.between(listeReservation.get(i).getDebut(), listeReservation.get(i).getFin());
                System.out.println(dureeReservationVoiture);
                System.out.println("nbr boucle et i = " + i);
                reservationTraitee=listeReservation.get(i);
                i--;
                if(i<0){break;}
            }
        }
        if (dureeReservationVoiture>30){return true;}
        return false;
    }
}
