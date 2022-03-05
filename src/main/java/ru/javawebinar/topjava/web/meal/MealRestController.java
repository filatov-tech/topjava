package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll(Integer userId) {
        log.info("getAll user = {}", userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAll(Integer userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll user = {}", userId);

        return MealsUtil.getFilteredTos(
                service.getAll(userId, startDate, endDate),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                startTime,
                endTime);
    }

    public Meal get(Integer id, Integer userId) {
        log.info("get {}, for user = {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, Integer userId) {
        log.info("create {}, user = {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(Integer id, Integer userId) {
        log.info("delete {}, user = {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, Integer userId) {
        log.info("update {}, user = {}", meal, userId);
        service.update(meal, userId);
    }

}