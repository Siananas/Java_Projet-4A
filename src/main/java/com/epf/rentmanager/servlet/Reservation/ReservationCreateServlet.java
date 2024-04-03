package com.epf.rentmanager.servlet.Reservation;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.servlet.HomeServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HomeServlet {
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
        try{
            request.setAttribute("vehicles", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());
        } catch (Exception e){
            throw new RuntimeException();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try{
            int selectedClientId = Integer.parseInt(request.getParameter("client"));
            int selectedVehicleId = Integer.parseInt(request.getParameter("car"));
            LocalDate debut = LocalDate.parse(request.getParameter("debut"));
            LocalDate fin = LocalDate.parse(request.getParameter("fin"));

            Reservation reservation = new Reservation(selectedClientId, selectedVehicleId, debut, fin);
            reservationService.create(reservation);

            response.sendRedirect(request.getContextPath() + "/rents/list");
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

}
