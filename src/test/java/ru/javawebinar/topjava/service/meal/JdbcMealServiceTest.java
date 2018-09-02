package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.HSQL_DB;
import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles({HSQL_DB, JDBC})
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
