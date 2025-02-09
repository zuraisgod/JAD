<nav style="background-color: #333; color: white; padding: 10px;">
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <div>
            <h2>Admin Panel</h2>
        </div>
        <div>
            <a href="<%= request.getContextPath() %>/admin/dashboard" style="color: white; margin-right: 15px;">Dashboard</a>
            <a href="<%= request.getContextPath() %>/admin/viewBookings" style="color: white; margin-right: 15px;">View Bookings</a>
            <a href="<%= request.getContextPath() %>/admin/members" style="color: white; margin-right: 15px;">Manage Members</a>
            <a href="<%= request.getContextPath() %>/admin/inquiries" style="color: white; margin-right: 15px;">Manage Inquiries</a>
            <a href="<%= request.getContextPath() %>/admin/reports" style="color: white; margin-right: 15px;">Manage Reports</a>
            <a href="<%= request.getContextPath() %>/logout" style="color: white; margin-right: 15px;">Logout</a>
        </div>
    </div>
</nav>
