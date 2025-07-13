package com.ts_session_store_service_1608.jobs;

import com.ts_session_store_service_1608.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionCleanupJob {

    private final UserSessionRepository userSessionRepository;

    // Runs every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteDisabledSessions() {
        int count = userSessionRepository.deleteByEnabledFalse();
        log.info("Deleted {} disabled sessions.", count);
    }
}