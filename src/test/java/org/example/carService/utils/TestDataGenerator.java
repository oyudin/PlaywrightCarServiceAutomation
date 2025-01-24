package org.example.carService.utils;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generatePhoneNumber() {
        return faker.numerify("###########");
    }
}
