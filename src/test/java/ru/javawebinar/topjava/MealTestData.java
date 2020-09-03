package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final LocalDateTime LUNCH_TIME = LocalDateTime
            .of(2020, 8, 29, 13, 0, 0);

    public static final LocalDateTime DINNER_TIME = LocalDateTime
            .of(2020, 8, 29, 19, 0, 0);

    public static final Meal LUNCH = new Meal(MEAL_ID, LUNCH_TIME, "Lunch", 800);
    public static final Meal DINNER = new Meal(DINNER_TIME, "Dinner", 1200);

    public static Meal getNew() {
        return getNew(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public static Meal getNew(LocalDateTime ldt) {
        return new Meal(ldt, "NewMeal", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(LUNCH);
        updated.setDescription("updated");
        updated.setCalories(400);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }

}
