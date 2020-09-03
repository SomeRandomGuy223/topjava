package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, LUNCH);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, NOT_FOUND));
    }

    @Test
    public void getNotMyMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        assertNull(repository.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, NOT_FOUND));
    }

    @Test
    public void deleteNotMyMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        Meal inclMeal = getNew(LUNCH_TIME.plusDays(1));
        Meal excMeal = getNew(LUNCH_TIME.minusDays(1));

        inclMeal = service.create(inclMeal, USER_ID);
        service.create(excMeal, USER_ID);

        assertMatch(service.getBetweenInclusive(LUNCH_TIME.toLocalDate(), inclMeal.getDate(), USER_ID),
                service.get(inclMeal.getId(), USER_ID), DINNER, LUNCH);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, DINNER, LUNCH);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(updated, service.get(updated.getId(), USER_ID));
    }

    @Test
    public void updateNotMyMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.update(LUNCH, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(newMeal, service.get(created.getId(), USER_ID));

    }
}