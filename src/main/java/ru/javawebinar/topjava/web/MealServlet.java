package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.model.Meal.DEFAULT_CALORIES;

public class MealServlet extends HttpServlet {
    private MealRepository repository = new MealRepositoryImpl();
    public static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            LOG.info("Get all");
            req.setAttribute("meals", MealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else if(action.equalsIgnoreCase("delete")) {
            int id = getId(req);
            LOG.info("delete " + id);
            repository.delete(id);
            resp.sendRedirect("meals");

        } else if(action.equalsIgnoreCase("update") || action.equalsIgnoreCase("create")) {

            LOG.info("save ");
            Meal meal  = action.equals("create") ?
                    new Meal(LocalDateTime.now(), " ", 0)
                    : repository.get(getId(req));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("/meal.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.equals("") ? null : Integer.valueOf(id), LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        LOG.info(meal.isNew() ? "Save " + meal : "Update " + meal);
        repository.save(meal);
        resp.sendRedirect("meals");
    }

    private Integer getId(ServletRequest request){
        return Integer.parseInt(request.getParameter("id"));
    }
}
