package com.spring.booking.service.impl;

import com.spring.booking.constant.enums.Status;
import com.spring.booking.dto.pageRequest.PageRequestDto;
import com.spring.booking.dto.request.RoomRequest;
import com.spring.booking.dto.response.RoomResponse;
import com.spring.booking.entity.RoomTypes;
import com.spring.booking.entity.Rooms;
import com.spring.booking.exception.NotFoundException;
import com.spring.booking.mapper.RoomMapper;
import com.spring.booking.repository.RoomRepository;
import com.spring.booking.repository.RoomTypeRepository;
import com.spring.booking.service.RoomService;
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
public class RoomServiceImpl implements RoomService {

    RoomRepository roomRepository;
    RoomTypeRepository roomTypeRepository;
    RoomMapper roomMapper;

    @Override
    @Transactional
    public RoomResponse create(RoomRequest request) {
        log.info("Creating new room with room type id: {}", request.roomTypeId());

        RoomTypes roomType = roomTypeRepository.findById(request.roomTypeId()).orElseThrow(() -> new NotFoundException("Room type not found with id: " + request.roomTypeId()));

        Rooms room = roomMapper.toEntity(request);
        room.setRoomType(roomType);
        room.setStatus(Status.AVAILABLE);

        Rooms saved = roomRepository.save(room);
        log.info("Room created successfully with id: {}", saved.getId());

        return roomMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RoomResponse update(Long id, RoomRequest request) {
        log.info("Updating room with id: {}", id);

        Rooms room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

        roomMapper.update(room, request);

        if (request.roomTypeId() != null) {
            RoomTypes roomType = roomTypeRepository.findById(request.roomTypeId()).orElseThrow(() -> new NotFoundException("Room type not found with id: " + request.roomTypeId()));
            room.setRoomType(roomType);
        }

        Rooms updated = roomRepository.save(room);
        log.info("Room updated successfully with id: {}", updated.getId());

        return roomMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse get(Long id) {
        log.info("Fetching room with id: {}", id);

        Rooms room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

        return roomMapper.toResponse(room);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getAllWithSearch(PageRequestDto pageRequestDtoParams) {
        log.info("Searching rooms with text: {}, page: {}, size: {}", pageRequestDtoParams.getSearchText(), pageRequestDtoParams.getPage(), pageRequestDtoParams.getSize());
        Page<Rooms> page = roomRepository.findAllWithSearch(pageRequestDtoParams.getSearchText(), pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getByRoomTypeId(Long roomTypeId, PageRequestDto pageRequestDtoParams) {
        log.info("Fetching rooms by room type id: {}", roomTypeId);

        if (!roomTypeRepository.existsById(roomTypeId)) {
            throw new NotFoundException("Room type not found with id: " + roomTypeId);
        }

        Page<Rooms> page = roomRepository.findByRoomTypeId(roomTypeId, pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getByHotelId(Long hotelId, PageRequestDto pageRequestDtoParams) {
        log.info("Fetching rooms by hotel id: {}", hotelId);

        Page<Rooms> page = roomRepository.findByHotelId(hotelId, pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getByStatus(Status status, PageRequestDto pageRequestDtoParams) {
        log.info("Fetching rooms by status: {}", status);

        Page<Rooms> page = roomRepository.findByStatus(status, pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getByFloor(Integer floor, PageRequestDto pageRequestDtoParams) {
        log.info("Fetching rooms by floor: {}", floor);

        Page<Rooms> page = roomRepository.findByFloor(floor, pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomResponse> getByHotelIdAndStatus(Long hotelId, Status status, PageRequestDto pageRequestDtoParams) {
        log.info("Fetching rooms by hotel id: {} and status: {}", hotelId, status);

        Page<Rooms> page = roomRepository.findByHotelIdAndStatus(hotelId, status, pageRequestDtoParams.getPageable());
        return page.map(roomMapper::toResponse);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting room with id: {}", id);

        Rooms room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        roomRepository.delete(room);
        log.info("Room  deleted successfully with id: {}", id);
    }

    @Override
    @Transactional
    public RoomResponse updateStatus(Long id, Status status) {
        log.info("Updating room status with id: {} to status: {}", id, status);

        Rooms room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        room.setStatus(status);
        Rooms updated = roomRepository.save(room);

        log.info("Room status updated successfully. Room id: {}, new status: {}", id, status);

        return roomMapper.toResponse(updated);
    }
}