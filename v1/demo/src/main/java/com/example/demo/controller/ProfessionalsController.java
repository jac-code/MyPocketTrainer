package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.service.DietService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RoutineService;

@Controller
@RequestMapping("professionals/professional-basic")
public class ProfessionalsController {
    public static final String DIRECCION_BASE = "hub/professional/";

    public static final String PAGE_MY_CLIENTS = "my-clients";
    public static final String PAGE_MY_DIETS = "my-diets";
	public static final String PAGE_MY_ROUTINES = "my-routines";
    public static final String PAGE_ADD_DIET = "my-diets-add-diet";
    public static final String PAGE_ADD_ROUTINE = "my-routines-add-routine";

    public static final String URL_MY_CLIENTS = "my-clients";
    public static final String URL_MY_DIETS = "my-diets";
    public static final String URL_MY_ROUTINES = "my-routines";
    public static final String URL_ADD_DIET = "add-diet";
    public static final String URL_ADD_ROUTINE = "add-routine";
    public static final String URL_SAVE_DIET = "save-diet";
    public static final String URL_SAVE_ROUTINE = "save-routine";
    
    @Autowired
    ProfessionalsService professionalsService;

    @Autowired
    DietService dietService;

    @Autowired
    RoutineService routineService;

    @GetMapping("/home")
    public String showProfessionalsBasicPage(ModelAndView modelAndView) {
        return DIRECCION_BASE + "professional-basic";
    }

    /* ********************************************************************* */
    /* ******************** CLIENTS ****************** */
    /* ********************************************************************* */
    
    @GetMapping("/" + URL_MY_CLIENTS)
    public String listMyClients(Model model) {
        model.addAttribute("diets", dietService.listDietsByProfessional());
        return DIRECCION_BASE + PAGE_MY_CLIENTS;
    }


    /* ********************************************************************* */
    /* ******************** DIETS ****************** */
    /* ********************************************************************* */

    // PROFESIONAL LISTA TODAS SUS DIETAS
    @GetMapping("/" + URL_MY_DIETS)
    public String listMyDiets(Model model) {
        model.addAttribute("diets", dietService.listDietsByProfessional());
        return DIRECCION_BASE + PAGE_MY_DIETS;
    }

    @GetMapping("/" + URL_ADD_DIET)
    public String addNewDiet(Model model) {
        DietDAO new_diet = new DietDAO();
        model.addAttribute("new_diet", new_diet);
        return DIRECCION_BASE + PAGE_ADD_DIET; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_DIET)
    public String saveDiet(@ModelAttribute("new_diet") DietDAO dietDAO) {
        dietService.saveNewDiet(dietDAO);
        return "redirect:" + URL_MY_DIETS;    // nos REDIRECCIONA a la pagina con todas las dietas
    }

    /* ********************************************************************* */
    /* ******************** ROUTINES ****************** */
    /* ********************************************************************* */

    @GetMapping("/" + URL_MY_ROUTINES)
    public String listMyRoutines(Model model) {
        model.addAttribute("routines", routineService.listRoutinesByProfessional());
        return DIRECCION_BASE + PAGE_MY_ROUTINES;
    }

    @GetMapping("/" + URL_ADD_ROUTINE)
    public String addNewRoutine(Model model) {
        RoutineDAO new_routine = new RoutineDAO();
        model.addAttribute("new_routine", new_routine);
        return DIRECCION_BASE + PAGE_ADD_ROUTINE; // pagina html con formulario
    }

    @PostMapping("/" + URL_SAVE_ROUTINE)
    public String saveRoutine(@ModelAttribute("new_routine") RoutineDAO routineDAO) {
        routineService.saveNewRoutine(routineDAO);
        return "redirect:" + URL_MY_ROUTINES;    // nos REDIRECCIONA a la paginA con todas las rutinas
    }
}