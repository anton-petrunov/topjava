package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private final String name;
    private final List<UserMeal> meals = new ArrayList<>();
    private final Map<LocalDate, Integer> datesAndSumsOfCalories = new HashMap<>();

    public User(String name) {
        this.name = name;
    }

    public void addMeal(UserMeal userMeal) {
        meals.add(userMeal);
        datesAndSumsOfCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
    }

    public List<UserMeal> getMeals() {
        return meals;
    }

    public Map<LocalDate, Integer> getDatesAndSumsOfCalories() {
        return datesAndSumsOfCalories;
    }

    public String getName() {
        return name;
    }
}
