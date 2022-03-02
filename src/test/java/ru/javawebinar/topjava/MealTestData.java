package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = AbstractBaseEntity.START_SEQ;
    public static final int NOT_FOUND = 3;

    public static final Meal userMeal1 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(MEAL_ID + 4, LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(MEAL_ID + 5, LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(MEAL_ID + 6, LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(MEAL_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(MEAL_ID + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(MEAL_ID + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(MEAL_ID + 10, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Админа", 500);
    public static final Meal adminMeal2 = new Meal(MEAL_ID + 11, LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед Админа", 1000);
    public static final Meal adminMeal3 = new Meal(MEAL_ID + 12, LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин Админа", 500);
    public static final Meal adminMeal4 = new Meal(MEAL_ID + 13, LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Фасоль Админа", 100);
    public static final Meal adminMeal5 = new Meal(MEAL_ID + 14, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Биг-Тейсти Админа", 2690);
    public static final Meal adminMeal6 = new Meal(MEAL_ID + 15, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Маленькая шаверма Админа", 1440);

    public static final List<Meal> userMeals = Stream.of(userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
    public static final List<Meal> adminMeal = Arrays.asList(adminMeal1, adminMeal2, adminMeal3, adminMeal4, adminMeal5, adminMeal6);

    public static Meal getUpdated() {
        return new Meal(userMeal3.getId(), userMeal3.getDateTime(), "Updated -> Updated", 7777);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2345, Month.JANUARY, 1, 0, 0), "New Description", 333);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
