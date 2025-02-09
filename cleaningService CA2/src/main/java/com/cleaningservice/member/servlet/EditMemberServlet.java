package com.cleaningservice.member.servlet;

import com.cleaningservice.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@WebServlet("/admin/editMember")
public class EditMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Logic to fetch user details from API
        User user = fetchUserDetails(userId);

        if (user != null) {
            // Set the user object in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Forward to the editMember.jsp page
            RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/editMember.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/members?error=MemberNotFound");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Create a User object and populate it using setter methods
        User user = new User();
        user.setUserId(userId);
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setContactNumber(request.getParameter("contactNumber"));
        user.setAddress(request.getParameter("address"));

        // Call the API to update the user details
        boolean success = updateUserDetails(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/members?success=MemberUpdated");
        } else {
            request.setAttribute("error", "Failed to update member details");
            doGet(request, response); // Reload the form with error message
        }
    }

    private User fetchUserDetails(int userId) {
        try (Client client = ClientBuilder.newClient()) {
            String apiUrl = "http://localhost:8081/api/users/" + userId;
            WebTarget target = client.target(apiUrl);
            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean updateUserDetails(User user) {
        try (Client client = ClientBuilder.newClient()) {
            String apiUrl = "http://localhost:8081/api/users/update/" + user.getUserId();
            WebTarget target = client.target(apiUrl);
            Response response = target.request(MediaType.APPLICATION_JSON)
                                      .post(Entity.entity(user, MediaType.APPLICATION_JSON));

            return response.getStatus() == Response.Status.OK.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
