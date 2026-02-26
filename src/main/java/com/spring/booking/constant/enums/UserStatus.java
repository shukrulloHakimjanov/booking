package com.spring.booking.constant.enums;

public enum UserStatus {
        PENDING_VERIFICATION,   // user just signed up, must confirm email
        ACTIVE,                 // fully verified, can use all app features
        SUSPENDED,              // temporarily disabled (e.g. policy violation)
        DEACTIVATED,            // user deleted/deactivated their account
        BANNED,                 // permanently banned by admin
        PASSWORD_RESET_PENDING
}
