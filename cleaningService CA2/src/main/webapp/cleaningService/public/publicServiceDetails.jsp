<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Service Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .service-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }
        .service-card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            text-align: center;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .service-card img {
            max-width: 100%;
            height: auto;
            margin-bottom: 15px;
        }
        .service-card h3 {
            font-size: 1.2rem;
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
        .feedback-link {
            color: #007bff;
            text-decoration: none;
        }
        .feedback-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <%@ include file="publicNav.jsp" %>
    <div class="container">
        <h1>Our Services</h1>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

        <!-- Service Grid -->
        <div class="service-grid">
            <c:forEach items="${services}" var="service">
                <div class="service-card">
                    <img src="${pageContext.request.contextPath}${service.imagePath}" alt="${service.serviceName}">
                    <h3>${service.serviceName}</h3>
                    <p>${service.description}</p>
                    <p class="price">$${service.price}</p>
                    <a href="${pageContext.request.contextPath}/cleaningService/feedback.jsp?serviceId=${service.serviceId}" class="feedback-link">View Feedbacks</a>
                    <p>Log in or <a href="${pageContext.request.contextPath}/cleaningService/register.jsp">register</a> to book this service.</p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
