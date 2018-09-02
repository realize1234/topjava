package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATA_JPA;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({ACTIVE_DB, DATA_JPA})
public class DataJpaUserServiceTest  extends AbstractUserServiceTest{

    @Test
    public void testGetWithMeal() throws Exception{
        User u = service.getWithMeal(USER_ID);
        UserTestData.MATCHER.assertEquals(USER, u);
        MealTestData.MATCHER.assertCollectionEquals(MEALS, u.getMeals());

    }
}
