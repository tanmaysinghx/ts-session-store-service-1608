package com.ts_session_store_service_1608.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSessionRequest {
    private String userEmail;
    private String refreshToken;
    private String deviceInfo;
    private String ipAddress;
    private Instant expiresAt;
}