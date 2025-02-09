<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleaningservice.model.Service" %>
    <%@ include file="navigation.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .message {
            font-weight: bold;
            margin-bottom: 15px;
            padding: 10px;
            border-radius: 5px;
        }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background: white;
        }
        th, td {
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover { background-color: #f1f1f1; }
        .buttons {
            margin-bottom: 15px;
        }
        button {
            padding: 10px 15px;
            font-size: 16px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-add {
            background-color: #28a745;
            color: white;
        }
        .btn-refresh {
            background-color: #17a2b8;
            color: white;
        }
        .btn-edit {
            background-color: #ffc107;
            color: black;
        }
        button:hover {
            opacity: 0.8;
        }
        .container-table {
		    max-height: 700px; /* Adjust height as needed */
		    overflow-y: auto;
		    border: 1px solid #ddd;
		}
        
    </style>
</head>
<body>
    <div class="container">
        <h1>Admin Dashboard</h1>
        <form action="<%=request.getContextPath()%>/admin/searchService" method="GET">
            <input type="text" name="keyword" placeholder="Search services...">
            <button type="submit">Search</button>
        </form>
        <%-- Display success and error messages --%>
        <% String success = request.getParameter("success"); %>
        <% String error = request.getParameter("error"); %>

        <% if (success != null) { %>
            <div class="message success">
                <%= success.equals("ServiceUpdated") ? "✅ Service updated successfully!" : "" %>
                <%= success.equals("ServiceDeletedSuccessfully") ? "✅ Service deleted successfully!" : "" %>
                <%= success.equals("ServiceAddedSuccessfully") ? "✅ Service added successfully!" : "" %>
            </div>
        <% } %>

        <% if (error != null) { %>
            <div class="message error">
                <%= error.equals("UpdateFailed") ? "❌ Service update failed!" : "" %>
                <%= error.equals("ServiceNotFound") ? "❌ Service not found!" : "" %>
                <%= error.equals("ServerError") ? "❌ Internal server error!" : "" %>
                <%= error.equals("DeleteFailed") ? "❌ Service deletion failed!" : "" %>
            </div>
        <% } %>

        <div class="buttons">
            <a href="<%=request.getContextPath()%>/cleaningService/admin/addService.jsp">
                <button class="btn-add">➕ Add New Service</button>
            </a>
            <form action="<%=request.getContextPath()%>/admin/dashboard" style="display:inline;">
                <button class="btn-refresh">🔄 Refresh Services</button>
            </form>
        </div>

        <h2>List of Services</h2>
<div class="container-table">
        <table >
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Service Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Image</th>
                    <th>Action</th> <%-- Edit button column --%>
                </tr>
            </thead>
            <tbody>
                <%
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
                        <td><img src="<%= service.getImagePath() %>" alt="Service Image" width="50"></td>
                        <td>
                            <a href="<%=request.getContextPath()%>/admin/editService?serviceId=<%= service.getServiceId() %>">
                                <button class="btn-edit">✏️ Edit</button>
                            </a>
                        </td>
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
        </div>
    </div>
</body>
</html>
