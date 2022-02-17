package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.EnumSet;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();

            System.out.println(inMemoryUserRepository.save(new User(1, "", "", " ", Role.USER)));
            System.out.println(inMemoryUserRepository.getAll());
            System.out.println(inMemoryUserRepository.get(1));
            System.out.println(inMemoryUserRepository.getByEmail(""));
            System.out.println();

        }
    }
}
