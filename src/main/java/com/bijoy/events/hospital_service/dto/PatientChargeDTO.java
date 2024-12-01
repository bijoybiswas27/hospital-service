package com.bijoy.events.hospital_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientChargeDTO {
    private String email;
    private String test;
    private double cost;
}
