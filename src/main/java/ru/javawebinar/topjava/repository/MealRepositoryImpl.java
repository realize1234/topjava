package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    private AtomicInteger id = new AtomicInteger(0);

    public MealRepositoryImpl() {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew()) {
            meal.setId(id.incrementAndGet());
            repository.put(meal.getId(), meal);
        }
        else {
            repository.replace(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }


    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }
}
