package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal get(int id);

    Meal save(Meal meal);

    boolean delete(int id);


    List<Meal> getAll();
}
