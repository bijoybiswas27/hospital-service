package com.bijoy.events.hospital_service.publisher;

import com.bijoy.events.hospital_service.dto.PatientChargeDTO;
import com.bijoy.events.hospital_service.dto.PatientCreationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    private RabbitTemplate rabbitTemplate;
    @Value("${patient.creation.binding}")
    String patientCreationKey;
    @Value("${patient.charge.binding}")
    String patientChargeKey;
    @Value("${patient.exchange.name}")
    String exchangeName;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPatientCreationMessage(PatientCreationDTO patientCreation) {
        LOGGER.info("Sending patient details to rabbitMQ for patient: {}", patientCreation.getSso());
        rabbitTemplate.convertAndSend(exchangeName, patientCreationKey, patientCreation);
    }

    public void sendPatientChargeMessage(PatientChargeDTO patientCharge) {
        LOGGER.info("Sending charge details to rabbitMQ for patient: {}", patientCharge.getSso());
        rabbitTemplate.convertAndSend(exchangeName, patientChargeKey, patientCharge);
    }
}
