<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login</title>
    <link rel="stylesheet" href="../css/styles.css">
    <style>
        .navigation-links {
            margin-top: 15px;
        }
        .navigation-links a {
            margin-right: 10px;
            text-decoration: none;
            color: #007bff;
        }
        .navigation-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Login Page</h1>
    <%
        // Display error message if present
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            out.print("<p style='color:red;'>" + errorMessage + "</p>");  
        }
    %>
    <form action="<%=request.getContextPath()%>/loginServlet" method="post">
        <label for="email">Email:</label>
        <input type="email" name="email" placeholder="Enter your email" required><br>
        <label for="password">Password:</label>
        <input type="password" name="password" placeholder="Enter your password" required><br>
        <button type="submit">Login</button>
    </form>

    <!-- Navigation Links -->
    <div class="navigation-links">
        <a href="<%=request.getContextPath()%>/public/dashboard">View as Public</a>
        <a href="<%=request.getContextPath()%>/cleaningService/register.jsp">Register</a>
    </div>
</body>
</html>
