<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <h1>Your Cart</h1>
    <table>
        <thead>
            <tr>
                <th>Service Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.serviceName}</td>
                    <td>${item.quantity}</td>
                    <td>$${item.price}</td>
                    <td>$${item.totalPrice}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p>Grand Total: <strong>$<c:out value="${cartItems.stream().mapToDouble(i -> i.totalPrice).sum()}"/></strong></p>
    <button onclick="location.href='proceedBooking.jsp'">Proceed to Book</button>
    <button onclick="location.href='clearCartServlet'">Clear Cart</button>
</body>
</html>
