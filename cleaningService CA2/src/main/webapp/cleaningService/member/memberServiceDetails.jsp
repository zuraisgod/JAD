<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Service Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .service-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .service-card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .service-card img {
            max-width: 100%;
            height: auto;
            margin-bottom: 15px;
        }
        .service-card h3 {
            font-size: 1.5rem;
            margin-bottom: 10px;
        }
        .service-card p {
            font-size: 1rem;
            color: #555;
            margin-bottom: 10px;
        }
        .price {
            font-weight: bold;
            color: green;
            margin-bottom: 15px;
        }
        .book-button, .cart-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 15px;
            margin: 5px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
        }
        .book-button:hover, .cart-button:hover {
            background-color: #0056b3;
        }
        .error-message {
            font-size: 1.2rem;
            color: red;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="memberNav.jsp" %>
    <div class="container">
        <h1>Services in Selected Category</h1>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <!-- Service Grid -->
        <div class="service-grid">
            <c:if test="${not empty services}">
                <c:forEach var="service" items="${services}">
    <div class="service-card">
        <img src="${pageContext.request.contextPath}${service.imagePath}" alt="${service.serviceName}">
        <div class="service-card-content">
            <h3>${service.serviceName}</h3>
            <p>${service.description}</p>
            <p>Service ID Debug: ${service.serviceId}</p> <!-- ✅ Debugging Line -->
            <p class="price"><strong>Price:</strong> $${service.price}</p>

            <!-- Book Now Button -->
            <form action="<%=request.getContextPath()%>/ProcessBookingServlet" method="post">
                <input type="hidden" name="serviceId" value="${service.serviceId}">
                <button type="submit" class="book-button">Book Now</button>
            </form>
            <form action="<%=request.getContextPath()%>/addToCartServlet" method="post">
    <input type="hidden" name="serviceId" value="${service.serviceId}">
    <button type="submit" class="cart-button">Add to Cart</button>
</form>

        </div>
    </div>
</c:forEach>

            </c:if>
            <c:if test="${empty services}">
                <p>No services are available for this category at the moment.</p>
            </c:if>
        </div>
    </div>
</body>
</html>
