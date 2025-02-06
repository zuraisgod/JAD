<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member Dashboard - Cleaning Services</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <%@ include file="memberNav.jsp" %>   
    <div class="container">
        <h1>Explore Our Cleaning Services</h1>
        <div class="category-grid">
            <% 
                List<Map<String, Object>> categories = (List<Map<String, Object>>) request.getAttribute("categories");
                if (categories != null) {
                    for (Map<String, Object> category : categories) {
                        String categoryName = (String) category.get("categoryName");
                        String description = (String) category.get("description");
                        String imagePath = (String) category.get("imagePath");
                        int categoryId = (int) category.get("categoryId");
            %>
            <div class="category-card">
                <img src="<%= request.getContextPath() %><%= imagePath %>" alt="<%= categoryName %>">
                <div class="card-content">
                    <h3><%= categoryName %></h3>
                    <p><%= description %></p>
                    <button class="view-button" 
        onclick="location.href='<%= request.getContextPath() %>/cleaningService/MemberServiceDetails?categoryId=<%= categoryId %>'">
    View All
</button>


                </div>
            </div>
            <% 
                    }
                } else {
            %>
            <p>No services available at the moment.</p>
            <% 
                }
            %>
        </div>
    </div>
</body>
</html>
