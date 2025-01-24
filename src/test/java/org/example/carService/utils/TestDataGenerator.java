package org.example.carService.utils;

import com.github.javafaker.Faker;
import lombok.Data;

import java.util.List;
import java.util.Random;

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

    public static String generateCarNumber() {
        String letters1 = faker.regexify("[A-Z]{2}");
        String numbers = faker.number().digits(4);
        String letters2 = faker.regexify("[A-Z]{2}");
        return String.format("%s%s%s", letters1, numbers, letters2);
    }

    public static String generateVinCode() {
        String VIN_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        int VIN_LENGTH = 17;
        StringBuilder vin = new StringBuilder(VIN_LENGTH);
        Random random = new Random();

        for (int i = 0; i < VIN_LENGTH; i++) {
            int index = random.nextInt(VIN_CHARS.length());
            vin.append(VIN_CHARS.charAt(index));
        }

        return vin.toString();
    }

    public static String generateCarBrand() {
        List<String> brands = List.of(
                "Toyota", "BMW", "Mercedes-Benz", "Volkswagen",
                "Ford", "Honda", "Nissan", "Hyundai",
                "Chevrolet", "Kia", "Audi", "Porsche",
                "Lexus", "Mazda", "Tesla", "Subaru",
                "Jaguar", "Land Rover", "Volvo", "Mitsubishi"
        );
        Random random = new Random();
        return brands.get(random.nextInt(brands.size()));
    }

    public static String generateCarModel() {
        List<String> models = List.of(
                "Corolla", "Civic", "Mustang", "Camaro", "RAV4",
                "X5", "A4", "911", "Model 3", "Outback"
        );
        Random random = new Random();
        return models.get(random.nextInt(models.size()));
    }


}
