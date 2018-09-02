package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATA_JPA;

@ActiveProfiles({ACTIVE_DB, DATA_JPA})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
}
