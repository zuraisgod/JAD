<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.cleaningservice.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member Management</title>
    <style>
        /* Add existing CSS styles here */
    </style>
</head>
<body>
    <div class="container">
        <h1>Member Management</h1>

        <%-- Fetch Members Button --%>
        <form action="<%=request.getContextPath()%>/admin/members" method="GET">
            <button type="submit" class="btn-fetch">🔄 Fetch Members</button>
        </form>

        <h2>List of Members</h2>
        <div class="container-table">
            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Contact Number</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<User> users = (ArrayList<User>) request.getAttribute("members");

                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                    %>
                        <tr>
                            <td><%= user.getUserId() %></td>
                            <td><%= user.getName() %></td>
                            <td><%= user.getEmail() %></td>
                            <td><%= user.getContactNumber() %></td>
                            <td><%= user.getAddress() %></td>
                            <td>
                                <a href="<%=request.getContextPath()%>/admin/editMember?userId=<%= user.getUserId() %>">
                                    <button class="btn-edit">✏️ Edit</button>
                                </a>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="6">No members found. Please fetch data.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
