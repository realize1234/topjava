package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.format.datetime.joda.LocalTimeParser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealController;
    private GenericXmlApplicationContext context;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = new GenericXmlApplicationContext("/spring/spring-app.xml");
        mealController = context.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        context.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if(action == null) {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            if (meal.isNew()) {
                LOG.info("create " + meal);
                mealController.create(meal);
            } else {
                LOG.info("update " + meal);
                mealController.update(meal);
            }
            response.sendRedirect("meals");
        } else if(action.equalsIgnoreCase("filter")) {

                LOG.info("filter");
                String fromD = request.getParameter("fromDate");
                String toD = request.getParameter("toDate");
                String fromT = request.getParameter("fromTime");
                String toT = request.getParameter("toTime");
                LocalDate fromDate = fromD.isEmpty() ? LocalDate.MIN : LocalDate.parse(fromD);
                LocalDate toDate = toD.isEmpty()? LocalDate.MAX : LocalDate.parse(toD);
                LocalTime fromTime = fromT.isEmpty() ? LocalTime.MIN : LocalTime.parse(fromT);
                LocalTime toTime = toT.isEmpty()? LocalTime.MAX : LocalTime.parse(toT);
                request.setAttribute("meals", mealController.getBetween(fromDate, toDate, fromTime, toTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals", mealController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}