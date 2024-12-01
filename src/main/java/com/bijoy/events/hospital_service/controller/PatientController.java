package com.bijoy.events.hospital_service.controller;

import com.bijoy.events.hospital_service.dto.MessageResponseDTO;
import com.bijoy.events.hospital_service.dto.PatientChargeDTO;
import com.bijoy.events.hospital_service.dto.PatientCreationDTO;
import com.bijoy.events.hospital_service.dto.ResponseStatus;
import com.bijoy.events.hospital_service.publisher.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/patients")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private MessageProducer patientMessageProducer;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createPatient(@RequestBody PatientCreationDTO patientCreationDTO) {
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        try {
            patientMessageProducer.sendPatientCreationMessage(patientCreationDTO);
            responseDTO.setError(null);
            responseDTO.setResponseStatus(ResponseStatus.SUCCEEDED.getStatus());
            responseDTO.setMessage("Details send to rabbitMQ.");
        } catch (Exception ex) {
            LOGGER.error("Error occurred in sending the patient details to rabbitMQ: {}", ex.getMessage());
            responseDTO.setError(ex.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILED.getStatus());
            responseDTO.setMessage("Details couldn't be send to rabbitMQ.");
        }
        if (responseDTO.getResponseStatus().equals(ResponseStatus.SUCCEEDED.getStatus())) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @PostMapping("/{email}")
    public ResponseEntity<MessageResponseDTO> chargePatient(
            @RequestBody PatientChargeDTO patientChargeDTO, @PathVariable String email) {
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        patientChargeDTO.setEmail(email);
        try {
            patientMessageProducer.sendPatientChargeMessage(patientChargeDTO);
            responseDTO.setError(null);
            responseDTO.setResponseStatus(ResponseStatus.SUCCEEDED.getStatus());
            responseDTO.setMessage("Details send to rabbitMQ.");
        } catch (Exception ex) {
            LOGGER.error("Error occurred in sending the charge details to rabbitMQ: {}", ex.getMessage());
            responseDTO.setError(ex.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILED.getStatus());
            responseDTO.setMessage("Details couldn't be send to rabbitMQ.");
        }
        if (responseDTO.getResponseStatus().equals(ResponseStatus.SUCCEEDED.getStatus())) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

}
