package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;
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
        User anton = new User("Anton Petrunov");
        User kirill = new User("Kirill Petrunov");

        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        anton.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак пропустил :)", 0));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед - булочка...", 1000));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин - сосулька...", 200));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 20));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак - ice cream...", 1500));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 400));
        kirill.addMeal(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 0));

        System.out.println("\nOutput for " + anton.getName() + "\n");
        List<UserMealWithExcess> mealsTo = filteredByCycles(anton, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(filteredByStreams(anton, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println("\nOutput for " + kirill.getName() + "\n");
        System.out.println(filteredByCycles(kirill, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams(kirill, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(User user, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filteredMealsWithExcess = new ArrayList<>();

        for (UserMeal userMeal : user.getMeals()) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDate date = userMeal.getDateTime().toLocalDate();
                filteredMealsWithExcess.add(new UserMealWithExcess(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        user.getDatesAndSumsOfCalories().get(date) > caloriesPerDay));
            }
        }
        return filteredMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(User user, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return user.getMeals().stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(
                        userMeal.getDateTime().toLocalTime(),
                        startTime,
                        endTime))
                .map(userMeal -> new UserMealWithExcess(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        user.getDatesAndSumsOfCalories().get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
