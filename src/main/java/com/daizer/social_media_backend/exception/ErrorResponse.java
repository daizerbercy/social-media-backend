package com.daizer.social_media_backend.exception;

public record ErrorResponse(String timestamps,
                            int status,
                            String error,
                            String message,
                            String path) {
}
