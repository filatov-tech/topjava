package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.MealRepositoryInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static MealRepository mealRepository = new MealRepositoryInMemory();
    private static final String MEALS = "/meals.jsp";
    private static final String MEALS_EDIT = "/meals-edit.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            List<MealTo> mealsList = MealsUtil.toMealsTo(mealRepository.getAllMeals());
            req.setAttribute("mealsList", mealsList);
            req.getRequestDispatcher(MEALS).forward(req, resp);
        } else {
            switch (action) {
                case "delete":
                    mealRepository.deleteMeal(Integer.parseInt(req.getParameter("id")));
                    List<MealTo> mealsList = MealsUtil.toMealsTo(mealRepository.getAllMeals());
                    req.setAttribute("mealsList", mealsList);
                    resp.sendRedirect("meals");
                    break;
                case "add":
                    req.getRequestDispatcher(MEALS_EDIT).forward(req, resp);
                    break;
                case "update":
                    Meal meal = mealRepository.getMeal(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("meal", meal);
                    req.getRequestDispatcher(MEALS_EDIT).forward(req, resp);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        LocalDateTime date = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(date, description, calories);

        String idString = req.getParameter("id");
        Integer id = null;
        if (!idString.isEmpty()) {
            id = Integer.parseInt(idString);
        }
        if (id == null) {
            mealRepository.addMeal(meal);
        } else {
            meal.setId(id);
            mealRepository.updateMeal(meal);
        }
        List<MealTo> mealsList = MealsUtil.toMealsTo(mealRepository.getAllMeals());
        req.setAttribute("mealsList", mealsList);
        req.getRequestDispatcher(MEALS).forward(req, resp);
    }
}
