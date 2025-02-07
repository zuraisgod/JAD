<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <h1>Welcome to the Admin Dashboard</h1>
<<<<<<< Updated upstream

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
            <th>Action</th> <%-- New column for Edit button --%>
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
                <td>
                    <a href="<%=request.getContextPath()%>/admin/editService?serviceId=<%= service.getServiceId() %>">
                        <button>Edit</button>
                    </a>
                </td> <%-- Edit button redirects to editService.jsp --%>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="7">No services found.</td>
            </tr>
        <%
            }
        %>
    </tbody>
</table>

=======
>>>>>>> Stashed changes
</body>
</html>
