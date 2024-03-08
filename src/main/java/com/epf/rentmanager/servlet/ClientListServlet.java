package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ClientService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/list")
public class ClientListServlet extends HttpServlet {

    private ClientService clientService = ClientService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            request.setAttribute("clients", clientService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (Exception e){
            throw new RuntimeException();
        }

    }

}
