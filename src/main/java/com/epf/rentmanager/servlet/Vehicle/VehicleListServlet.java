package com.epf.rentmanager.servlet.Vehicle;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/list")
public class VehicleListServlet extends HttpServlet {
    @Autowired
    VehicleService vehicleService ;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                int vehicleId = Integer.parseInt(request.getParameter("id"));
                vehicleService.delete(vehicleId);
                response.setHeader("Refresh", "0; URL=" + request.getContextPath() + "/vehicles/list");
            }
            request.setAttribute("vehicles", vehicleService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
    }
}