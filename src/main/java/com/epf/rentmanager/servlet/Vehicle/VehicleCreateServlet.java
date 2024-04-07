package com.epf.rentmanager.servlet.Vehicle;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet {
    @Autowired
            VehicleService vehicleService ;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    //private VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affichage du formulaire
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nb_places = Integer.parseInt(request.getParameter("nb_places"));

            Vehicle vehicule = new Vehicle(constructeur, modele, nb_places);
            vehicleService.create(vehicule);

            response.sendRedirect(request.getContextPath() + "/vehicles/list");

    }
        }

