package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaUserTest extends UserServiceTest {

    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(UserTestData.USER_ID);
        MealTestData.MEAL_MATCHER.assertMatch(MealTestData.MEALS, user.getMeals());
    }

}
