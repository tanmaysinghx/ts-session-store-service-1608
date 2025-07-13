package com.ts_session_store_service_1608.service;

import com.ts_session_store_service_1608.dto.CreateSessionRequest;
import com.ts_session_store_service_1608.dto.InvalidateSessionResponse;
import com.ts_session_store_service_1608.dto.SessionListResponse;
import com.ts_session_store_service_1608.dto.SessionResponse;
import com.ts_session_store_service_1608.model.UserSession;
import com.ts_session_store_service_1608.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepository sessionRepository;

    // Create a new session
    public SessionResponse createSession(CreateSessionRequest request) {
        String hashedToken = BCrypt.hashpw(request.getRefreshToken(), BCrypt.gensalt());

        UserSession session = UserSession.builder()
                .userEmail(request.getUserEmail())
                .refreshTokenHash(hashedToken)
                .deviceInfo(request.getDeviceInfo())
                .ipAddress(request.getIpAddress())
                .createdAt(Instant.now())
                .lastUsedAt(Instant.now())
                .expiresAt(request.getExpiresAt())
                .revoked(false)
                .build();

        UserSession saved = sessionRepository.save(session);

        return toResponse(saved, true); // assume current session = newly created
    }

    // List active sessions for a user
    public SessionListResponse listSessions(String userId, UUID currentSessionId) {
        List<SessionResponse> sessions = sessionRepository.findByUserEmailAndRevokedFalse(userId).stream()
                .map(session -> toResponse(session, session.getSessionId().equals(currentSessionId)))
                .collect(Collectors.toList());

        return SessionListResponse.builder()
                .sessions(sessions)
                .build();
    }

    // Invalidate a session by ID
    public InvalidateSessionResponse invalidateSession(UUID sessionId, String userId) {
        return sessionRepository.findBySessionIdAndRevokedFalse(sessionId)
                .filter(session -> session.getUserEmail().equals(userId)) // ensure session belongs to user
                .map(session -> {
                    session.setRevoked(true);
                    sessionRepository.save(session);
                    return InvalidateSessionResponse.builder()
                            .success(true)
                            .message("Session invalidated successfully.")
                            .build();
                })
                .orElseGet(() -> InvalidateSessionResponse.builder()
                        .success(false)
                        .message("Session not found or already invalidated.")
                        .build());
    }

    // Invalidate all sessions except the current one
    public void invalidateOtherSessions(String userId, UUID currentSessionId) {
        List<UserSession> sessions = sessionRepository.findByUserEmailAndRevokedFalse(userId);
        for (UserSession session : sessions) {
            if (!session.getSessionId().equals(currentSessionId)) {
                session.setRevoked(true);
                sessionRepository.save(session);
            }
        }
    }

    private SessionResponse toResponse(UserSession session, boolean isCurrent) {
        return SessionResponse.builder()
                .sessionId(session.getSessionId())
                .deviceInfo(session.getDeviceInfo())
                .ipAddress(session.getIpAddress())
                .createdAt(session.getCreatedAt())
                .lastUsedAt(session.getLastUsedAt())
                .current(isCurrent)
                .build();
    }
}