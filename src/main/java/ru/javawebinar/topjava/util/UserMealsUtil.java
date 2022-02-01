package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles
                (meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> limitCalc = new HashMap<>();
        List<UserMeal> filteredMeals = new ArrayList<>();
        //Задачи первого прохода: отфильтровать UserMeal по заданному времени и подсчитать общий объём потреблённых
        //калорий за каждый день
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();

            if (!limitCalc.containsKey(mealDate))
                limitCalc.put(mealDate, meal.getCalories());
            else
                limitCalc.put(mealDate, limitCalc.get(mealDate) + meal.getCalories());

            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                filteredMeals.add(meal);
        }
        //Задачи второго прохода по отфильтрованному списку - определить какой UserMeal приходится на день с
        //превышением и сформировать итоговый список
        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMeal meal : filteredMeals) {
            if (limitCalc.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                resultList.add(new UserMealWithExcess
                        (meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
            else
                resultList.add(new UserMealWithExcess
                        (meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
