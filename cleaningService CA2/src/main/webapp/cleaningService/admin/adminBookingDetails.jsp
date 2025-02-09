<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navigation.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Details</title>
    <style>
        .container { margin: 20px auto; width: 50%; padding: 20px; border: 1px solid #ddd; border-radius: 5px; background: #f9f9f9; }
        .btn { padding: 10px 15px; border: none; border-radius: 5px; cursor: pointer; font-size: 14px; }
        .btn-complete { background-color: #28a745; color: white; }
        .btn-back { background-color: #007bff; color: white; }
    </style>
</head>
<body>
    <div class="container">
    <h1>Booking Details</h1>
    <p><strong>Booking ID:</strong> ${bookingDetails.bookingId}</p>
    <p><strong>User Name:</strong> ${bookingDetails.userName}</p>
    <p><strong>Booking Date:</strong> ${bookingDetails.bookingDate}</p>
    <p><strong>Status:</strong> ${bookingDetails.status}</p>
    <p><strong>Total Amount:</strong> $${bookingDetails.price}</p>

    <!-- Dynamically render the button based on the status -->
    <c:choose>
        <c:when test="${bookingDetails.status == 'Completed'}">
            <button class="btn btn-complete" style="background-color: grey; cursor: not-allowed;" disabled>
                Completed
            </button>
        </c:when>
        <c:otherwise>
            <form action="<%= request.getContextPath() %>/admin/updateBookingStatus" method="post" style="display: inline;">
                <input type="hidden" name="bookingId" value="${bookingDetails.bookingId}" />
                <input type="hidden" name="newStatus" value="Completed" />
                <button type="submit" class="btn btn-complete" style="background-color: #28a745; color: white;">
                    Mark as Complete
                </button>
            </form>
        </c:otherwise>
    </c:choose>

    <a href="viewBookings">
        <button class="btn btn-back">Back to Dashboard</button>
    </a>
</div>

</body>
</html>
