package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATA_JPA;

@ActiveProfiles({ACTIVE_DB, DATA_JPA})
public class DataJpaUserServiceTest  extends AbstractUserServiceTest{
}
