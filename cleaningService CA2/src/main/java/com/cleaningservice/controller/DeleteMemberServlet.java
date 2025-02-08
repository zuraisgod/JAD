package com.cleaningservice.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@WebServlet("/admin/deleteMember")
public class DeleteMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        try (Client client = ClientBuilder.newClient()) {
            String apiUrl = "http://localhost:8081/api/users/" + userId;
            WebTarget target = client.target(apiUrl);
            Response apiResponse = target.request().delete();

            if (apiResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                response.sendRedirect(request.getContextPath() + "/admin/members?success=MemberDeleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/members?error=DeleteFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/members?error=ServerError");
        }
    }
}
