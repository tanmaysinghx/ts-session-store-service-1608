package com.ts_session_store_service_1608.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponse {
    private UUID sessionId;
    private String deviceInfo;
    private String ipAddress;
    private Instant createdAt;
    private Instant lastUsedAt;
    private boolean current;
}