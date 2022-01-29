package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserMeal {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private static Map<LocalDate, Integer> datesAndSumOfCalories = new HashMap<>();

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        datesAndSumOfCalories.merge(dateTime.toLocalDate(), calories, Integer::sum);
    }

    public static Map<LocalDate, Integer> getMap() {
        return datesAndSumOfCalories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
