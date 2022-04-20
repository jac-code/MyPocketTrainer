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
import com.example.demo.model.Recipe;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DailyService;
import com.example.demo.service.DietService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.WeeklyService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


@Controller
@RequestMapping("clients/client-free")
public class ClientsController {
    public static final String DIRECCION_BASE = "hub/client/";

    public static final String PAGE_MY_PROFESSIONALS= "my-professionals";
    public static final String PAGE_ADD_PROFESSIONAL = "my-professionals-add-professional";

    public static final String PAGE_MPT_FINDER = "my-mpt-finder";
    public static final String PAGE_MPT_FINDER_RESULTS = "my-mpt-finder-results";
    public static final String PAGE_PROFESSIONAL_DATA = "my-mpt-finder-professional-data";

    public static final String URL_MY_PROFESSIONALS = "my-professionals";
    public static final String URL_ADD_PROFESSIONAL = "add-professional";
    public static final String URL_SAVE_PROFESSIONAL = "save-professional";

    public static final String URL_MPT_FINDER = "my-mpt-finder";
    public static final String URL_MPT_FINDER_RESULTS = "my-mpt-finder-results";
    public static final String URL_MPT_FINDER_ADD_DIET = "add-professional-diet";
    public static final String URL_MPT_FINDER_ADD_ROUTINE = "add-professional-routine";
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    ClientsService clientsService;

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

    @Autowired
    private MessageSource messageSource;

    public String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        String week_day = "";

        switch (i) {
            case 1:
                week_day = "SUNDAY";
                break;
            case 2:
                week_day = "MONDAY";
                break;
            case 3:
                week_day = "TUESDAY";
                break;
            case 4:
                week_day = "WEDNESDAY";
                break;
            case 5:
                week_day = "THURSDAY";
                break;
            case 6:
                week_day = "FRIDAY";
                break;
            case 7:
                week_day = "SATURDAY";
                break;
            
            default:
                break;
        }

        return week_day;
    }

    @GetMapping("/home")
    public String showHomePage(ModelMap modelMap) { // el ModelMap es para poder pasar distintos obejetos
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        List<Daily> dailies = clientsService.getClientByUsername(userPrincipal.getUsername()).getWeekly().getDailies();

        modelMap.addAttribute("dailies", dailies);
        modelMap.addAttribute("week_day", getDayOfWeek());
        
        for (Daily daily : dailies) {
            if (getDayOfWeek().equals(daily.getWeek_day())) {
                List<Recipe> recipes = daily.getDiet().getRecipes();
                for(Recipe r : recipes) {
                    r.setImageBase64(r.getImage());
                }

                List<Exercise> exercises = daily.getRoutine().getExercises();
                for(Exercise e : exercises) {
                    e.setImageBase64(e.getImage());
                }

                modelMap.addAttribute("recipes", recipes);
                modelMap.addAttribute("exercises", exercises);
            }
        }
        
        return DIRECCION_BASE + "client-free";
    }
    
    /* ********************************************************************* */
    /* ******************** PROFESSIONALS ****************** */
    /* ********************************************************************* */

    @GetMapping("/" + URL_MY_PROFESSIONALS)
    public String listMyProfessionals(Model model) {
        // para saber eel usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        model.addAttribute("professionals_list", professionalsService.listProfessionalsByClient(userPrincipal.getUsername()));
        return DIRECCION_BASE + PAGE_MY_PROFESSIONALS;
    }

    @GetMapping("/" + URL_ADD_PROFESSIONAL)
    public String addNewProfessional(Model model) {
        ProfessionalDAO new_professional = new ProfessionalDAO();
        model.addAttribute("new_professional", new_professional);
        return DIRECCION_BASE + PAGE_ADD_PROFESSIONAL; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_PROFESSIONAL)
    public String saveProfessional(@ModelAttribute("new_professional") ProfessionalDAO new_professional) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.ClientLinksWithProfessional(new_professional, userPrincipal.getUsername());
        return "redirect:" + URL_MY_PROFESSIONALS;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    @GetMapping("/delete-professional/{id}")
	public String deleteClient(@PathVariable(value="id") Long professional_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.ClientDeLinksWithProfessional(professional_id, userPrincipal.getUsername());

		return "redirect:../" + URL_MY_PROFESSIONALS;
	}

    /* ********************************************************************* */
    /* ******************** RECIPES ****************** */
    /* ********************************************************************* */

    @GetMapping("/my-recipe-finder")
    public String showClientsMyRecipeFinderFreePage(ModelAndView modelAndView) {
        return DIRECCION_BASE + "my-recipe-finder";
    }

    /* ********************************************************************* */
    /* ******************** EXERCISES ****************** */
    /* ********************************************************************* */
    
    @GetMapping("/my-exercise-finder")
    public String showClientsMyExerciseFinderPage(ModelAndView modelAndView) {
        return DIRECCION_BASE + "my-exercise-finder";
    }

    /* ********************************************************************* */
    /* ******************** MPT - FINDER ****************** */
    /* ********************************************************************* */
    
    @GetMapping("/" + URL_MPT_FINDER)
    public String showMPTFinderPage(Model model) {
        UserFinderDAO new_user = new UserFinderDAO();
        model.addAttribute("new_user", new_user);
        return DIRECCION_BASE + PAGE_MPT_FINDER;
    }

    @GetMapping("/" + URL_MPT_FINDER + "/" + "{professional_id}")
    public String showProfessionalInfo(@PathVariable(required = true) String professional_id, ModelMap modelMap) {
        // primero comprobamos si tiene perfil público
        Professional p = professionalsService.getProfessionalById(Long.parseLong(professional_id));
        if (p.isPublic()) {
            // es PÚBLICO
            modelMap.addAttribute("isPublic", "true");
            modelMap.addAttribute("isPrivate", "false");
            modelMap.addAttribute("professional_username", p.getUser_name());
            modelMap.addAttribute("routines", routineService.listRoutinesByProfessional(p.getUser_name()));
            modelMap.addAttribute("diets", dietService.listDietsByProfessional(p.getUser_name()));
        } else {
            // es PRIVADO
            modelMap.addAttribute("isPublic", "false");
            modelMap.addAttribute("isPrivate", "true");
            modelMap.addAttribute("professional_username", p.getUser_name());
        }

        return DIRECCION_BASE + PAGE_PROFESSIONAL_DATA;
    }

    @GetMapping("/" + URL_MPT_FINDER_RESULTS)
    public String listSearchedUsers(UserFinderDAO new_user, Model model) {
        switch (new_user.getUser_type()) {
            case "CLIENT":
                model.addAttribute("user", clientsService.getClientByUsername(new_user.getParameter()));
                break;
            case "PROFESSIONAL":
                model.addAttribute("user", professionalsService.getProfessionalByUsername(new_user.getParameter()));
                break;
            default:
                break;
        }

        return DIRECCION_BASE + PAGE_MPT_FINDER_RESULTS;
    }

    @GetMapping("/" + URL_MPT_FINDER_ADD_DIET + "/" + "{diet_id}")
    public String addDietFromProfessional(@PathVariable(required = false) String diet_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.ClientFollowsDiets(diet_id, userPrincipal.getUsername());

        return "redirect:/clients/client-free/" + "home";
    }

    @GetMapping("/" + URL_MPT_FINDER_ADD_ROUTINE + "/" + "{routine_id}")
    public String addRoutineFromProfessional(@PathVariable(required = false) String routine_id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        clientsService.ClientFollowsRoutines(routine_id, userPrincipal.getUsername());

        return "redirect:/clients/client-free/" + "home";
    }
}