<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.cleaningservice.model.Service" %>
<%
    Service service = (Service) session.getAttribute("service");
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
<form action="<%=request.getContextPath()%>/admin/editService" method="post" enctype="multipart/form-data">
    <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">
    
    <label>Service Name:</label>
    <input type="text" name="serviceName" value="<%= service.getServiceName() %>" required>
    
    <label>Description:</label>
    <textarea name="description" required><%= service.getDescription() %></textarea>
    
    <label>Price:</label>
    <input type="number" step="0.01" name="price" value="<%= service.getPrice() %>" required>
    
    <label>Current Image:</label>
    <img src="<%= request.getContextPath() %>/<%= service.getImagePath() %>" alt="Service Image" style="max-width: 100px; display: block;">

    <!-- Hidden input to send existing image path -->
    <input type="hidden" name="existingImage" value="<%= service.getImagePath() %>">
    
    <label>Upload New Image:</label>
    <input type="file" name="image" accept="image/*">
    
    <label>Category ID:</label>
    <input type="number" name="categoryId" value="<%= service.getCategoryId() %>" required>
    
    <input type="submit" value="Update Service">
</form>

    <form id="deleteForm" action="<%=request.getContextPath()%>/admin/deleteService" method="post">
    <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">
    <input type="submit" value="Delete Service" onclick="return confirmDelete();">
    </form>

    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete this service?");
        }
    </script>
    
    <br>
    <a href="<%=request.getContextPath()%>/admin/dashboard">Back to Dashboard</a>
</body>
</html>
