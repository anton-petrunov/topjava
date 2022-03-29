package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.user;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + meal1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void delete() throws Exception {
        meal2.setUser(user);
        perform(MockMvcRequestBuilders.delete(REST_URL + meal2.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(meal2.getId(), user.getId()));
    }

    @Test
    void getAll() throws Exception {
        List<MealTo> mealsTo = MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(mealsTo));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        newMeal.setUser(admin);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(action);
        int newMealId = created.getId();
        newMeal.setId(newMealId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newMealId, admin.getId()), newMeal);
    }

    @Test
    void update() throws Exception {
        meal1.setUser(user);
        Meal updated = MealTestData.getUpdated();
        updated.setUser(user);
        perform(MockMvcRequestBuilders.put(REST_URL + meal1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.get(meal1.getId(), user.getId()), updated);
    }

    @Test
    void getBetween() throws Exception {
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 30), user.getId());
        List<MealTo> mealsTo = MealsUtil.getFilteredTos(mealsDateFiltered, MealsUtil.DEFAULT_CALORIES_PER_DAY, LocalTime.of(9, 0), LocalTime.of(12, 0));
        perform(MockMvcRequestBuilders.get(REST_URL + "between"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(mealsTo));
    }
}