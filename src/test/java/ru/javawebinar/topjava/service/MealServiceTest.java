package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(value = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "utf-8"))

public class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void get() {
        MATCHER.assertEquals(USER_MEAL_ONE, service.get(FIRST_USER_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(FIRST_USER_MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_SIX, USER_MEAL_FIVE, USER_MEAL_FOUR, USER_MEAL_THREE, USER_MEAL_TWO),
                service.getAll(USER_ID));
    }

    @Test
    public void getBetweenDateTimes() {
        MATCHER.assertCollectionEquals(USER_MEAL_LIST,
                service.getBetweenDateTimes(USER_MEAL_ONE.getDateTime(), USER_MEAL_SIX.getDateTime(),USER_ID));

    }

    @Test
    public void getAll() {
       MATCHER.assertCollectionEquals(USER_MEAL_LIST, service.getAll(USER_ID));
    }

    @Test
    public void update() {
        Meal meal = new Meal(FIRST_USER_MEAL_ID, LocalDateTime.now(), "updated", 1005);
        service.update(meal, USER_ID);
        MATCHER.assertEquals(service.get(FIRST_USER_MEAL_ID, USER_ID), meal);
    }

    @Test
    public void save() {
        Meal meal = new Meal(LocalDateTime.now(), "new", 500);
        service.save(meal, USER_ID);
        MATCHER.assertEquals(service.get(meal.getId(), USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() {
        service.delete(FIRST_USER_MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotGet() {
        service.get(FIRST_USER_MEAL_ID, ADMIN_ID);
    }
}