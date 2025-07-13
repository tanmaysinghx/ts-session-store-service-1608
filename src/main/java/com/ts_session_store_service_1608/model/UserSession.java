package com.ts_session_store_service_1608.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sessionId;

    private String userEmail;

    private String refreshTokenHash;

    private String deviceInfo;

    private String ipAddress;

    private Instant createdAt;

    private Instant lastUsedAt;

    private Instant expiresAt;

    private boolean revoked;
}
