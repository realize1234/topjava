package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.model.Meal.*;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager manager;
    @Override
    public Meal save(Meal meal, int userId) {
        if(!meal.isNew() && meal.getUser().getId() != userId) return null;
        if(meal.isNew()) {
            User ref = manager.getReference(User.class, userId);
            meal.setUser(ref);
            manager.persist(meal);
            return meal;
        }
        return manager.merge(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return manager.createNamedQuery(MEAL_DELETE).setParameter("id", id)
                .setParameter("userId", id).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return manager.createNamedQuery(MEAL_GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId).getSingleResult();
    }

    @Override
    public List<Meal> getAll(int userId) {
        return manager.createNamedQuery(MEAL_GET_ALL, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return manager.createNamedQuery(MEAL_GET_ALL_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDate)
                .setParameter("endDateTime", endDate)
                .getResultList();
    }
}