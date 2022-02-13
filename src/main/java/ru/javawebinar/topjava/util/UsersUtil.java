package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User("nobody", "nobody@nowhere", "psswd", EnumSet.of(Role.USER)),
            new User("xandra", "boginya@gmail", "psswd", EnumSet.of(Role.USER)),
            new User("Kirill", "kirill@kirillov", "psswd", EnumSet.of(Role.USER)),
            new User("Anton", "anton@baton", "psswd", EnumSet.of(Role.USER))
    );
}
