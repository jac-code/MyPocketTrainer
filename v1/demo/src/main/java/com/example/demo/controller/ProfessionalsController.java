package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Calendar;

import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.DailyDAO;
import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.ExerciseDAO;
import com.example.demo.controller.dao.RecipeDAO;
import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.controller.dao.WeeklyDAO;
import com.example.demo.model.ModelUser;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DailyService;
import com.example.demo.service.DietService;
import com.example.demo.service.ExercisesService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.WeeklyService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("professionals/professional-basic")
public class ProfessionalsController {
    public static final String DIRECCION_BASE = "hub/professional/";

    public static final String PAGE_MY_CLIENTS = "my-clients";
    public static final String PAGE_ADD_CLIENT = "my-clients-add-client";

    public static final String PAGE_MY_DIETS = "my-diets";
    public static final String PAGE_ADD_DIET = "my-diets-add-diet";

    public static final String PAGE_MY_EXERCISES = "my-exercises";
    public static final String PAGE_ADD_EXERCISE = "my-exercises-add-exercise";

    public static final String PAGE_MY_RECIPES = "my-recipes";
    public static final String PAGE_ADD_RECIPE = "my-recipes-add-recipe";

	public static final String PAGE_MY_ROUTINES = "my-routines";
    public static final String PAGE_ADD_ROUTINE = "my-routines-add-routine";

    public static final String PAGE_MY_WEEKLY_PLANS = "my-weekly-plans";
    public static final String PAGE_ADD_WEEKLY_PLAN = "my-daily-plans-form";

    public static final String PAGE_MY_DAILY_PLANS = "my-daily-plans";
    public static final String PAGE_ADD_DAILY_PLAN = "my-daily-plans-add";

    public static final String URL_MY_CLIENTS = "my-clients";
    public static final String URL_ADD_CLIENT = "add-client";
    public static final String URL_SAVE_CLIENT = "save-client";

    public static final String URL_MY_EXERCISES = "my-exercises/";
    public static final String URL_ADD_EXERCISE = "add-exercise";
    public static final String URL_SAVE_EXERCISE = "save-exercise";

    public static final String URL_MY_RECIPES = "my-recipes/";
    public static final String URL_ADD_RECIPE = "add-recipe";
    public static final String URL_SAVE_RECIPE = "save-recipe";

    public static final String URL_MY_DIETS = "my-diets/";
    public static final String URL_ADD_DIET = "add-diet";
    public static final String URL_SAVE_DIET = "save-diet";

    public static final String URL_MY_ROUTINES = "my-routines/";
    public static final String URL_ADD_ROUTINE = "add-routine";
    public static final String URL_SAVE_ROUTINE = "save-routine";

    public static final String URL_MY_DAILY_PLANS = "my-daily-plans/";
    public static final String URL_ADD_DAILY_PLAN = "add-daily-plan";
    public static final String URL_SAVE_DAILY_PLAN = "save-daily-plan/";

    public static final String URL_MY_WEEKLY_PLANS = "my-weekly-plans/";
    public static final String URL_ADD_WEEKLY_PLAN = "add-weekly-plan";
    public static final String URL_SAVE_WEEKLY_PLAN = "save-weekly-plan";
    
    @Autowired
    ProfessionalsService professionalsService;

    @Autowired
    ClientsService clientsService;

    @Autowired
    ModelUserService modelUserService;

    @Autowired
    DietService dietService;

    @Autowired
    RoutineService routineService;

    @Autowired
    ExercisesService exercisesService;

    @Autowired
    RecipesService recipeService;

    @Autowired
    DailyService dailyService;

    @Autowired
    WeeklyService weeklyService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/home")
    public String showHomePage(ModelMap modelMap) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        ModelUser user = (modelUserService.getModelUserByUsername(userPrincipal.getUsername()));
         
        modelMap.addAttribute("user", user);
         
