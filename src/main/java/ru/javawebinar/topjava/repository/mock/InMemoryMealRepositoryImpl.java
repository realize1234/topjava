package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    public static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    public static final Comparator<Meal> MEAL_DATE_TIME_R = Comparator.comparing(Meal::getDateTime).reversed();

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public InMemoryMealRepositoryImpl() {
        MealsUtil.MEALS.forEach(meal -> this.save(1, meal));
        this.save(2, new Meal(LocalDateTime.of(2018, 8, 22, 14, 55),
                "adminLunch", 1500));
        this.save(2, new Meal(LocalDateTime.of(2018, 8, 22, 20, 0),
                "adminSupper", 2000));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        LOG.info("Save for " + userId + " " + meal);
        Map<Integer, Meal> userMeal = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) meal.setId(id.incrementAndGet());
        userMeal.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("Delete for " + userId + " with id " + id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info("Get for " + userId + "with id " + id);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("Get all " + userId);
        List<Meal> result = new ArrayList<>(repository.get(userId).values());
        result.sort(MEAL_DATE_TIME_R);
        return result;
    }

    @Override
    public List<Meal> getBetween(int userId, LocalDateTime from, LocalDateTime to) {
        LOG.info("getBetween " + from + " - " + to);
        return repository.get(userId).values().stream().filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), from, to))
                .sorted(MEAL_DATE_TIME_R).collect(Collectors.toList());
    }

}