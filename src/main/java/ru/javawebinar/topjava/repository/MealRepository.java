package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(int userId, Meal meal);

     boolean delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(int userId, LocalDateTime from, LocalDateTime to);
}
