package com.ts_session_store_service_1608.repository;

import com.ts_session_store_service_1608.model.UserSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    List<UserSession> findByUserEmail(String userId);

    Optional<UserSession> findBySessionIdAndRevokedFalse(UUID sessionId);

    List<UserSession> findByUserEmailAndRevokedFalse(String userEmail);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserSession s WHERE s.revoked = true")
    int deleteByEnabledFalse();
}
