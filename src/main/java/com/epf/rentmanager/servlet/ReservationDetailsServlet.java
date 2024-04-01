package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Reservation;
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

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
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
            int reservationId = Integer.parseInt(request.getParameter("id"));
            Reservation reservation = new Reservation();
            reservation = reservationService.findReservationById(reservationId);
            request.setAttribute("reservation", reservation);

            request.setAttribute("client", clientService.findById(reservation.getClient_id()));
            request.setAttribute("vehicle", vehicleService.findById(reservation.getVehicule_id()));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
                dispatcher.forward(request, response);
        } catch(Exception e) {
            throw new RuntimeException() ;
        }
    }
}
