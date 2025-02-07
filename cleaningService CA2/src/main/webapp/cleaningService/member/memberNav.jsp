<nav class="navbar">
    <div class="container">
        <a href="${pageContext.request.contextPath}/cleaningService/member/memberIndex.jsp" class="nav-logo">Cleaning Services</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/cleaningService/member/memberIndex.jsp">Home</a></li>
            <li><a href="<%= request.getContextPath() %>/cartServlet">View Cart</a></li>
            <li><a href="<%= request.getContextPath() %>/ProfileServlet">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/cleaningService/logoutServlet">Logout</a></li>
        </ul>
    </div>
</nav>

<style>
    .navbar {
        background-color: #343a40;
        padding: 15px;
        color: #fff;
    }
    .nav-logo {
        font-size: 1.5rem;
        color: #fff;
        text-decoration: none;
        font-weight: bold;
    }
    .nav-links {
        list-style: none;
        display: flex;
        gap: 15px;
    }
    .nav-links li {
        display: inline;
    }
    .nav-links a {
        color: #fff;
        text-decoration: none;
        font-size: 1rem;
    }
    .nav-links a:hover {
        text-decoration: underline;
    }
</style>
