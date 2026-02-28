package com.spring.booking.service.impl;

import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.AmenityRequest;
import com.spring.booking.dto.response.AmenityResponse;
import com.spring.booking.entity.Amenities;
import com.spring.booking.mapper.AmenityMapper;
import com.spring.booking.repository.AmenityRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmenityServiceImplTest {

    @Spy
    AmenityMapper amenityMapper;

    @Mock
    AmenityRepository amenityRepository;

    @InjectMocks
    AmenityServiceImpl amenityService;

    @Captor
    ArgumentCaptor<Amenities> amenityCaptor;

    Amenities amenity;
    AmenityRequest amenityRequest;
    AmenityResponse amenityResponse;

    @BeforeEach
    void setUp() {
        amenity = new Amenities();
        amenity.setId(1L);
        amenity.setName("WiFi");
        amenity.setCategory("Internet");

        amenityRequest = new AmenityRequest("WiFi", "Internet");

        amenityResponse = new AmenityResponse(1L, "WiFi", "Internet");
    }

    @Test
    @DisplayName("create - should save amenity and return response")
    void create_ShouldSaveAmenityAndReturnResponse() {
        when(amenityMapper.fromDto(amenityRequest)).thenReturn(amenity);
        when(amenityRepository.save(amenity)).thenReturn(amenity);
        when(amenityMapper.toDto(amenity)).thenReturn(amenityResponse);

        AmenityResponse result = amenityService.create(amenityRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(amenityResponse);

        verify(amenityRepository, times(1)).save(amenityCaptor.capture());
        assertThat(amenityCaptor.getValue().getName()).isEqualTo("WiFi");
        verify(amenityMapper, times(1)).toDto(amenity);
    }

    @Test
    @DisplayName("get - should return amenity response when amenity exists")
    void get_ShouldReturnAmenityResponse_WhenAmenityExists() {
        when(amenityRepository.findById(1L)).thenReturn(Optional.of(amenity));
        when(amenityMapper.toDto(amenity)).thenReturn(amenityResponse);

        AmenityResponse result = amenityService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(amenityResponse);

        verify(amenityRepository, times(1)).findById(1L);
        verify(amenityMapper, times(1)).toDto(amenityCaptor.capture());
        assertThat(amenityCaptor.getValue().getName()).isEqualTo("WiFi");
    }


    @Test
    @DisplayName("update - should update amenity and return response when name is different")
    void update_ShouldReturnUpdatedAmenityResponse_WhenNameIsDifferent() {
        AmenityRequest updateRequest = new AmenityRequest("Pool", "Recreation");
        AmenityResponse updatedResponse = new AmenityResponse(1L, "Pool", "Recreation");

        when(amenityRepository.findById(1L)).thenReturn(Optional.of(amenity));
        when(amenityRepository.save(amenity)).thenReturn(amenity);
        when(amenityMapper.toDto(amenity)).thenReturn(updatedResponse);

        AmenityResponse result = amenityService.update(1L, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(updatedResponse);

        verify(amenityRepository, times(1)).findById(1L);
        verify(amenityMapper, times(1)).update(amenity, updateRequest);
        verify(amenityRepository, times(1)).save(amenityCaptor.capture());
        assertThat(amenityCaptor.getValue().getName()).isEqualTo("WiFi");
        verify(amenityMapper, times(1)).toDto(amenity);
    }

    @Test
    @DisplayName("getAll - should return page of amenity responses")
    void getAll_ShouldReturnPageOfAmenityResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Amenities> amenityPage = new PageImpl<>(List.of(amenity), pageable, 1);

        PageRequestDto pageRequestDto = mock(PageRequestDto.class);
        when(pageRequestDto.getSearchText()).thenReturn("WiFi");
        when(pageRequestDto.getPageable()).thenReturn(pageable);
        when(amenityRepository.findAllWithSearch("WiFi", pageable)).thenReturn(amenityPage);
        when(amenityMapper.toDto(amenity)).thenReturn(amenityResponse);

        Page<AmenityResponse> result = amenityService.getAll(pageRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst()).isEqualTo(amenityResponse);

        verify(amenityRepository, times(1)).findAllWithSearch("WiFi", pageable);
        verify(amenityMapper, times(1)).toDto(amenity);
    }

    @Test
    @DisplayName("delete - should delete amenity when amenity exists")
    void delete_ShouldDeleteAmenity_WhenAmenityExists() {
        when(amenityRepository.existsById(1L)).thenReturn(true);

        amenityService.delete(1L);

        verify(amenityRepository, times(1)).existsById(1L);
        verify(amenityRepository, times(1)).deleteById(1L);
        verifyNoInteractions(amenityMapper);
    }

}