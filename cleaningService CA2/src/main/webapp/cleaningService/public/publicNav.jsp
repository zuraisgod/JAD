<nav class="navbar">
    <div class="container">
        <a href="${pageContext.request.contextPath}/cleaningService/public/publicIndex.jsp" class="nav-logo">Cleaning Services</a>
        <ul class="nav-links">
<a href="<%=request.getContextPath()%>/public/dashboard">Home</a>
            <li><a href="${pageContext.request.contextPath}/cleaningService/login.jsp">Login</a></li>
            <li><a href="${pageContext.request.contextPath}/cleaningService/register.jsp">Register</a></li>
        </ul>
    </div>
</nav>

<style>
    .navbar {
        background-color: #343a40;
        padding: 15px;
        color: #fff;
    }
    .container {
        display: flex;
        justify-content: space-between;
        align-items: center;
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
