package com.spring.booking.service.impl;

import com.spring.booking.component.publisher.BookingEventPublisher;
import com.spring.booking.constant.enums.BookingStatus;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.BookingRequest;
import com.spring.booking.dto.response.BookingResponse;
import com.spring.booking.entity.Bookings;
import com.spring.booking.entity.Hotels;
import com.spring.booking.entity.Rooms;
import com.spring.booking.entity.User;
import com.spring.booking.exception.exceptions404.BookingNotFoundException;
import com.spring.booking.exception.exceptions404.HotelNotFoundException;
import com.spring.booking.exception.exceptions404.RoomNotFoundException;
import com.spring.booking.exception.exceptions404.UserNotFoundException;
import com.spring.booking.mapper.BookingMapper;
import com.spring.booking.repository.BookingRepository;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.UserRepository;
import com.spring.booking.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;
    UserRepository userRepository;
    HotelRepository hotelRepository;
    RoomRepository roomRepository;
    BookingMapper bookingMapper;
    BookingEventPublisher bookingEventPublisher;

    @Override
    @Transactional
    public BookingResponse create(BookingRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.userId()));

        Hotels hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));

        Rooms room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + request.roomId()));

        Bookings booking = bookingMapper.toEntity(request);

        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setStatus(BookingStatus.BOOKING_PENDING_PAYMENT);
        booking.setExpireDate(LocalDate.now().plusDays(2));


        Bookings saved = bookingRepository.save(booking);
        BookingResponse response = bookingMapper.toResponse(saved);

        bookingEventPublisher.sendBookingEvent(response);
        return response;
    }


    @Override
    @Transactional
    public BookingResponse update(UUID id, BookingRequest request) {

        Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

        BookingStatus oldStatus = booking.getStatus();

        bookingMapper.update(booking, request);

        if (request.userId() != null) {
            User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.userId()));
            booking.setUser(user);
        }

        if (request.hotelId() != null) {
            Hotels hotel = hotelRepository.findById(request.hotelId()).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));
            booking.setHotel(hotel);
        }

        if (request.roomId() != null) {
            Rooms room = roomRepository.findById(request.roomId()).orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + request.roomId()));
            booking.setRoom(room);
        }

        Bookings updated = bookingRepository.save(booking);

        BookingResponse response = bookingMapper.toResponse(updated);

        if (oldStatus != updated.getStatus()) {
            bookingEventPublisher.sendBookingEvent(response);
        }

        return response;
    }


    @Override
    @Transactional
    public void updateStatusAndPaymentId(UUID id, BookingStatus newStatus, String paymentId) {

        Bookings booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

        BookingStatus oldStatus = booking.getStatus();

        boolean changed = false;

        if (newStatus != null && oldStatus != newStatus) {
            booking.setStatus(newStatus);
            changed = true;
        }

        if (paymentId != null && !paymentId.isBlank()) {
            booking.setPaymentId(paymentId);
            changed = true;
        }

        if (!changed) {
            log.info("No changes for booking id={}", id);
            return;
        }

        Bookings bookings = bookingRepository.save(booking);
        BookingResponse response = bookingMapper.toResponse(bookings);
        bookingEventPublisher.sendBookingEvent(response);

        log.info("Booking updated. id={}, status={}, paymentId={}", id, booking.getStatus(), booking.getPaymentId());
    }

    @Override
    @Transactional
    public BookingResponse updateStatus(UUID id, BookingStatus newStatus) {

        Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));

        BookingStatus oldStatus = booking.getStatus();

        if (oldStatus == newStatus) {
            log.info("Booking status not changed. id={}, status={}", id, oldStatus);
            return bookingMapper.toResponse(booking);
        }

        booking.setStatus(newStatus);

        Bookings updated = bookingRepository.save(booking);
        BookingResponse response = bookingMapper.toResponse(updated);
        bookingEventPublisher.sendBookingEvent(response);
        return response;
    }


    @Override
    public BookingResponse get(UUID id) {
        Bookings booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public Page<BookingResponse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<Bookings> page = bookingRepository.findAllWithSearch(pageRequestDtoParams.getSearchText(), pageRequestDtoParams.getPageable());
        return page.map(bookingMapper::toResponse);
    }


    @Override
    @Transactional
    public void delete(UUID id) {
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<BookingResponse> getByUser(Long userId, PageRequestDto pageRequestDto) {
        Page<Bookings> page = bookingRepository.findByUserId(userId, pageRequestDto.getPageable());
        return page.map(bookingMapper::toResponse);
    }

    @Override
    public Page<BookingResponse> getByHotel(Long hotelId, PageRequestDto pageRequestDto) {
        Page<Bookings> page = bookingRepository.findByHotelId(hotelId, pageRequestDto.getPageable());
        return page.map(bookingMapper::toResponse);
    }

    @Override
    public Page<BookingResponse> getByStatus(BookingStatus status, PageRequestDto pageRequestDto) {
        Page<Bookings> page = bookingRepository.findByStatus(status, pageRequestDto.getPageable());
        return page.map(bookingMapper::toResponse);
    }
}
