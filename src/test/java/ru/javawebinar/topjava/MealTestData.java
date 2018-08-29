package ru.javawebinar.topjava;

import ch.qos.logback.core.util.TimeUtil;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl.MEAL_COMPARATOR;

public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
    public static final int FIRST_USER_MEAL_ID = START_SEQ + 2;
    public static final Meal USER_MEAL_ONE = new Meal(FIRST_USER_MEAL_ID ,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500) ;
    public static final Meal USER_MEAL_TWO = new Meal(FIRST_USER_MEAL_ID +1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_THREE = new Meal(FIRST_USER_MEAL_ID +2,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_FOUR = new Meal(FIRST_USER_MEAL_ID +3,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL_FIVE = new Meal(FIRST_USER_MEAL_ID +4,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL_SIX = new Meal(FIRST_USER_MEAL_ID +5,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);


    public static final List<Meal> USER_MEAL_LIST = Arrays.asList(USER_MEAL_SIX, USER_MEAL_FIVE, USER_MEAL_FOUR,
            USER_MEAL_THREE, USER_MEAL_TWO, USER_MEAL_ONE);

    public static List<Meal> getInTime(LocalDateTime from, LocalDateTime to){
        return USER_MEAL_LIST.stream().filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), from , to))
                .sorted(MEAL_COMPARATOR).collect(toList());
    }

}
