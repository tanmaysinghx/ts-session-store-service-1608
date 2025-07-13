package com.ts_session_store_service_1608.repository;

import com.ts_session_store_service_1608.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    List<UserSession> findByUserEmail(String userId);

    Optional<UserSession> findBySessionIdAndRevokedFalse(UUID sessionId);

    List<UserSession> findByUserEmailAndRevokedFalse(String userEmail);
}
