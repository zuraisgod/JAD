package com.cleaningservice.member.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.GenericType;
import java.io.IOException;
import java.util.ArrayList;

import com.cleaningservice.model.Service;

/**
 * Servlet implementation class SearchServiceServlet
 */
@WebServlet("/admin/searchService")
public class SearchServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchServiceServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/api/services/search").queryParam("keyword", keyword);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.get();
        
        if (apiResponse.getStatus() == 200) {
            ArrayList<Service> services = apiResponse.readEntity(new GenericType<ArrayList<Service>>() {});
            request.setAttribute("services", services);
        } else {
            request.setAttribute("error", "ServiceNotFound");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
