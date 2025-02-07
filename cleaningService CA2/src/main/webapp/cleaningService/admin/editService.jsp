<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.cleaningservice.model.Service" %>
<%
    Service service = (Service) session.getAttribute("service"); // ✅ Use session
    if (service == null) {
        response.sendRedirect(request.getContextPath() + "/cleaningService/admin/adminDashboard.jsp?error=ServiceNotFound");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Service</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { max-width: 500px; margin: auto; }
        label, input, textarea { display: block; width: 100%; margin: 10px 0; }
        input[type="submit"] { background-color: #4CAF50; color: white; border: none; padding: 10px; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Edit Service</h2>
    <form action="<%=request.getContextPath()%>/admin/editService" method="post">

        <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">
        <label>Service Name:</label>
        <input type="text" name="serviceName" value="<%= service.getServiceName() %>" required>
        
        <label>Description:</label>
        <textarea name="description" required><%= service.getDescription() %></textarea>
        
        <label>Price:</label>
        <input type="number" step="0.01" name="price" value="<%= service.getPrice() %>" required>
        
        <label>Image Path:</label>
        <input type="text" name="imagePath" value="<%= service.getImagePath() %>">
        
        <label>Category ID:</label>
        <input type="number" name="categoryId" value="<%= service.getCategoryId() %>" required>
        
        <input type="submit" value="Update Service">
    </form>
    <br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
