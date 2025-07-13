package com.ts_session_store_service_1608.controller;

import com.ts_session_store_service_1608.dto.CreateSessionRequest;
import com.ts_session_store_service_1608.dto.InvalidateSessionResponse;
import com.ts_session_store_service_1608.dto.SessionListResponse;
import com.ts_session_store_service_1608.dto.SessionResponse;
import com.ts_session_store_service_1608.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    /**
     * Create a new session after login
     */
    @PostMapping
    public ResponseEntity<SessionResponse> createSession(@RequestBody CreateSessionRequest request) {
        SessionResponse response = sessionService.createSession(request);
        return ResponseEntity.ok(response);
    }

    /**
     * List all active (non-revoked) sessions for a user
     * Assumes userId and currentSessionId are passed from token context or headers
     */
    @GetMapping
    public ResponseEntity<SessionListResponse> listSessions(
            @RequestParam String userId,
            @RequestParam UUID currentSessionId
    ) {
        SessionListResponse sessions = sessionService.listSessions(userId, currentSessionId);
        return ResponseEntity.ok(sessions);
    }

    /**
     * Invalidate a specific session by session ID
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<InvalidateSessionResponse> invalidateSession(
            @PathVariable UUID sessionId,
            @RequestParam String userId
    ) {
        InvalidateSessionResponse result = sessionService.invalidateSession(sessionId, userId);
        return ResponseEntity.ok(result);
    }

    /**
     * Invalidate all sessions except the current one
     */
    @DeleteMapping("/others")
    public ResponseEntity<Void> invalidateOtherSessions(
            @RequestParam String userId,
            @RequestParam UUID currentSessionId
    ) {
        sessionService.invalidateOtherSessions(userId, currentSessionId);
        return ResponseEntity.noContent().build();
    }
}
