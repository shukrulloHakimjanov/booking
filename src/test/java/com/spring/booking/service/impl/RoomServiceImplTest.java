package com.spring.booking.service.impl;

import com.spring.booking.constant.enums.AccommodationType;
import com.spring.booking.constant.enums.Status;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomRequest;
import com.spring.booking.dto.response.RoomResponse;
import com.spring.booking.entity.RoomTypes;
import com.spring.booking.entity.Rooms;
import com.spring.booking.mapper.RoomMapper;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.RoomTypeRepository;
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
class RoomServiceImplTest {

    @Spy
    RoomMapper roomMapper;

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomTypeRepository roomTypeRepository;

    @InjectMocks
    RoomServiceImpl roomServiceImpl;

    @Captor
    ArgumentCaptor<Rooms> captor;

    RoomTypes roomTypes;
    Rooms rooms;
    RoomResponse roomResponse;
    RoomRequest roomRequest;
    Long roomId;

    @BeforeEach
    void setUp() {
        roomId = 1L;

        roomTypes = new RoomTypes();
        roomTypes.setId(1L);
        roomTypes.setName("Room Type");
        roomTypes.setDescription("Room Description");


        rooms = new Rooms();
        rooms.setId(roomId);
        rooms.setRoomType(roomTypes);
        rooms.setRoomNumber("101");
        rooms.setFloor(1);
        rooms.setStatus(Status.AVAILABLE);
        rooms.setAccommodationType(AccommodationType.HOTEL);

        roomResponse = new RoomResponse(
                1L,
                1L,
                "101",
                1,
                Status.AVAILABLE,
                AccommodationType.HOTEL
        );
        roomRequest = new RoomRequest(
                1L,
                "101",
                1,
                Status.AVAILABLE,
                AccommodationType.HOTEL
        );
    }

    @Test
    @DisplayName("create - should save room return response")
    void create_ShouldSaveRoomAndReturnRoomResponse() {
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomTypes));
        when(roomMapper.toEntity(roomRequest)).thenReturn(rooms);
        when(roomRepository.save(rooms)).thenReturn(rooms);
        when(roomMapper.toResponse(rooms)).thenReturn(roomResponse);

        RoomResponse roomResponse = roomServiceImpl.create(roomRequest);

        assertThat(roomResponse).isNotNull();
        assertThat(roomResponse).isEqualTo(roomResponse);

        verify(roomRepository, times(1)).save(captor.capture());
        Rooms captured = captor.getValue();
        assertThat(captured.getRoomType()).isEqualTo(roomTypes);
        assertThat(captured.getRoomNumber()).isEqualTo(rooms.getRoomNumber());
        assertThat(captured.getStatus()).isEqualTo(rooms.getStatus());
    }

    @Test
    @DisplayName("update - should update room and return response")
    void update_ShouldReturnUpdatedRoomResponse_WhenRoomExists() {

        long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(rooms));
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomTypes));
        doNothing().when(roomMapper).update(rooms, roomRequest);
        when(roomRepository.save(rooms)).thenReturn(rooms);
        when(roomMapper.toResponse(rooms)).thenReturn(roomResponse);

        RoomResponse result = roomServiceImpl.update(roomId, roomRequest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(roomResponse);

        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, times(1)).update(rooms, roomRequest);
        verify(roomRepository, times(1)).save(captor.capture());

        Rooms captured = captor.getValue();
        assertThat(captured).isEqualTo(rooms);
        assertThat(captured.getRoomType()).isEqualTo(roomTypes);
        assertThat(captured.getRoomNumber()).isEqualTo(rooms.getRoomNumber());
    }

    @Test
    @DisplayName("get - should return room response when room exists")
    void get_ShouldReturnRoomResponse_WhenRoomExists() {

        long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(rooms));
        when(roomMapper.toResponse(rooms)).thenReturn(roomResponse);

        RoomResponse result = roomServiceImpl.get(roomId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(roomResponse);

        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, times(1)).toResponse(captor.capture());

        Rooms captured = captor.getValue();
        assertThat(captured).isEqualTo(rooms);
    }
    @Test
    @DisplayName("getAllWithSearch - should return paged rooms based on search text")
    void getAllWithSearch() {

        // given
        PageRequestDto pageRequestDto = new PageRequestDto();
        pageRequestDto.setSearchText("Room");
        pageRequestDto.setPage(1);
        pageRequestDto.setSize(10);

        Pageable pageable = pageRequestDto.getPageable();

        Page<Rooms> roomsPage = new PageImpl<>(List.of(rooms), pageable, 1);

        when(roomRepository.findAllWithSearch("Room", pageable)).thenReturn(roomsPage);

        when(roomMapper.toResponse(rooms)).thenReturn(roomResponse);

        Page<RoomResponse> result = roomServiceImpl.getAllWithSearch(pageRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).containsExactly(roomResponse);

        verify(roomRepository, times(1)).findAllWithSearch("Room", pageable);
        verify(roomMapper, times(1)).toResponse(rooms);
    }
    @Test
    @DisplayName("getByRoomTypeId - should return paged rooms for given room type")
    void getByRoomTypeId_ShouldReturnPagedRooms_WhenRoomTypeExists() {
        long roomTypeId = 1L;

        PageRequestDto pageRequestDto = new PageRequestDto();
        pageRequestDto.setPage(1);
        pageRequestDto.setSize(10);

        Pageable pageable = pageRequestDto.getPageable();

        when(roomTypeRepository.existsById(roomTypeId)).thenReturn(true);

        Page<Rooms> roomsPage = new PageImpl<>(List.of(rooms), pageable, 1);
        when(roomRepository.findByRoomTypeId(roomTypeId, pageable)).thenReturn(roomsPage);
        when(roomMapper.toResponse(rooms)).thenReturn(roomResponse);

        Page<RoomResponse> result = roomServiceImpl.getByRoomTypeId(roomTypeId, pageRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).containsExactly(roomResponse);

        verify(roomTypeRepository, times(1)).existsById(roomTypeId);
        verify(roomRepository, times(1)).findByRoomTypeId(roomTypeId, pageable);
        verify(roomMapper, times(1)).toResponse(rooms);
    }

    @Test
    @DisplayName("delete - should delete room when room exists")
    void delete_ShouldDeleteRoom_WhenRoomExists() {

        long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(rooms));

        roomServiceImpl.deleteById(roomId);

        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).delete(rooms);

        verifyNoInteractions(roomMapper);
    }
}