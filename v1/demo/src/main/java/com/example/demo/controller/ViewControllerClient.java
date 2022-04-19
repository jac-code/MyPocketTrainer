package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;

import com.example.demo.controller.dao.ProfessionalDAO;
import com.example.demo.controller.dao.UserFinderDAO;
import com.example.demo.model.Daily;
import com.example.demo.model.Exercise;
import com.example.demo.model.Professional;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DailyService;
import com.example.demo.service.DietService;
import com.example.demo.service.ExercisesService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.WeeklyService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("clients/client-free/view")
public class ViewControllerClient {
    public static final String DIRECCION_BASE = "hub/client/";

    public static final String PAGE_VIEW_EXERCISE = "view-exercise";
    public static final String PAGE_VIEW_RECIPE = "view-recipe";
    public static final String PAGE_VIEW_DIET = "view-diet";
    public static final String PAGE_VIEW_ROUTINE = "view-routine";
    public static final String PAGE_VIEW_DAILY = "view-daily";
    public static final String PAGE_VIEW_WEEKLY = "view-weekly";
    
    public static final String URL_VIEW_EXERCISE = "exercise/";
    public static final String URL_VIEW_RECIPE = "recipe/";
    public static final String URL_VIEW_DIET = "diet/";
    public static final String URL_VIEW_ROUTINE = "routine/";
    public static final String URL_VIEW_DAILY = "daily/";
    public static final String URL_VIEW_WEEKLY = "weekly/";
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    ClientsService clientsService;

    @Autowired
    ExercisesService exercisesService;

    @Autowired 
    RecipesService recipesService;

    @Autowired
    DailyService dailyService;

    @Autowired
    RoutineService routineService;

    @Autowired
    DietService dietService;

    @Autowired
    ProfessionalsService professionalsService;

    @Autowired
    WeeklyService weeklyService;
    
    /* ********************************************************************* */
    /* ******************** VIEWS ****************** */
    /* ********************************************************************* */

    @GetMapping("/" + URL_VIEW_EXERCISE + "/" + "{exercise_id}")
    public String viewExercise(@PathVariable String exercise_id, ModelMap modelMap) {
        modelMap.addAttribute("exercise", exercisesService.getExerciseById(Long.parseLong(exercise_id)));
        modelMap.addAttribute("exercise_id", exercise_id);
        return DIRECCION_BASE + PAGE_VIEW_EXERCISE;
    }
    
    @GetMapping("/" + URL_VIEW_RECIPE + "/" + "{recipe_id}")
    public String viewRecipe(@PathVariable String recipe_id, Model model) {
        model.addAttribute("recipe", recipesService.getRecipeById(Long.parseLong(recipe_id)));
        return DIRECCION_BASE + PAGE_VIEW_RECIPE;
    }

    @GetMapping("/" + URL_VIEW_DIET + "/" + "{diet_id}")
    public String viewDiet(@PathVariable String diet_id, Model model) {
        model.addAttribute("diet", dietService.getDietById(Long.parseLong(diet_id)));
        return DIRECCION_BASE + PAGE_VIEW_DIET;
    }

    @GetMapping("/" + URL_VIEW_ROUTINE + "/" + "{routine_id}")
    public String viewRoutine(@PathVariable String routine_id, Model model) {
        model.addAttribute("routine", routineService.getRoutineById(Long.parseLong(routine_id)));
        return DIRECCION_BASE +  PAGE_VIEW_ROUTINE;
    }
}