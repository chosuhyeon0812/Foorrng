package com.d205.foorrng.user.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
//    USER,
//    OWNER
    USER("ROLE_USER", "USER"),
    OWNER("ROLE_OWNER", "OWNER");
//
    private final String key;
//
    private final String title;
}
