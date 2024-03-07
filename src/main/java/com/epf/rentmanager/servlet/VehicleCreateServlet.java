package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet {

    private VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affichage du formulaire
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try{
            System.out.println("ts");
            String constructeur = request.getParameter("constructeur");
            System.out.println("constructor");

            String modele = request.getParameter("modele");
            System.out.println("modele");

            int nb_places = Integer.parseInt(request.getParameter("nb_places"));
            System.out.println("nbmodele");

            System.out.println(constructeur + modele + nb_places);
            Vehicle vehicule = new Vehicle(constructeur, modele, nb_places);
            vehicleService.create(vehicule);

            response.sendRedirect(request.getContextPath() + "/vehicles/list");

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException() ;
        }
    }
        }

