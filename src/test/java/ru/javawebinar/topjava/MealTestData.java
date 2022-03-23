package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 3;

    public static final Meal meal1User = new Meal(MEAL_ID, LocalDateTime.of(2022, 3, 16, 13,0), "Pizza", 700);
    public static final Meal meal2User = new Meal(MEAL_ID + 1, LocalDateTime.of(2022, 3, 16, 14,0),"Pasta", 650);
    public static final Meal meal3User = new Meal(MEAL_ID + 2, LocalDateTime.of(2022, 3, 16, 15,0),"Apple", 150);
    public static final Meal meal4User = new Meal(MEAL_ID + 3, LocalDateTime.of(2022, 3, 16, 16,0),"Orange juice", 200);
    public static final Meal meal1Admin = new Meal(MEAL_ID + 4, LocalDateTime.of(2022, 3, 16, 11,0),"Pinza", 650);
    public static final Meal meal2Admin = new Meal(MEAL_ID + 5, LocalDateTime.of(2022, 3, 16, 12,0),"Coca-Cola", 300);



    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newPizza", 950);
    }

    public static Meal getUpdate() {
        Meal meal = new Meal(meal1User);
        meal.setCreated(meal1User.getDateTime().minusMinutes(1));
        meal.setDescription("updatedPizza");
        meal.setCalories(1100);
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}
