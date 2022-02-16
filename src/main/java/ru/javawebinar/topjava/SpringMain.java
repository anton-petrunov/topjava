package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            System.out.println("-------------------------------->");
            InMemoryUserRepository inMemoryUserRepository = appCtx.getBean(InMemoryUserRepository.class);
            System.out.println(inMemoryUserRepository.getAll());

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println("-------------------------------->");

            System.out.println(mealRestController.getAll());

            InMemoryMealRepository inMemoryMealRepository = appCtx.getBean(InMemoryMealRepository.class);
            System.out.println("-------------------------------->");

            System.out.println(mealRestController.get(2));
            mealRestController.delete(1);
            System.out.println(mealRestController.getAll());
            System.out.println("-------------------------------->");

            System.out.println(adminUserController.getByMail("KIRILL@kirillov"));
            System.out.println("-------------------------------->");
            adminUserController.delete(adminUserController.getByMail("KiRiLl@KirIlLoV").getId());
            System.out.println("-------------------------------->");
            System.out.println(adminUserController.getAll());
            System.out.println("-------------------------------->");

            System.out.println(adminUserController.create(new User("NoName", "noMail", "noPasswd", EnumSet.of(Role.USER))));
            System.out.println("-------------------------------->");
            System.out.println(adminUserController.getAll());
            adminUserController.update(new User("empty", "empty", "test", EnumSet.of(Role.USER)), 4);
            System.out.println(adminUserController.getAll());
            System.out.println("--------------------------");
            inMemoryMealRepository.save(new Meal(LocalDateTime.now(), "description", 471, 1), 1);
            System.out.println(inMemoryMealRepository.getAll(1));
            inMemoryMealRepository.save(new Meal(LocalDateTime.now(), "Replacement of description", 88, 1), 1);
            System.out.println("--------------------------");
            System.out.println(inMemoryMealRepository.getAll(1));
            inMemoryMealRepository.delete(8, 1);
            System.out.println("--------------------------\n" + inMemoryMealRepository.getAll(1));
            inMemoryMealRepository.save(inMemoryMealRepository.get(9, 1), 1);
            System.out.println("--------------------------\n" + inMemoryMealRepository.getAll(1));
            inMemoryMealRepository.save(new Meal(9, LocalDateTime.now(), "-----", 400, 2), 1);
            System.out.println("--------------------------\n" + inMemoryMealRepository.getAll(1));
            System.out.println("-------------------------------->");
            mealRestController.create(new Meal(LocalDateTime.now(), "Real Meal", 12800, 1));
            System.out.println("-------------------------------->");
            System.out.println(mealRestController.getAll());
            System.out.println(adminUserController.getByMail("nomAIL"));
        }
    }
}
