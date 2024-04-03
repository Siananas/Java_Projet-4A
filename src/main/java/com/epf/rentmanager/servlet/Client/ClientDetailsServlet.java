package com.epf.rentmanager.servlet.Client;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    @Autowired
    ClientService clientService ;
    @Autowired
    ReservationService reservationService ;
    @Autowired
    VehicleService vehicleService ;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            int clientId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("client", clientService.findById(clientId));
            List <Vehicle> vehicles = new ArrayList<>();
            vehicles = clientService.findAllVehiclesOfClientReservations(clientService.findById(clientId));
            List<Vehicle> vehicleSansDoublons = vehicles.stream().collect(Collectors.toMap(
                            Vehicle::getId, vehicle -> vehicle, (existing, replacement) -> existing, LinkedHashMap::new))
                    .values().stream().collect(Collectors.toList());
            request.setAttribute("vehicles", vehicleSansDoublons);
            request.setAttribute("vehicles_count", vehicleSansDoublons.size());

            List <Reservation> listeReservations = new ArrayList<>();
            listeReservations = reservationService.findByClientId(clientId);
            for (Reservation reservation : listeReservations) {
                String vehicle_contructeur = vehicleService.findById(reservation.getVehicule_id())
                        .getConstructeur();
                String vehicle_modele = vehicleService.findById(reservation.getVehicule_id())
                        .getModele();
                reservation.setVehicle_contructeur_modele(vehicle_contructeur + " " + vehicle_modele);
            }
            request.setAttribute("reservations", listeReservations);
            request.setAttribute("reservations_count", listeReservations.size());



            //List <Reservation> reservations = new ArrayList<>();


            //Client client = new Client();
            //client = clientService.findById(clientId);
            //List <Vehicle> vehicules = new ArrayList<>();
            //vehicules = clientService.findAllVehiclesOfClientReservations(client);
            //
            //System.out.println("client id : "+ clientId);
//            List <Vehicle> vehicules = new ArrayList<>();
//            for (Reservation reservation : reservations)
//            if (!vehicules.contains(reservation.getVehicule_id())){
//
//            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
            dispatcher.forward(request, response);

        } catch(Exception e) {
            throw new RuntimeException() ;
        }
    }
}
