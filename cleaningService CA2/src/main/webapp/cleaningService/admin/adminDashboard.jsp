<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleaningservice.model.Service" %>

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
        .container-table {
            max-height: 700px;
            overflow-y: auto;
            border: 1px solid #ddd;
        }
        .filter-form {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
    </style>
    <script>
        function toggleTimeFrame() {
            var sortBy = document.getElementById("sortBy").value;
            var timeFrameDiv = document.getElementById("timeFrameDiv");
            if (sortBy === "popularity") {
                timeFrameDiv.style.display = "inline";
            } else {
                timeFrameDiv.style.display = "none";
            }
        }
    </script>
</head>
<body>

<!-- Include Navigation Bar -->
<jsp:include page="navigation.jsp" />

<div class="container">
    <h1>Admin Dashboard</h1>

    <!-- Search Bar -->
    <form action="<%=request.getContextPath()%>/admin/searchService" method="GET">
        <input type="text" name="keyword" placeholder="Search services...">
        <button type="submit">Search</button>
    </form>

    <!-- Buttons -->
    <div class="buttons">
        <a href="<%=request.getContextPath()%>/cleaningService/admin/addService.jsp">
            <button class="btn-add">➕ Add New Service</button>
        </a>
        <form action="<%=request.getContextPath()%>/admin/dashboard" style="display:inline;">
            <button class="btn-refresh">🔄 Refresh Services</button>
        </form>
    </div>

    <!-- Sorting & Filtering Form -->
    <form action="<%=request.getContextPath()%>/admin/filtered-services" method="get" class="filter-form">
        <label for="categoryId">Category ID:</label>
        <input type="number" id="categoryId" name="categoryID" min="1">

        <label for="minPrice">Min Price:</label>
        <input type="number" id="minPrice" name="minPrice" step="0.01" value="0.00" required>

        <label for="maxPrice">Max Price:</label>
        <input type="number" id="maxPrice" name="maxPrice" step="0.01" value="50" required>

        <label for="sortBy">Sort By:</label>
        <select id="sortBy" name="sortBy" onchange="toggleTimeFrame()">
            <option value="popularity">Popularity</option>
            <option value="rating">Rating</option>
        </select>

        <div id="timeFrameDiv" style="display: inline;">
            <label for="timeFrame">Time Frame:</label>
            <select id="timeFrame" name="timeFrame">
                <option value="month">Month</option>
                <option value="year">Year</option>
                <option value="all">All Time</option>
            </select>
        </div>

        <button type="submit">Filter</button>
    </form>

    <h2>List of Services</h2>
    <div class="container-table">
        <table>
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Service Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category ID</th>
                    <th>Category Name</th>
                    <th>Image</th>
                    <th>Action</th>
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
                        <td><%= service.getCategoryId() %></td>
                        <td><%= service.getCategoryName() %></td>
                        <td><img src="<%= request.getContextPath() %><%= service.getImagePath() %>" alt="Service Image" width="50"></td>
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
                        <td colspan="8">No services found.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Filtered Services Table -->
    <h2>Filtered Services</h2>
    <div class="container-table">
        <table>
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Service Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category ID</th>
                    <th>Category Name</th>
                    <th>Popularity</th>
                    <th>Average Rating</th>
                    <th>Image</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Service> filteredServices = (ArrayList<Service>) request.getAttribute("filteredServices");
                    if (filteredServices != null && !filteredServices.isEmpty()) {
                        for (Service service : filteredServices) {
                %>
                    <tr>
                        <td><%= service.getServiceId() %></td>
                        <td><%= service.getServiceName() %></td>
                        <td><%= service.getDescription() %></td>
                        <td>$<%= service.getPrice() %></td>
                        <td><%= service.getCategoryId() %></td>
                        <td><%= service.getCategoryName() %></td>
                        <td><%= service.getPopularity() %></td>
                        <td><%= service.getAverageRating() %></td>
                        <td><img src="<%= request.getContextPath() %><%= service.getImagePath() %>" width="50"></td>
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
                        <td colspan="9">No filtered services available.</td>
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
