package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VehicleService vehicleService = VehicleService.getInstance();
	//private ClientService clientService = ClientService.getInstance();
	private ReservationService reservationService = ReservationService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int nombreVehicles = vehicleService.count();
			request.setAttribute("nombreVehicles", nombreVehicles);

			//int nombreClients = clientService.count();
			//request.setAttribute("nombreClients", nombreClients);

			int nombreReservations = reservationService.count();
			request.setAttribute("nombreReservations", nombreReservations);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

}
