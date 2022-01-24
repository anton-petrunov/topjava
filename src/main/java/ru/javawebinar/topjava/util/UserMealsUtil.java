package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> datesAndCalories = new HashMap<>();
        for (UserMeal currentMeal : meals) {
            LocalDate currentDate = currentMeal.getDateTime().toLocalDate();
            int currentCals = currentMeal.getCalories();
            datesAndCalories.merge(currentDate, currentCals, Integer::sum);
        }

        List<UserMealWithExcess> filteredMealsWithExcess = new ArrayList<>();
        for (UserMeal currentMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(currentMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDate currentDate = currentMeal.getDateTime().toLocalDate();
                filteredMealsWithExcess.add(new UserMealWithExcess(
                        currentMeal.getDateTime(),
                        currentMeal.getDescription(),
                        currentMeal.getCalories(),
                        datesAndCalories.get(currentDate) > caloriesPerDay));
            }
        }
        return filteredMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> datesAndCalories = meals.stream()
                .collect(Collectors.groupingBy(
                        userMeal -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .map(userMealWithExcess -> new UserMealWithExcess(
                        userMealWithExcess.getDateTime(),
                        userMealWithExcess.getDescription(),
                        userMealWithExcess.getCalories(),
                        datesAndCalories.get(userMealWithExcess.getDateTime().toLocalDate()) > caloriesPerDay))
                .filter(userMealWithExcess -> TimeUtil.isBetweenHalfOpen(userMealWithExcess.getDateTime().toLocalTime(),startTime,endTime))
                .collect(Collectors.toList());
    }
}
