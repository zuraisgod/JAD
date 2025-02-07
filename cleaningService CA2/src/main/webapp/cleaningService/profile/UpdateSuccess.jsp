<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background: #fff;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .container h1 {
            color: #28a745;
        }
        .container p {
            font-size: 1.1rem;
            margin: 10px 0;
        }
        .container .error {
            color: #dc3545;
            font-size: 1rem;
            margin: 10px 0;
        }
        .container button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
        }
        .container button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Profile Updated Successfully!</h1>
        <p>Your profile information has been updated.</p>
        <!-- Check for errorMessage in session -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <p class="error"><%= errorMessage %></p>
        <%
            }
        %>
        
        <button onclick="location.href='<%= request.getContextPath() %>/ProfileServlet'">Go Back to Profile</button>
    </div>
</body>
</html>
