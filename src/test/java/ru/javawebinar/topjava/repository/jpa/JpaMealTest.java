package ru.javawebinar.topjava.repository.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(value = Profiles.JPA)
public class JpaMealTest extends MealServiceTest {
}
