package com.spring.booking.utility;

import com.spring.booking.component.publisher.BookingEventPublisher;
import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.response.BookingResponse;
import com.spring.booking.entity.Bookings;
import com.spring.booking.mapper.BookingMapper;
import com.spring.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingExpirationUtility {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingEventPublisher bookingEventPublisher;

    @Scheduled(fixedRate = 600000)
    @Transactional
    public void expireBookings() {

        List<Bookings> expiredBookings =
                bookingRepository.findByStatusAndExpireDateBefore(
                        BookingStatus.BOOKING_PENDING_PAYMENT,
                        LocalDate.now()
                );

        for (Bookings booking : expiredBookings) {

            booking.setStatus(BookingStatus.BOOKING_EXPIRED);
            bookingRepository.save(booking);

            BookingResponse response = bookingMapper.toResponse(booking);
            bookingEventPublisher.sendBookingEvent(response);

            log.info("Booking expired automatically. id={}", booking.getId());
        }
    }
}