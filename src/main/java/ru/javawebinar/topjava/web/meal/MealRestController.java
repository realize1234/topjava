package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

@Controller
public class MealRestController {
    @Autowired private MealService service;

    public Meal create(Meal meal) {
        ValidationUtil.checkNew(meal);
        return service.create(AuthorizedUser.id(),meal);
    }

    public Meal update(Meal meal) {
        return service.update(AuthorizedUser.id(), meal);
    }

    public Meal get(int id) {
        return service.get(AuthorizedUser.id(), id);
    }

    public void delete(int id) {
        service.delete(AuthorizedUser.id(), id);
    }

    public List<MealWithExceed> getAll() {
        return getWithExceeded(service.getAll(AuthorizedUser.id()), DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getBetween(LocalDate fromD, LocalDate toD, LocalTime fromT, LocalTime toT) {
        return getWithExceeded(service.getBetween(AuthorizedUser.id(), fromD, toD, fromT, toT), DEFAULT_CALORIES_PER_DAY);
    }

}