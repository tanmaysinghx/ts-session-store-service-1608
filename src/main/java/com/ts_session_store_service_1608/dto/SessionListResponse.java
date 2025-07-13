package com.ts_session_store_service_1608.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionListResponse {
    private List<SessionResponse> sessions;
}