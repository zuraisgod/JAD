<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navigation.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Bookings</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        th { background-color: #333; color: white; }
        .btn-details { background-color: #007bff; color: white; border: none; border-radius: 5px; padding: 5px 10px; cursor: pointer; }
    </style>
</head>
<body>
    <h1>View Bookings</h1>
    <table>
        <thead>
            <tr>
                <th>Booking ID</th>
                <th>User</th>
                <th>Booking Date</th>
                <th>Status</th>
                <th>Total Amount</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="booking" items="${bookings}">
                <tr>
                    <td>${booking.bookingId}</td>
                    <td>${booking.userName}</td>
                    <td>${booking.bookingDate}</td>
                    <td>${booking.status}</td>
                    <td>${booking.price}</td>
                    <td>
                        <form action="viewBookingDetails" method="get" style="display: inline;">
                            <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                            <button type="submit" class="btn-details">Details</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
