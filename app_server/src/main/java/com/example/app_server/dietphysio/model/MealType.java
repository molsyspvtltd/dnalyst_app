package com.example.app_server.dietphysio.model;

public enum MealType {
    MORNING, BREAKFAST, LUNCH, EVENING, DINNER, BEDTIME, UNKNOWN;

    // Handle case-insensitive input and replace "-" with "_"
    public static MealType fromString(String value) {
        try {
            // Sanitize the input to handle cases like BED-TIME
            return MealType.valueOf(value.replace("-", "").toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid value, you can return null or throw a custom exception
            return null; // or throw new InvalidMealTypeException("Invalid MealType: " + value);
        }
    }
}
