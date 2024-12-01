package com.bijoy.events.hospital_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientChargeDTO {
    private String sso;
    private String test;
    private double cost;
}
