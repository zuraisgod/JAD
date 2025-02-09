<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleaningservice.model.Inquiry" %>

<!DOCTYPE html>
<html>
<head>
    <title>Inquiry Management</title>
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
        <h2>Inquiry Managment</h2>
                        <form action="<%=request.getContextPath()%>/admin/inquiries" style="display:inline;">
            <button class="btn-refresh">🔄 Refresh Inquiries</button></form>
        <form action="<%=request.getContextPath()%>/admin/searchInquiries" method="GET">
            <input type="text" name="keyword" placeholder="Search inquiries..." required>
            <button type="submit">Search</button>
        </form>
        <table>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Service ID</th>
                <th>Subject</th>
                <th>Message</th>
                <th>Status</th>
                <th>Created At</th>
                <th>Resolved At</th>
            </tr>
            <% ArrayList<Inquiry> inquiries = (ArrayList<Inquiry>) request.getAttribute("inquiries");
               if (inquiries != null) {
                   for (Inquiry inquiry : inquiries) { %>
                    <tr>
                        <td><%= inquiry.getInquiryID() %></td>
                        <td><%= inquiry.getUserID() %></td>
                        <td><%= inquiry.getServiceID() %></td>
                        <td><%= inquiry.getSubject() %></td>
                        <td><%= inquiry.getMessage() %></td>
                        <td><%= inquiry.getStatus() %></td>
                        <td><%= inquiry.getCreatedAt() %></td>
                        <td><%= (inquiry.getResolvedAt() != null) ? inquiry.getResolvedAt() : "N/A" %></td>
                    </tr>
                <% } } %>
        </table>
    </div>
</body>
</html>