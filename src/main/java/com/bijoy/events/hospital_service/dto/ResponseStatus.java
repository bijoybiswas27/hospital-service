package com.bijoy.events.hospital_service.dto;

public enum ResponseStatus {
    SUCCEEDED("succeeded"),
    FAILED("failed");
    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
