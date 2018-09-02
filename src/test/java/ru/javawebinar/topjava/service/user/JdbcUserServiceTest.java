package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles({ACTIVE_DB, JDBC})
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
