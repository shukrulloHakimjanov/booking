package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.HotelFilterDto;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.HotelRequest;
import com.spring.booking.dto.response.HotelResponse;
import com.spring.booking.entity.*;
import com.spring.booking.mapper.HotelMapper;
import com.spring.booking.repository.*;
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

import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    HotelRepository hotelRepository;

    @Mock
    AttachmentRepository attachmentRepository;

    @Mock
    AmenityRepository amenityRepository;

    @Mock
    CitiesRepository citiesRepository;

    @Mock
    HotelMapper hotelMapper;

    @InjectMocks
    HotelServiceImpl hotelService;

    @Captor
    ArgumentCaptor<Hotels> hotelCaptor;

    HotelRequest request;
    Hotels hotel;
    City city;
    Amenities amenity;
    Attachments attachment;
    HotelResponse response;

    @BeforeEach
    void setUp() {
        request = new HotelRequest(
                1L,
                null,
                "Hotel 1",
                "Description",
                "Address",
                4.5f,
                null,
                null,
                Set.of(10L),
                Set.of(20L)
        );

        hotel = new Hotels();
        hotel.setId(1L);

        city = new City();
        city.setId(1L);

        amenity = new Amenities();
        amenity.setId(10L);

        attachment = new Attachments();
        attachment.setId(20L);

        response = new HotelResponse(
                1L, null, null, "Hotel 1", "Description",
                "Address", 4.5f, LocalTime.of(14, 0), LocalTime.of(12, 0), null, null
        );
    }
    @Test
    @DisplayName("create - should set city, attachments, amenities and save hotel")
    void create_ShouldSetCityAttachmentsAmenitiesAndSave() {
        when(hotelMapper.toEntity(request)).thenReturn(hotel);
        when(citiesRepository.findById(request.cityId())).thenReturn(Optional.of(city));
        when(amenityRepository.findAllById(request.amenitiesIds())).thenReturn(List.of(amenity));
        when(attachmentRepository.findAllById(request.attachmentIds())).thenReturn(List.of(attachment));
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(hotelMapper.toResponse(hotel)).thenReturn(response);

        HotelResponse result = hotelService.create(request);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(response);

        verify(hotelRepository, times(1)).save(hotelCaptor.capture());
        Hotels captured = hotelCaptor.getValue();
        assertThat(captured.getCity()).isEqualTo(city);
        assertThat(captured.getAmenities()).containsExactly(amenity);
        assertThat(captured.getAttachments()).containsExactly(attachment);

        verify(citiesRepository, times(1)).findById(request.cityId());
        verify(amenityRepository, times(1)).findAllById(request.amenitiesIds());
        verify(attachmentRepository, times(1)).findAllById(request.attachmentIds());
    }
    @Test
    @DisplayName("get - should return hotel response when hotel exists")
    void get_ShouldReturnHotelResponse_WhenHotelExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toResponse(hotel)).thenReturn(response);

        HotelResponse result = hotelService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(response);

        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelMapper, times(1)).toResponse(hotelCaptor.capture());
        assertThat(hotelCaptor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getAll - should return page of hotel responses with filter params")
    void getAll_ShouldReturnPageOfHotelResponses_WithFilterParams() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hotels> hotelPage = new PageImpl<>(List.of(hotel), pageable, 1);

        HotelFilterDto filterDto = mock(HotelFilterDto.class);
        when(filterDto.getSearchText()).thenReturn("Hotel");
        when(filterDto.getCityId()).thenReturn(1L);
        when(filterDto.getOwnerId()).thenReturn(null);
        when(filterDto.getMinRating()).thenReturn(4.0f);
        when(filterDto.getAmenityId()).thenReturn(10L);
        when(filterDto.getIsActive()).thenReturn(true);
        when(filterDto.getPageable()).thenReturn(pageable);

        when(hotelRepository.searchHotels("Hotel", 1L, null, 4.0f, 10L, true, pageable)).thenReturn(hotelPage);
        when(hotelMapper.toResponse(hotel)).thenReturn(response);

        Page<HotelResponse> result = hotelService.getAll(filterDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(response);

        verify(hotelRepository, times(1)).searchHotels("Hotel", 1L, null, 4.0f, 10L, true, pageable);
        verify(hotelMapper, times(1)).toResponse(hotel);
    }

    @Test
    @DisplayName("delete - should delete hotel when hotel exists")
    void delete_ShouldDeleteHotel_WhenHotelExists() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        hotelService.delete(1L);

        verify(hotelRepository, times(1)).existsById(1L);
        verify(hotelRepository, times(1)).deleteById(1L);
        verifyNoInteractions(hotelMapper);
    }
}