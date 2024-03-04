package org.spring.kafka.conf;

import org.spring.kafka.models.Json;
import org.spring.kafka.models.Person;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:hibernate.properties")
public class SchemaExportConfig {

    private final Environment env;

    @Autowired
    public SchemaExportConfig(Environment env) {
        this.env = env;
    }

    private Map<String, String> getSettings() {
        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.driver_class", env.getProperty("hibernate.driver_class"));
        settings.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        settings.put("hibernate.connection.url", env.getProperty("hibernate.connection.url"));
        settings.put("hibernate.connection.username", env.getProperty("hibernate.connection.username"));
        settings.put("hibernate.connection.password", env.getProperty("hibernate.connection.password"));
        settings.put("hibernate.hbm2ddl.auto", "update");
        return settings;
    }

    @Bean
    public SchemaExport schemaExport() {

        Map<String, String> settings = getSettings();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Json.class)
                .buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.createOnly(EnumSet.of(TargetType.DATABASE), metadata);

        return schemaExport;
    }

}
