package org.spring.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonDTO {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "age")
    private int age;
    @JsonProperty(value = "email")
    private String email;

    public PersonDTO() {
    }

    public PersonDTO(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
