package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MealRepositoryInMemory implements MealRepository {
    private static final Map<Integer, Meal> meals = new HashMap<>();

    static {
        meals.put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(7, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getMeal(Integer id) {
        return meals.get(id);
    }

    @Override
    public void addMeal(Meal meal) {
        synchronized (meals) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        deleteMeal(meal.getId());
        addMeal(meal);
    }

    @Override
    public void deleteMeal(Integer id) {
        synchronized (meals) {
            meals.remove(id);
        }
    }
}
