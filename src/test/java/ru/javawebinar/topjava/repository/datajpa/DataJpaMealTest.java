package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        Meal m2 = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories(), USER);
        MEAL_MATCHER_ALL_FIELDS.assertMatch(meal, m2);
    }
}
