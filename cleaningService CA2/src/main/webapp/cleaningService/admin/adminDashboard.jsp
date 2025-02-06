<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="com.cleaningservice.model.Service" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Admin Dashboard</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        img {
            max-width: 50px;
            height: auto;
        }
    </style>
</head>
<body>
    <h1>Welcome to the Admin Dashboard</h1>

    <h2>List All Services</h2>
    <form action="<%=request.getContextPath()%>/admin/dashboard">
        <input type="submit" value="Refresh Services">
    </form>

    <%
        String error = (String) request.getAttribute("err");
        if (error != null && error.equals("NotFound")) {
    %>
        <p style="color: red;">Error: Services not found!</p>
    <%
        }
    %>

    <table>
        <thead>
            <tr>
                <th>Service ID</th>
                <th>Service Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Category Name</th>
                <th>Image</th>
            </tr>
        </thead>
        <tbody>
            <%
                @SuppressWarnings("unchecked")
                ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");

                if (services != null && !services.isEmpty()) {
                    for (Service service : services) {
            %>
                <tr>
                    <td><%= service.getServiceId() %></td>
                    <td><%= service.getServiceName() %></td>
                    <td><%= service.getDescription() %></td>
                    <td>$<%= service.getPrice() %></td>
                    <td><%= service.getCategoryName() %></td>
                    <td><img src="<%= service.getImagePath() %>" alt="Service Image"></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="6" style="text-align:center;">No services available.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
