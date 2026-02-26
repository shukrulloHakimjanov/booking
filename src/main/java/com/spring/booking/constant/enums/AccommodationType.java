package com.spring.booking.constant.enums;

import lombok.Getter;

@Getter
public enum AccommodationType {

    HOTEL("Hotel"),
    HOSTEL("Hostel"),
    MOTEL("Motel"),
    RESORT("Resort"),
    GUEST_HOUSE("Guest House"),
    BED_AND_BREAKFAST("Bed & Breakfast"),
    APARTMENT("Apartment"),
    SERVICED_APARTMENT("Serviced Apartment"),
    VILLA("Villa"),
    HOLIDAY_HOME("Holiday Home"),
    LODGE("Lodge"),
    CHALET("Chalet"),
    CAPSULE_HOTEL("Capsule Hotel"),
    ECO_LODGE("Eco Lodge"),
    FARM_STAY("Farm Stay"),
    RYOKAN("Ryokan");

    private final String label;

    AccommodationType(String label) {
        this.label = label;
    }

}
