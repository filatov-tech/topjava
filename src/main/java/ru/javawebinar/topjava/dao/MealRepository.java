package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    List<Meal> getAllMeals();

    Meal getMeal(Integer id);

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(Integer id);
}
