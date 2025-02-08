<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Service</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { max-width: 500px; margin: auto; }
        label, input, textarea { display: block; width: 100%; margin: 10px 0; }
        input[type="submit"] { background-color: #4CAF50; color: white; border: none; padding: 10px; cursor: pointer; }
        .message {
            font-weight: bold;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
        }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <h2>Add Service</h2>

    <%-- Display success and error messages --%>
    <% String success = request.getParameter("success"); %>
    <% String error = request.getParameter("error"); %>

    <% if (success != null) { %>
        <div class="message success">✅ Service added successfully!</div>
    <% } %>

    <% if (error != null) { %>
        <div class="message error">❌ Failed to add service. Please try again.</div>
    <% } %>

    <form action="<%=request.getContextPath()%>/admin/addService" method="post">
        <label>Service Name:</label>
        <input type="text" name="serviceName" required>

        <label>Description:</label>
        <textarea name="description" required></textarea>

        <label>Price:</label>
        <input type="number" step="0.01" name="price" required>

        <label>Image Path:</label>
        <input type="text" name="imagePath">

        <label>Category ID:</label>
        <input type="number" name="categoryId" required>

        <input type="submit" value="Add Service">
    </form>
    <br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
