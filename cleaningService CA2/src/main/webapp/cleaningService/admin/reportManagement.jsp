<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleaningservice.model.Report" %>

<!DOCTYPE html>
<html>
<head>
    <title>Report Management</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
        }
        table, th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>
<!-- Include Navigation Bar -->
<jsp:include page="navigation.jsp" />
    <div class="container">
        <h2>Report Management</h2>
<form action="<%=request.getContextPath()%>/admin/reports" style="display:inline;">
            <button class="btn-refresh">🔄 Refresh Reports</button></form>

        <table>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Status</th>
                <th>Created At</th>
            </tr>
            <% ArrayList<Report> reports = (ArrayList<Report>) request.getAttribute("reports");
               if (reports != null) {
                   for (Report report : reports) { %>
                    <tr>
                        <td><%= report.getReportID() %></td>
                        <td><%= report.getUserID() %></td>
                        <td><%= report.getStatus() %></td>
                        <td><%= report.getCreatedAt() %></td>
                    </tr>
                <% } } %>
        </table>
    </div>
</body>
</html>
