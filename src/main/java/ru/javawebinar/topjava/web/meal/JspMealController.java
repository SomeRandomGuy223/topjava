package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public void getAll(Model model) {
        model.addAttribute("meals", getAll());
    }

    @GetMapping(params = {"startDate", "endDate", "startTime", "endTime"})
    public void getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<MealTo> mealsFiltered = getBetween(startDate, startTime, endDate, endTime);

        model.addAttribute("meals", mealsFiltered);
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        delete(getId(request));

        return "redirect:/meals";
    }

    @GetMapping("/meal-form")
    public String addMealForm(Model model, HttpServletRequest request) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);

        return "mealForm";
    }

    @GetMapping(value = "/meal-form", params = "id")
    public String updMealForm(@RequestParam int id, Model model, HttpServletRequest request) {
        model.addAttribute("meal", service.get(getId(request), SecurityUtil.authUserId()));

        return "mealForm";
    }

    @PostMapping("/meal-form/save")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            create(meal);
        } else {
            update(meal, getId(request));
        }

        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
