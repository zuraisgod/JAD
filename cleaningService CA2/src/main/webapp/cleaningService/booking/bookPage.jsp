<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Book a Service</title>
</head>
<body>
    <h1>Book Your Service</h1>
    
    <form action="<%=request.getContextPath()%>/finishBookingServlet" method="post">
        <label>User ID:</label>
        <input type="text" name="userID" value="<%= request.getAttribute("userId") %>" readonly><br>

        <label>Service ID:</label>
        <input type="text" name="serviceID" value="<%= request.getAttribute("serviceId") %>" readonly><br>

        <label>Booking Date:</label>
        <input type="date" name="bookingDate" required><br>

        <button type="submit">Finish Booking</button>
    </form>
</body>
</html>
