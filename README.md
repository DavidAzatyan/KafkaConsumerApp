# Kafka Consumer App

This Kafka Consumer App is a simple Spring-based application that consumes JSON messages from a Kafka producer and
stores them in a PostgreSQL database.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
    - [1. PostgreSQL Configuration](#1-postgresql-configuration)
    - [2. Kafka Configuration](#2-kafka-configuration)
- [Usage](#usage)

## Features

- Consumes JSON messages from a Kafka topic.
- Persists the received messages to a PostgreSQL database.
- Built using the Spring framework for simplicity and flexibility.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 8 or later
- Apache Kafka
- PostgreSQL

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/KafkaConsumerApp.git

## Configuration

1. PostgreSQL Configuration

   1. Create a PostgreSQL database for the application:

      ``` 
       CREATE DATABASE kafka_consumer_db;
      ```

   2. Provide Database Details into hibernate.properties

      ```
      #config datasource
       hibernate.driver_class=org.postgresql.Driver
       hibernate.connection.url=jdbc:postgresql://localhost:5432/kafka_consumer_db
       hibernate.connection.username=postgres
       hibernate.connection.password=***
      ```

      SchemaExportConfig creates related tables 


2. Kafka Configuration

   1. Start the ZooKeeper service
      ```
      $ bin/zookeeper-server-start.sh config/zookeeper.properties
      ```
   2. Get the docker image and Start the kafka docker container
        ```
        $ docker pull apache/kafka:3.7.0
        $ docker run -p 9092:9092 apache/kafka:3.7.0
        ```
   3. Start the Kafka Server
   
      ```
      $ bin/kafka-server-start.sh config/kraft/server.properties
      ```
   4. Create a topic to store your events
      
      ```
      $ bin/kafka-topics.sh --create --topic json-messages --bootstrap-server localhost:9092
      ```
   5. Write some events into the topic
   
      ```
      bin/kafka-console-producer.sh --broker-list localhost:9092 
      --topic json-messages < file.json
      ```

   6. Provide kafka details into kafka.properties

      ```
      spring.kafka.bootstrap-servers=localhost:9092
      spring.kafka.consumer.group-id=json-group
      spring.kafka.consumer.topic=json-messages
      ```
   After configuration run KafkaConsumerApp

## Usage

1. **JSON Messages:**
   - When a sample JSON message is sent, it will be saved in the `json` table.

2. **Structured JSON Data:**
   - If a JSON message is sent in the following format:
     ```json
     {"name": "David", "age": 28, "email": "email@gmail.com"}
     ```
     It will be concurrently saved in the both `json` and `person` tables.

3. **Handling Invalid Messages:**
   - If a message is not in JSON format, the application will respond with the following error message:
     ```
     Error processing Kafka message, invalid JSON
     ```
