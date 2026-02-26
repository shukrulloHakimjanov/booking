package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.ReviewRequest;
import com.spring.booking.dto.response.ReviewResponse;
import com.spring.booking.entity.Bookings;
import com.spring.booking.entity.Hotels;
import com.spring.booking.entity.Review;
import com.spring.booking.entity.User;
import com.spring.booking.exception.exceptions404.BookingNotFoundException;
import com.spring.booking.exception.exceptions404.HotelNotFoundException;
import com.spring.booking.exception.exceptions404.ReviewNotFoundException;
import com.spring.booking.exception.exceptions404.UserNotFoundException;
import com.spring.booking.mapper.ReviewMapper;
import com.spring.booking.repository.BookingRepository;
import com.spring.booking.repository.HotelRepository;
import com.spring.booking.repository.ReviewRepository;
import com.spring.booking.repository.UserRepository;
import com.spring.booking.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    BookingRepository bookingRepository;
    UserRepository userRepository;
    HotelRepository hotelRepository;
    ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse create(ReviewRequest request) {

        Bookings booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + request.bookingId()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.userId()));

        Hotels hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));

        Review review = reviewMapper.toEntity(request);
        review.setBooking(booking);
        review.setUser(user);
        review.setHotel(hotel);

        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ReviewResponse update(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        reviewMapper.update(review, request);

        if (request.bookingId() != null) {
            Bookings booking = bookingRepository.findById(request.bookingId())
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + request.bookingId()));
            review.setBooking(booking);
        }

        if (request.userId() != null) {
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.userId()));
            review.setUser(user);
        }

        if (request.hotelId() != null) {
            Hotels hotel = hotelRepository.findById(request.hotelId())
                    .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + request.hotelId()));
            review.setHotel(hotel);
        }

        Review updated = reviewRepository.save(review);
        return reviewMapper.toResponse(updated);
    }

    @Override
    public ReviewResponse get(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
        return reviewMapper.toResponse(review);
    }

    @Override
    public Page<ReviewResponse> getAll(PageRequestDto pageRequestDtoParams) {
        Page<Review> page = reviewRepository.findAll(pageRequestDtoParams.getPageable());
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public Page<ReviewResponse> getByHotelId(Long hotelId, PageRequestDto pageRequestDto) {
        Page<Review> page = reviewRepository.findByHotelId(hotelId, pageRequestDto.getPageable());
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getByUserId(Long userId, PageRequestDto pageRequestDto) {
        Page<Review> page = reviewRepository.findByUserId(userId, pageRequestDto.getPageable());
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getByBookingId(Long bookingId, PageRequestDto pageRequestDto) {
        Page<Review> page = reviewRepository.findByBookingId(bookingId, pageRequestDto.getPageable());
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getByMinRating(Integer minRating, PageRequestDto pageRequestDto) {
        Page<Review> page = reviewRepository.findByMinRating(minRating, pageRequestDto.getPageable());
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getByHotelIdAndMinRating(Long hotelId, Integer minRating, PageRequestDto pageRequestDto) {
        Page<Review> page = reviewRepository.findByHotelIdAndMinRating(hotelId, minRating, pageRequestDto.getPageable());
        return page.map(reviewMapper::toResponse);
    }

}