        return DIRECCION_BASE + "professional-basic";
    }

    /* ********************************************************************* */
    /* ******************** DAILY PLANS ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_DAILY_PLANS, "/" + URL_MY_DAILY_PLANS + "{weekly_id}"})  // hacemos opcional el parámetro
    public String listMyDailyPlans(@PathVariable(required = false) String weekly_id, ModelMap modelMap) {
        if (weekly_id == null) { // NO poner boton conectar
            // value is a String and is not “false”, “off” or “no”
            modelMap.addAttribute("weekly_id", "false");
        } else {    // poner el botón de LINK
            modelMap.addAttribute("weekly_id", weekly_id);
        }
        
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("dailies", dailyService.listDailiesByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_DAILY_PLANS;
    }

    @GetMapping("/" + URL_ADD_DAILY_PLAN)
    public String startNewDailyPlan() {
        return DIRECCION_BASE + PAGE_ADD_DAILY_PLAN; // pagina html con formulario
    }

    @GetMapping("/save-daily-plan/{diet_id}/{routine_id}")
    public String saveDailyPlan(@PathVariable String diet_id, @PathVariable String routine_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        DailyDAO dailyDAO = new DailyDAO();
        dailyDAO.setDiet_id(diet_id);
        dailyDAO.setRoutine_id(routine_id);
        dailyService.saveNewDaily(dailyDAO, userPrincipal.getUsername());
        return "redirect:/professionals/professional-basic/" + URL_MY_DAILY_PLANS;    // nos REDIRECCIONA a la pagina con la lista de clientes
    }

    /* ********************************************************************* */
    /* ******************** WEEKLY PLANS ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_WEEKLY_PLANS, "/" + URL_MY_WEEKLY_PLANS + "{client_user_name}"})  // hacemos opcional el parámetro
    public String listWeeklyMyPlans(@PathVariable(required = false) String client_user_name, ModelMap modelMap) {
        if (client_user_name == null) { // NO poner boton conectar
            // value is a String and is not “false”, “off” or “no”
            modelMap.addAttribute("client_user_name", "false");
        } else {    // poner el botón de LINK
            modelMap.addAttribute("client_user_name", client_user_name);
        }

        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("weeklies", weeklyService.listWeekliesByProfessional(userPrincipal.getUsername()));
        
        return DIRECCION_BASE + PAGE_MY_WEEKLY_PLANS;
    }

    @GetMapping("/" + URL_ADD_WEEKLY_PLAN)
    public String addNewWeeklyPlan(ModelMap modelMap) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        WeeklyDAO new_weekly = new WeeklyDAO();

        modelMap.addAttribute("new_weekly", new_weekly);
        modelMap.addAttribute("dailies", dailyService.listDailiesByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_ADD_WEEKLY_PLAN; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_WEEKLY_PLAN)
    public String saveNewWeekly(@ModelAttribute("new_weekly") WeeklyDAO weeklyDAO) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        weeklyService.saveNewWeekly(weeklyDAO, userPrincipal.getUsername());

        return "redirect:" + URL_MY_WEEKLY_PLANS;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    @GetMapping("/link-client-weekly/{client_user_name}/{weekly_id}")
    public String linkWeeklyToClient(@PathVariable String client_user_name, @PathVariable String weekly_id) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        weeklyService.linkWeeklyToClient(client_user_name, weekly_id);
        
        return "redirect:/professionals/professional-basic/" + URL_MY_WEEKLY_PLANS;
    }

    /* ********************************************************************* */
    /* ******************** EXERCISES ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_EXERCISES, "/" + URL_MY_EXERCISES + "{routine_id}"})  // hacemos opcional el parámetro
    public String listMyExercises(@PathVariable(required = false) String routine_id, ModelMap modelMap) {
        if (routine_id == null) { // NO poner boton conectar
            // value is a String and is not “false”, “off” or “no”
            modelMap.addAttribute("routine_id", "false");
        } else {    // poner el botón de LINK
            modelMap.addAttribute("routine_id", routine_id);
        }
        
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("exercises", exercisesService.listExercisesByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_EXERCISES;
    }

    @GetMapping("/link-exercise-routine/{routine_id}/{exercise_id}")
    public String linkExerciseToRoutine(@PathVariable String routine_id, @PathVariable String exercise_id) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        routineService.ExerciseLinksWithRoutine(routine_id, exercise_id, userPrincipal.getUsername());
        return "redirect:/professionals/professional-basic/" + URL_MY_ROUTINES;
    }

    @GetMapping("/" + URL_ADD_EXERCISE)
    public String addNewExercise(Model model) {
        ExerciseDAO new_exercise = new ExerciseDAO();
        model.addAttribute("new_exercise", new_exercise);
        return DIRECCION_BASE + PAGE_ADD_EXERCISE; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_EXERCISE)
    public String saveExercise(@ModelAttribute("new_exercise") ExerciseDAO exerciseDAO) throws IOException{
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        exercisesService.saveNewExercise(exerciseDAO, userPrincipal.getUsername());
        return "redirect:" + URL_MY_EXERCISES;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    @GetMapping("/delete-exercise/{id}")
	public String deleteExercise(@PathVariable(value="id") Long exercise_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.deleteExercise(exercise_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_EXERCISES;
	}
    
    /* ********************************************************************* */
    /* ******************** RECIPES ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_RECIPES, "/" + URL_MY_RECIPES + "{diet_id}"})  // hacemos opcional el parámetro
    public String listMyRecipes(@PathVariable(required = false) String diet_id, ModelMap modelMap) {
        if (diet_id == null) { // NO poner boton conectar
            // value is a String and is not “false”, “off” or “no”
            modelMap.addAttribute("diet_id", "false");
        } else {    // poner el botón de LINK
            modelMap.addAttribute("diet_id", diet_id);
        }
        
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("recipes", recipeService.listRecipesByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_RECIPES;
    }

    @GetMapping("/link-recipe-diet/{diet_id}/{recipe_id}")
    public String linkRecipeLinksWithDiet(@PathVariable String diet_id, @PathVariable String recipe_id) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        dietService.RecipesLinksWithDiet(diet_id, recipe_id, userPrincipal.getUsername());
        return "redirect:/professionals/professional-basic/" + URL_MY_DIETS;
    }

    @GetMapping("/" + URL_ADD_RECIPE)
    public String addNewRecipe(Model model) {
        RecipeDAO new_recipe = new RecipeDAO();
        model.addAttribute("new_recipe", new_recipe);
        return DIRECCION_BASE + PAGE_ADD_RECIPE; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_RECIPE)
    public String saveRecipe(@ModelAttribute("new_recipe") RecipeDAO recipeDAO) throws IOException{
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        recipeService.saveNewRecipe(recipeDAO, userPrincipal.getUsername());
        return "redirect:" + URL_MY_RECIPES;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    @GetMapping("/delete-recipe/{id}")
	public String deleteRecipe(@PathVariable(value="id") Long recipe_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.deleteRecipe(recipe_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_RECIPES;
	}

    /* ********************************************************************* */
    /* ******************** DIETS ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_DIETS, "/" + URL_MY_DIETS + "{client_user_name}"})  // hacemos opcional el parámetro
    public String listMyDiets(@PathVariable(required = false) String client_user_name, ModelMap modelMap) {
        if (client_user_name == null) { // NO poner boton conectar
            // value is a String and is not “false”, “off” or “no”
            modelMap.addAttribute("client_user_name", "false");
        } else {    // poner el botón de LINK
            modelMap.addAttribute("client_user_name", client_user_name);
        }
        
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("diets_list", dietService.listDietsByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_DIETS;
    }

    @GetMapping("/link-diet-client/{client_user_name}/{diet_id}")
    public String linkDietToClient(@PathVariable String client_user_name, @PathVariable String diet_id) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.DietLinksToClient(client_user_name, diet_id, userPrincipal.getUsername());
        return "redirect:/professionals/professional-basic/" + URL_MY_CLIENTS;
    }

    @GetMapping("/" + URL_ADD_DIET)
    public String addNewDiet(Model model) {
        DietDAO new_diet = new DietDAO();
        model.addAttribute("new_diet", new_diet);
        return DIRECCION_BASE + PAGE_ADD_DIET; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_DIET)
    public String saveDiet(@ModelAttribute("new_diet") DietDAO dietDAO) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        dietService.saveNewDiet(dietDAO, userPrincipal.getUsername());
        return "redirect:" + URL_MY_DIETS;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    @GetMapping("/delete-diet/{id}")
	public String deleteDiet(@PathVariable(value="id") Long diet_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.deleteDiet(diet_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_DIETS;
	}

    /* ********************************************************************* */
    /* ******************** ROUTINES ****************** */
    /* ********************************************************************* */

    @GetMapping({"/" + URL_MY_ROUTINES, "/" + URL_MY_ROUTINES + "{param}"})  // hacemos opcional el parámetro
    public String listMyRoutines(@PathVariable(required = false) String param, ModelMap modelMap) {
        if (param == null) {
            modelMap.addAttribute("diet_id", "false");
            modelMap.addAttribute("client_user_name", "false");
        } else {
            if (param.indexOf('=') != -1) {
                String part[] = param.split("=");
                modelMap.addAttribute("diet_id", part[1]);
            } else {
                modelMap.addAttribute("client_user_name", param);
            }
        }
        
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        modelMap.addAttribute("routines", routineService.listRoutinesByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_ROUTINES;
    }

    @GetMapping("/link-routine-client/{client_user_name}/{routine_id}")
    public String linkRoutineToClient(@PathVariable String client_user_name, @PathVariable String routine_id) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.RoutineLinksToClient(client_user_name, routine_id, userPrincipal.getUsername());
        return "redirect:/professionals/professional-basic/" + URL_MY_CLIENTS;
    }

    @GetMapping("/" + URL_ADD_ROUTINE)
    public String addNewRoutine(Model model) {
        RoutineDAO new_routine = new RoutineDAO();
        model.addAttribute("new_routine", new_routine);
        return DIRECCION_BASE + PAGE_ADD_ROUTINE; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_ROUTINE)
    public String saveRoutine(@ModelAttribute("new_routine") RoutineDAO routineDAO) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        routineService.saveNewRoutine(routineDAO, userPrincipal.getUsername());
        return "redirect:" + URL_MY_ROUTINES;    // nos REDIRECCIONA a la pagina con la lista de clientes
    }

    @GetMapping("/delete-routine/{id}")
	public String deleteRoutine(@PathVariable(value="id") Long routine_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.deleteRoutine(routine_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_ROUTINES;
	}

    /* ********************************************************************* */
    /* ******************** CLIENTS ****************** */
    /* ********************************************************************* */
    @GetMapping("/" + URL_MY_CLIENTS)
    public String listMyClients(Model model) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        model.addAttribute("clients_list", clientsService.listClientsByProfessional(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_CLIENTS;
    }

    @GetMapping("/" + URL_ADD_CLIENT)
    public String addNewClient(Model model) {
        ClientDAO new_client = new ClientDAO();
        model.addAttribute("new_client", new_client);
        return DIRECCION_BASE + PAGE_ADD_CLIENT; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_CLIENT)
    public String saveClient(@ModelAttribute("new_client") ClientDAO clientDAO) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.ProfessionalLinksWithClient(clientDAO, userPrincipal.getUsername());   // user_name cliente + user_name actual
        return "redirect:" + URL_MY_CLIENTS;    // nos REDIRECCIONA a la pagina con todas las rutinas
    }

    @GetMapping("/delete-client/{id}")
	public String deleteClient(@PathVariable(value="id") Long client_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        professionalsService.ProfessionalDeLinksWithClient(client_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_CLIENTS;
	}
}