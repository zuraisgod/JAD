package com.cleaningservice.dbaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingDAO bookingDAO;

    public boolean completeBooking(String userId, int serviceId, String dateTime) {
        return bookingDAO.completeBooking(userId, serviceId, dateTime);
    }
}
