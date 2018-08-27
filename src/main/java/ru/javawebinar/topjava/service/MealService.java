package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal get(int userId, int id);

    void delete(int userId, int id);

    Meal create(int userId, Meal meal);

    Meal update(int userId, Meal meal);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTome, LocalTime toTime);

}