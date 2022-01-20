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
        TreeMap<LocalDate, Integer> consolidateMap = new TreeMap<>();

        for (UserMeal currentMeal : meals) {
            LocalDate currentDate = currentMeal.getDateTime().toLocalDate();
            int currentCals = currentMeal.getCalories();
            if (consolidateMap.containsKey(currentDate)) {
                int initCals = consolidateMap.get(currentDate);
                consolidateMap.put(currentDate, initCals + currentCals);
            } else
                consolidateMap.put(currentDate, currentCals);
        }
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();

        for (UserMeal currentMeal : meals) {
            LocalDate currentDate = currentMeal.getDateTime().toLocalDate();
            if (consolidateMap.get(currentDate) > caloriesPerDay) {
                mealsWithExcess.add(new UserMealWithExcess(currentMeal.getDateTime(), currentMeal.getDescription(), currentMeal.getCalories(), true));
            } else {
                mealsWithExcess.add(new UserMealWithExcess(currentMeal.getDateTime(), currentMeal.getDescription(), currentMeal.getCalories(), false));
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMealWithExcess currentMeal : mealsWithExcess) {
            if (TimeUtil.isBetweenHalfOpen(currentMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(currentMeal);
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        // TODO Delete the first Stream
/*        meals.stream()
                .map(m -> m.getDateTime().toLocalDate())
                .forEach(System.out::println);
*/
        Map<LocalDate, Integer> dateAndCals = meals.stream()
                .collect(Collectors.groupingBy(
                        me -> me.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        System.out.println(dateAndCals.toString());

        // TODO Re-DO whits STREAMS

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal currentMeal : meals) {
            LocalDate currentDate = currentMeal.getDateTime().toLocalDate();
            if (dateAndCals.get(currentDate) > caloriesPerDay)
                mealsWithExcess.add(new UserMealWithExcess(currentMeal.getDateTime(), currentMeal.getDescription(), currentMeal.getCalories(), true));
            else
                mealsWithExcess.add(new UserMealWithExcess(currentMeal.getDateTime(), currentMeal.getDescription(), currentMeal.getCalories(), false));
        }

        return mealsWithExcess.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(),startTime,endTime))
                .collect(Collectors.toList());
    }
}
