package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;
    private final SimpleJdbcInsert insert;

    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.template = template;
        this.namedTemplate = namedTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", meal.getId())
               .addValue("userId", userId)
               .addValue("description", meal.getDescription())
               .addValue("calories", meal.getCalories())
               .addValue("dateTime", meal.getDateTime());

        if(meal.isNew()) {
            Number mealId = insert.executeAndReturnKey(source);
            meal.setId(mealId.intValue());
        } else {
            namedTemplate.update(
                    "UPDATE meals SET description=:description, calories=:calories, date_time=:dateTime" +
                            " WHERE id=:id AND user_id=:userId", source);
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return template.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return  template.query("SELECT * FROM meals WHERE id=? AND user_id =?", ROW_MAPPER, id, userId).get(0);

    }

    @Override
    public List<Meal> getAll(int userId) {

        return template.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return template.query("SELECT * FROM meals " +
                "WHERE user_id =? AND date_time BETWEEN ? AND ? ORDER BY date_time DESC", ROW_MAPPER, userId, startDate, endDate);
    }
}
