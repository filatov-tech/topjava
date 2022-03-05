package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, 1);
        }
        save(new Meal(null, LocalDateTime.now(), "Breakfast", 1000), 2);
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (repository.containsKey(userId)) {
                repository.get(userId).put(meal.getId(), meal);
            } else {
                repository.put(userId, new HashMap<>());
                repository.get(userId).put(meal.getId(), meal);
            }
            return meal;
        }
        // handle case: update, but not present in storage



        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals == null) {
            throw new NotFoundException("User with id=" + userId + " didn't found");
        }
        Meal meal = usersMeals.remove(id);
        return meal != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals == null) {
            throw new NotFoundException("User with id=" + userId + " didn't found");
        }
        return usersMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return getAll(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public Collection<Meal> getAll(Integer userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> usersMeals = repository.get(userId);
        if (usersMeals == null) {
            throw new NotFoundException("User with id=" + userId + " didn't found");
        }
        return usersMeals.values().stream()
                .filter(meal -> DateTimeUtil.isDateBetweenOpen(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

