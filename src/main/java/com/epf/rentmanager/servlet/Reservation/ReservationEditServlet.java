package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
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
import java.time.LocalDate;

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService ;

    @Autowired
    private VehicleService vehicleService ;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reservationId = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationService.findReservationById(reservationId);
        request.setAttribute("reservation", reservation);

        request.setAttribute("vehicles", vehicleService.findAll());
        request.setAttribute("clients", clientService.findAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reservationId = Integer.parseInt(request.getParameter("id"));

        int selectedClientId = Integer.parseInt(request.getParameter("client"));
        int selectedVehicleId = Integer.parseInt(request.getParameter("car"));
        LocalDate debut = LocalDate.parse(request.getParameter("debut"));
        LocalDate fin = LocalDate.parse(request.getParameter("fin"));
        Reservation reservation = new Reservation(reservationId, selectedClientId, selectedVehicleId, debut, fin);

        reservationService.updateReservation(reservation);
        response.sendRedirect(request.getContextPath() + "/rents/list");
    }
}