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
import com.spring.booking.mapper.BookingMapper;
import com.spring.booking.repository.BookingRepository;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Spy
    BookingMapper bookingMapper;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    HotelRepository hotelRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    BookingEventPublisher bookingEventPublisher;

    @InjectMocks
    BookingServiceImpl bookingService;

    @Captor
    ArgumentCaptor<Bookings> bookingCaptor;

    UUID bookingId;
    User user;
    Hotels hotel;
    Rooms room;
    Bookings booking;
    BookingRequest bookingRequest;
    BookingResponse bookingResponse;

    @BeforeEach
    void setUp() {
        bookingId = UUID.randomUUID();

        user = new User();
        user.setId(1L);
        user.setFirstName("John Doe");

        hotel = new Hotels();
        hotel.setId(1L);
        hotel.setName("Grand Hotel");

        room = new Rooms();
        room.setId(1L);

        booking = new Bookings();
        booking.setId(bookingId);
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setStatus(BookingStatus.BOOKING_PENDING_PAYMENT);
        booking.setCheckInDate(LocalDate.of(2025, 6, 1));
        booking.setCheckOutDate(LocalDate.of(2025, 6, 7));
        booking.setNumGuests(2);
        booking.setTotalPrice(BigDecimal.valueOf(1200));
        booking.setGuests(List.of());

        bookingRequest = new BookingRequest(
                1L,
                1L,
                1L,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 7),
                2,
                BigDecimal.valueOf(1200),
                "USD",
                BookingStatus.BOOKING_PENDING_PAYMENT,
                null,
                null,
                List.of()
        );

        bookingResponse = new BookingResponse(
                bookingId,
                null,
                null,
                null,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 7),
                2,
                BigDecimal.valueOf(1200),
                "USD",
                BookingStatus.BOOKING_PENDING_PAYMENT,
                null,
                null,
                List.of()
        );
    }


    @Test
    @DisplayName("create - should save booking and publish event")
    void create_ShouldSaveBookingAndReturnResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingMapper.toEntity(bookingRequest)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        BookingResponse result = bookingService.create(bookingRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookingResponse);

        verify(bookingRepository, times(1)).save(bookingCaptor.capture());
        Bookings captured = bookingCaptor.getValue();
        assertThat(captured.getUser()).isEqualTo(user);
        assertThat(captured.getHotel()).isEqualTo(hotel);
        assertThat(captured.getRoom()).isEqualTo(room);
        assertThat(captured.getStatus()).isEqualTo(BookingStatus.BOOKING_PENDING_PAYMENT);
        assertThat(captured.getExpireDate()).isEqualTo(LocalDate.now().plusDays(2));

        verify(bookingEventPublisher, times(1)).sendBookingEvent(bookingResponse);
    }

    @Test
    @DisplayName("get - should return booking response when booking exists")
    void get_ShouldReturnBookingResponse_WhenBookingExists() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        BookingResponse result = bookingService.get(bookingId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookingResponse);

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingMapper, times(1)).toResponse(bookingCaptor.capture());
        assertThat(bookingCaptor.getValue().getId()).isEqualTo(bookingId);
    }

    @Test
    @DisplayName("update - should update booking and return response")
    void update_ShouldReturnUpdatedBookingResponse_WhenBookingExists() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        BookingResponse result = bookingService.update(bookingId, bookingRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookingResponse);

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingMapper, times(1)).update(booking, bookingRequest);
        verify(bookingRepository, times(1)).save(bookingCaptor.capture());
        assertThat(bookingCaptor.getValue().getUser()).isEqualTo(user);
        assertThat(bookingCaptor.getValue().getHotel()).isEqualTo(hotel);
        assertThat(bookingCaptor.getValue().getRoom()).isEqualTo(room);
     }

    @Test
    @DisplayName("update - should publish event when status changes")
    void update_ShouldPublishEvent_WhenStatusChanges() {
        booking.setStatus(BookingStatus.BOOKING_PENDING_PAYMENT);

        BookingRequest requestWithNewStatus = new BookingRequest(
                1L, 1L, 1L,
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 7),
                2, BigDecimal.valueOf(1200), "USD",
                BookingStatus.BOOKING_CONFIRMED,
                null, null, List.of()
        );

        Bookings updatedBooking = new Bookings();
        updatedBooking.setId(bookingId);
        updatedBooking.setStatus(BookingStatus.BOOKING_CONFIRMED);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(updatedBooking);
        when(bookingMapper.toResponse(updatedBooking)).thenReturn(bookingResponse);

        doAnswer(inv -> {
            Bookings b = inv.getArgument(0);
            b.setStatus(BookingStatus.BOOKING_CONFIRMED);
            return null;
        }).when(bookingMapper).update(booking, requestWithNewStatus);

        bookingService.update(bookingId, requestWithNewStatus);

        verify(bookingEventPublisher, times(1)).sendBookingEvent(bookingResponse);
    }

    @Test
    @DisplayName("updateStatus - should update status and publish event when status changes")
    void updateStatus_ShouldUpdateStatusAndPublishEvent_WhenStatusChanges() {
        booking.setStatus(BookingStatus.BOOKING_PENDING_PAYMENT);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        BookingResponse result = bookingService.updateStatus(bookingId, BookingStatus.BOOKING_CONFIRMED);

        assertThat(result).isNotNull();
        assertThat(booking.getStatus()).isEqualTo(BookingStatus.BOOKING_CONFIRMED);

        verify(bookingRepository, times(1)).save(booking);
        verify(bookingEventPublisher, times(1)).sendBookingEvent(bookingResponse);
    }

    @Test
    @DisplayName("updateStatusAndPaymentId - should update both fields and publish event")
    void updateStatusAndPaymentId_ShouldUpdateAndPublishEvent() {
        booking.setStatus(BookingStatus.BOOKING_PENDING_PAYMENT);
        booking.setPaymentId(null);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        bookingService.updateStatusAndPaymentId(bookingId, BookingStatus.BOOKING_CONFIRMED, "PAY-123");

        assertThat(booking.getStatus()).isEqualTo(BookingStatus.BOOKING_CONFIRMED);
        assertThat(booking.getPaymentId()).isEqualTo("PAY-123");

        verify(bookingRepository, times(1)).save(booking);
        verify(bookingEventPublisher, times(1)).sendBookingEvent(bookingResponse);
    }



    @Test
    @DisplayName("getAll - should return page of booking responses")
    void getAll_ShouldReturnPageOfBookingResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Bookings> bookingPage = new PageImpl<>(List.of(booking), pageable, 1);

        PageRequestDto pageRequestDto = mock(PageRequestDto.class);
        when(pageRequestDto.getSearchText()).thenReturn("Grand");
        when(pageRequestDto.getPageable()).thenReturn(pageable);
        when(bookingRepository.findAllWithSearch("Grand", pageable)).thenReturn(bookingPage);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        Page<BookingResponse> result = bookingService.getAll(pageRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(bookingResponse);

        verify(bookingRepository, times(1)).findAllWithSearch("Grand", pageable);
    }


    @Test
    @DisplayName("delete - should delete booking when booking exists")
    void delete_ShouldDeleteBooking_WhenBookingExists() {
        when(bookingRepository.existsById(bookingId)).thenReturn(true);

        bookingService.delete(bookingId);

        verify(bookingRepository, times(1)).existsById(bookingId);
        verify(bookingRepository, times(1)).deleteById(bookingId);
        verifyNoInteractions(bookingMapper, bookingEventPublisher);
    }

    @Test
    @DisplayName("getByUser - should return page of booking responses for user")
    void getByUser_ShouldReturnPageOfBookingResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Bookings> bookingPage = new PageImpl<>(List.of(booking), pageable, 1);

        PageRequestDto pageRequestDto = mock(PageRequestDto.class);
        when(pageRequestDto.getPageable()).thenReturn(pageable);
        when(bookingRepository.findByUserId(1L, pageable)).thenReturn(bookingPage);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        Page<BookingResponse> result = bookingService.getByUser(1L, pageRequestDto);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(bookingResponse);
        verify(bookingRepository, times(1)).findByUserId(1L, pageable);
    }

    @Test
    @DisplayName("getByHotel - should return page of booking responses for hotel")
    void getByHotel_ShouldReturnPageOfBookingResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Bookings> bookingPage = new PageImpl<>(List.of(booking), pageable, 1);

        PageRequestDto pageRequestDto = mock(PageRequestDto.class);
        when(pageRequestDto.getPageable()).thenReturn(pageable);
        when(bookingRepository.findByHotelId(1L, pageable)).thenReturn(bookingPage);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        Page<BookingResponse> result = bookingService.getByHotel(1L, pageRequestDto);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(bookingResponse);
        verify(bookingRepository, times(1)).findByHotelId(1L, pageable);
    }

    @Test
    @DisplayName("getByStatus - should return page of booking responses for status")
    void getByStatus_ShouldReturnPageOfBookingResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Bookings> bookingPage = new PageImpl<>(List.of(booking), pageable, 1);

        PageRequestDto pageRequestDto = mock(PageRequestDto.class);
        when(pageRequestDto.getPageable()).thenReturn(pageable);
        when(bookingRepository.findByStatus(BookingStatus.BOOKING_PENDING_PAYMENT, pageable)).thenReturn(bookingPage);
        when(bookingMapper.toResponse(booking)).thenReturn(bookingResponse);

        Page<BookingResponse> result = bookingService.getByStatus(BookingStatus.BOOKING_PENDING_PAYMENT, pageRequestDto);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(bookingResponse);
        verify(bookingRepository, times(1)).findByStatus(BookingStatus.BOOKING_PENDING_PAYMENT, pageable);
    }
}