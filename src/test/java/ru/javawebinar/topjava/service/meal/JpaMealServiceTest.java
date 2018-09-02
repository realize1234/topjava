package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles({ACTIVE_DB, JPA})
public class JpaMealServiceTest  extends AbstractMealServiceTest{
}
