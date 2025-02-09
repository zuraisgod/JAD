<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleaningservice.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Member Management</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7fc;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            color: #333;
        }

        .buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-bottom: 20px;
        }

        .buttons button {
            padding: 12px 18px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: opacity 0.3s ease;
        }

        .btn-fetch {
            background-color: #007bff;
            color: white;
        }

        .btn-fetch:hover {
            background-color: #0056b3;
        }

        .btn-sort {
            background-color: #28a745;
            color: white;
        }

        .btn-sort:hover {
            background-color: #218838;
        }

        .btn-edit {
            background-color: #ffc107;
            color: black;
        }

        .btn-edit:hover {
            background-color: #e0a800;
        }

        .container-table {
            max-height: 600px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 6px;
            margin-top: 10px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>

<!-- Include Navigation Bar -->
<jsp:include page="navigation.jsp" />

<div class="container">
    <h1>Member Management</h1>

    <!-- Fetch Members Button -->
    <div class="buttons">
        <form action="<%=request.getContextPath()%>/admin/members" method="GET">
            <button type="submit" class="btn-fetch">🔄 Fetch Members</button>
        </form>
    </div>

    <!-- List of Members -->
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
                        <td><%= user.getName() != null ? user.getName() : "N/A" %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getContactNumber() != null ? user.getContactNumber() : "N/A" %></td>
                        <td><%= user.getAddress() != null ? user.getAddress() : "N/A" %></td>
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

    <!-- Sorting Form -->
    <h2>Sort Members</h2>
    <form action="<%=request.getContextPath()%>/admin/sortUsers" method="GET" class="buttons">
        <label for="sortBy">Sort By:</label>
        <select id="sortBy" name="sortBy">
            <option value="value">Total Value</option>
            <option value="registration_date">Registration Date</option>
            <option value="activity">Total Activity</option>
        </select>
        <button type="submit" class="btn-sort">🔽 Sort</button>
    </form>

    <!-- Sorted Members Table -->
    <h2>Sorted Members</h2>
    <div class="container-table">
        <table>
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Contact Number</th>
                    <th>Address</th>
                    <th>Total Value</th>
                    <th>Registration Date</th>
                    <th>Total Activity</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<User> sortedUsers = (ArrayList<User>) request.getAttribute("sortedUsers");

                    if (sortedUsers != null && !sortedUsers.isEmpty()) {
                        for (User user : sortedUsers) {
                %>
                    <tr>
                        <td><%= user.getUserId() %></td>
                        <td><%= user.getName() != null ? user.getName() : "N/A" %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getContactNumber() != null ? user.getContactNumber() : "N/A" %></td>
                        <td><%= user.getAddress() != null ? user.getAddress() : "N/A" %></td>
                        <td>$<%= user.getTotalValue() %></td>
                        <td><%= user.getRegistrationDate() != null ? user.getRegistrationDate() : "N/A" %></td>

                        <td><%= user.getTotalActivity() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="8">No sorted members found. Please sort the data.</td>
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
