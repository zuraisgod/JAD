<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="../css/styles.css"> <!-- Link to your CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        .container {
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }
        .profile-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .profile-header h1 {
            font-size: 2rem;
            margin: 0;
        }
        .grid-container {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 20px;
        }
        .left-panel, .right-panel {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .left-panel h2, .right-panel h2 {
            margin-top: 0;
        }
        .left-panel .booking-card, .left-panel .booking {
        border-bottom: 1px solid #ddd;
        padding-bottom: 15px;
        margin-bottom: 15px;
    }
        .left-panel .booking-card:last-child, .left-panel .booking:last-child {
        border-bottom: none;
    }

        .right-panel form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        .right-panel input, .right-panel textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .right-panel button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background: #28a745;
            color: white;
            cursor: pointer;
            font-size: 1rem;
        }
        .right-panel button.cancel {
            background: #dc3545;
        }
        
        .action-links {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .action-links a {
            text-decoration: none;
            color: #007bff;
        }
        .action-links a:hover {
            text-decoration: underline;
        }
         .section-header {
            background-color: #000;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 1.2rem;
            border-radius: 5px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="profile-header">
            <h1>User Profile</h1>
        </div>
        <div class="grid-container">
            <!-- Left Panel -->
            <div class="left-panel">
            <div class="section-header">My Bookings</div>
<c:forEach var="booking" items="${bookings}">
        <div class="booking-card">
            <h3>${booking.serviceName}</h3>
            <p><strong>Total Amount:</strong> $${booking.price}</p>
            <p><strong>Booking Date:</strong> ${booking.bookingDate}</p>
            <p><strong>Status:</strong> ${booking.status}</p>
        </div>
    </c:forEach>
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    
    
            <div class="section-header">Completed Services</div>
<c:forEach var="booking" items="${completedBookings}">
    <div class="booking">
        <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
        <p><strong>Total Amount:</strong> ${booking.price}</p>
        <p><strong>Booking Date:</strong> ${booking.bookingDate}</p>
        <p><strong>Status:</strong> ${booking.status}</p>
        <p><strong>Service:</strong> ${booking.serviceName}</p>
        <form action="<%= request.getContextPath() %>/SubmitFeedbackServlet" method="post">
            <input type="hidden" name="bookingId" value="${booking.bookingId}">
            <label for="rating">Rating (1-5):</label>
            <input type="number" name="rating" min="1" max="5" required>
            <textarea name="comments" placeholder="Leave your comments here..." required></textarea>
            <button type="submit">Submit Feedback</button>
        </form>
    </div>
</c:forEach>
</div>
            <!-- Right Panel -->
            <div class="right-panel">
            <div class="section-header">Profile</div>
<form action="<%= request.getContextPath() %>/updateProfileServlet" method="post">
        <label>Name:</label>
        <input type="text" name="name" value="${userProfile.name}" required>
        <label>Email:</label>
        <input type="email" name="email" value="${userProfile.email}" readonly>
        <label>Contact Number:</label>
        <input type="text" name="contactNumber" value="${userProfile.contactNumber}" required>
        <label>Address:</label>
        <textarea name="address" required>${userProfile.address}</textarea>
        <button type="submit">Update</button>
    </form>
            <div class="section-header">Change Password</div>
<form action="<%= request.getContextPath() %>/changePasswordServlet" method="post">
    <label for="oldPassword">Old Password:</label>
    <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter old password" required>

    <label for="newPassword">New Password:</label>
    <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required>

    <label for="confirmPassword">Confirm New Password:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>

    <button type="submit">Change Password</button>
</form>

                
            <div class="section-header">Delete Account</div>
<form action="<%= request.getContextPath() %>/DeleteAcount" method="post">    <label for="password">Enter Password:</label>
    <input type="password" id="password" name="password" required>
    <button type="submit" onclick="return confirm('Are you sure you want to delete your account?')">Delete My Account</button>
</form>



            </div>
        </div>
    </div>
</body>
</html>
