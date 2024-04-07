package com.epf.rentmanager.servlet.Reservation;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/list")
public class ReservationListServlet extends HttpServlet {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService ;
    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int reservationId = Integer.parseInt(request.getParameter("id"));
            reservationService.delete(reservationId);
            response.setHeader("Refresh", "0; URL=" + request.getContextPath() + "/rents/list");
        }

        List <Reservation> listeReservations = reservationService.findAll();
        for (Reservation reservation : listeReservations){
            Client client = clientService.findById(reservation.getClient_id());
            reservation.setClient_nom(client.getPrenom() + " " + client.getNom());

            Vehicle vehicle = vehicleService.findById(reservation.getVehicule_id());
            reservation.setVehicle_contructeur_modele(vehicle.getConstructeur() + " " + vehicle.getModele());
        }
        request.setAttribute("reservations", listeReservations);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
    }
}