package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATA_JPA;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({ACTIVE_DB, DATA_JPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() throws Exception {
        Meal m = service.getWithUser(MEAL1_ID, USER_ID);
        MealTestData.MATCHER.assertEquals(MEAL1, m);
        UserTestData.MATCHER.assertEquals(USER, m.getUser());
    }
}
