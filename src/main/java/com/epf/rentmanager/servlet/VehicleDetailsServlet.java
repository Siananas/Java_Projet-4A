package com.epf.rentmanager.servlet;

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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {
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
            throws ServletException, IOException {
        try{
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("vehicle", vehicleService.findById(vehicleId));

            List<Client> clients = new ArrayList<>();
            clients = vehicleService.findAllClientsOfVehicleReservation(vehicleService.findById(vehicleId));
            List<Client> clientsSansDoublons = clients.stream().collect(Collectors.toMap(
                            Client::getId, client -> client, (existing, replacement) -> existing, LinkedHashMap::new))
                    .values().stream().collect(Collectors.toList());
            request.setAttribute("clients", clientsSansDoublons);
            request.setAttribute("clients_count", clientsSansDoublons.size());

            for (Client client : clients){
                System.out.println(client.toString());
            }
            for (Client client : clientsSansDoublons){
                System.out.println(client.toString());
            }

            List <Reservation> listeReservations = new ArrayList<>();
            listeReservations = reservationService.findByVehicleId(vehicleId);
            for (Reservation reservation : listeReservations) {
                String client_nom = clientService.findById(reservation.getClient_id())
                        .getNom();
                String client_prenom = clientService.findById(reservation.getClient_id())
                        .getPrenom();
                reservation.setClient_prenom_nom(client_prenom + " " + client_nom);
            }

            request.setAttribute("reservations", listeReservations);
            request.setAttribute("reservations_count", listeReservations.size());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
            dispatcher.forward(request, response);
        } catch(Exception e) {
            throw new RuntimeException() ;
        }
    }
}
