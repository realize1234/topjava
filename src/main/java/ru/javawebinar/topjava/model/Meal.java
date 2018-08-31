package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.model.Meal.*;

@Entity

@NamedQueries({
        @NamedQuery(name = MEAL_GET, query = "SELECT  m FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = MEAL_GET_ALL, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC "),
        @NamedQuery(name = MEAL_DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = MEAL_GET_ALL_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId" +
                " AND m.dateTime BETWEEN :startDateTime AND :endDateTime ORDER BY m.dateTime DESC ")
})
@Table(name = "meals", uniqueConstraints =
        {@UniqueConstraint(columnNames ={"date_time", "user_id"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends BaseEntity {
    public static final String MEAL_GET = "mealGet";
    public static final String MEAL_GET_ALL = "mealGetAll";
    public static final String MEAL_DELETE = "mealDelete";
    public static final String MEAL_GET_ALL_BETWEEN = "mealGetAllBetween";
    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull @Range(min = 10, max = 3000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
