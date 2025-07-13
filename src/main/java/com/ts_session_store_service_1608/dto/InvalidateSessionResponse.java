package com.ts_session_store_service_1608.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidateSessionResponse {
    private boolean success;
    private String message;
}
