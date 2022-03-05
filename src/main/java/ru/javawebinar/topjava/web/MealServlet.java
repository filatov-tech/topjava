package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext context;
    private MealRestController mealRestController;


    @Override
    public void init() {
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = context.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        context.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        Integer userId = SecurityUtil.authUserId();
        if (meal.isNew()) {
            mealRestController.create(meal, userId);
        } else {
            mealRestController.update(meal, userId);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer userId = SecurityUtil.authUserId();

        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalTime startTime = null;
        LocalTime endTime = null;

        String filter = request.getParameter("filter");
        log.info("is filter null = " + (filter == null));

        if (filter != null) {
            startDate = request.getParameter("startDate").isEmpty() ?
                    LocalDate.MIN : LocalDate.parse(request.getParameter("startDate"));
            request.setAttribute("startDate", startDate);
            endDate = request.getParameter("endDate").isEmpty() ?
                    LocalDate.MAX : LocalDate.parse(request.getParameter("endDate"));
            request.setAttribute("endDate", endDate);
            startTime = request.getParameter("startTime").isEmpty() ?
                    LocalTime.MIN : LocalTime.parse(request.getParameter("startTime"));
            request.setAttribute("startTime", startTime);
            endTime = request.getParameter("endTime").isEmpty() ?
                    LocalTime.MAX : LocalTime.parse(request.getParameter("endTime"));
            request.setAttribute("endTime", endTime);
        }


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id, userId);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request), userId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                List<MealTo> meals = null;
                if (filter != null) {
                    meals = mealRestController.getAll(userId, startDate, endDate, startTime, endTime);
                } else {
                    meals = mealRestController.getAll(userId);
                }
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
