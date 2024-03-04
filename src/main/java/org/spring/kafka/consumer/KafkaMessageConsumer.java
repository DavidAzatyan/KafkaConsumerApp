package org.spring.kafka.consumer;

import org.spring.kafka.dao.JsonDAO;
import org.spring.kafka.dao.PersonDAO;
import org.spring.kafka.dto.PersonDTO;
import org.spring.kafka.models.Json;
import org.spring.kafka.models.Person;
import org.spring.kafka.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaMessageConsumer {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final PersonDAO personDAO;
    private final JsonDAO jsonDAO;

    @Autowired
    public KafkaMessageConsumer(final PersonDAO personDAO, final JsonDAO jsonDAO) {
        this.personDAO = personDAO;
        this.jsonDAO = jsonDAO;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        LOGGER.log(Level.INFO, "Received message: " + message);
        if (!JsonUtil.isValid(message)) {
            LOGGER.log(Level.WARNING, "Error processing Kafka message, invalid Json");
            return;
        }
        Json json = new Json(JsonUtil.convertJsonStringToJsonNode(message));
        JsonUtil.parse(message, PersonDTO.class).ifPresentOrElse(personDTO -> {
            Person person = new Person(personDTO.getName(), personDTO.getAge(), personDTO.getEmail());
            jsonDAO.save(json);
            personDAO.save(person);
        }, () -> jsonDAO.save(json));
    }
}
