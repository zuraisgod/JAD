<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="../css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
            background-color: #f9f9f9;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        th, td {
            text-align: center;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        td {
            color: #333;
        }

        .quantity-controls {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .quantity-controls button {
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 5px 10px;
            cursor: pointer;
            font-size: 14px;
        }

        .quantity-controls button:hover {
            background-color: #45a049;
        }

        .grand-total {
            font-weight: bold;
            font-size: 1.2em;
            margin: 20px auto;
            text-align: center;
            color: #333;
        }

        .buttons-container {
            text-align: center;
            margin: 20px auto;
        }

        .buttons-container button {
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            margin: 0 10px;
        }

        .buttons-container button:hover {
            background-color: #45a049;
        }
    </style>
      <script>
        // JavaScript for Updating Quantity
        function updateQuantity(serviceId, change) {
            const userId = '<%= session.getAttribute("userId") %>'; // Get userId from session
            const action = (change > 0) ? "increase" : "decrease";

            fetch(`/api/cart/${action}/${userId}/${serviceId}`, {
                method: 'PUT',
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload(); // Reload page to reflect updated cart
                } else {
                    alert(data.message || "Failed to update quantity");
                }
            })
            .catch(error => {
                console.error("Error updating quantity:", error);
            });
        }
    </script>
</head>
<body>
    <h1>Your Cart</h1>
    <form action="proceedToCheckout" method="post">
    <table>
        <thead>
            <tr>
                <th>Select</th>
                <th>Service Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total</th>
                <th>Date & Time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td><input type="checkbox" name="selectedItems" value="${item.serviceId}" /></td>
                    <td>${item.serviceName}</td>
                    
                    <td>
                        <div class="quantity-controls">
                            <button onclick="updateQuantity(${item.serviceId}, -1)">-</button>
                            ${item.quantity}
                            <button onclick="updateQuantity(${item.serviceId}, 1)">+</button>
                        </div>
                    </td>
                    <td>$${item.price}</td>
                    <td>$${item.totalPrice}</td>
                    <td>
                        <input type="datetime-local" name="serviceDateTime_${item.serviceId}" required />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="grand-total">
        Grand Total: $<c:out value="${grandTotal}" />
    </div>
    <button type="submit">Proceed to Checkout</button>
</form>


</body>
</html>
