package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    //ClientService clientService = ClientService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
            dispatcher.forward(request, response);
        } catch(Exception e) {
            throw new RuntimeException() ;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));

            Client client = new Client(nom, prenom, email, naissance) ;
            //clientService.create(client);

            response.sendRedirect(request.getContextPath() + "/users/list");

        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException() ;
        }
    }
}
