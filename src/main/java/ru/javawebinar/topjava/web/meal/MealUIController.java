package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> createOrUpdate(@Valid MealTo mealTo, BindingResult result) {
        if (result.hasErrors()) {
            String errorFieldsMsg = result.getFieldErrors().stream()
                    .map(fieldError -> String.format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.joining("<br>"));
            return ResponseEntity.unprocessableEntity().body(errorFieldsMsg);
        }
        if (mealTo.isNew()) {
            super.create(mealTo);
        } else {
            super.update(mealTo, mealTo.id());
        }
        return ResponseEntity.ok().build();
    }
/*
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                       String description,
                       int calories) {
        super.create(new Meal(null, dateTime, description, calories));
    }*/

    @Override
    @GetMapping("/filter")
    public List<MealTo> getBetween(
            @Nullable LocalDate startDate, @Nullable LocalTime startTime,
            @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}