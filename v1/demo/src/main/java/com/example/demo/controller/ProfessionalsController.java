package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.model.ModelUser;
import com.example.demo.model.ModelUserDetails;
import com.example.demo.security.IAuthenticationFacade;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DietService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RoutineService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Controller
@RequestMapping("professionals/professional-basic")
public class ProfessionalsController {
    public static final String DIRECCION_BASE = "hub/professional/";

    public static final String PAGE_MY_CLIENTS = "my-clients";
    public static final String PAGE_MY_DIETS = "my-diets";
	public static final String PAGE_MY_ROUTINES = "my-routines";
    public static final String PAGE_ADD_DIET = "my-diets-add-diet";
    public static final String PAGE_ADD_ROUTINE = "my-routines-add-routine";
    public static final String PAGE_ADD_CLIENT = "my-clients-add-client";

    public static final String URL_MY_CLIENTS = "my-clients";
    public static final String URL_ADD_CLIENT = "add-client";
    public static final String URL_SAVE_CLIENT = "save-client";
    public static final String URL_MY_DIETS = "my-diets";
    public static final String URL_MY_ROUTINES = "my-routines";
    public static final String URL_ADD_DIET = "add-diet";
    public static final String URL_ADD_ROUTINE = "add-routine";
    public static final String URL_SAVE_DIET = "save-diet";
    public static final String URL_SAVE_ROUTINE = "save-routine";
    
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
    private IAuthenticationFacade authenticationFacade;

    // @GetMapping("/home")
    // public String showProfessionalsBasicPage(ModelAndView modelAndView) {
    //     return DIRECCION_BASE + "professional-basic";
    // }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // String userEmail = userDetails.getEmail();
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        // ModelUserDetails userPrincipal = (ModelUserDetails)authentication.getPrincipal();
        ModelUser user = (modelUserService.getModelUserByUsername(userPrincipal.getUsername()));
         
        model.addAttribute("user", user);
         
        return DIRECCION_BASE + "professional-basic";
    }

    // @GetMapping("/home")
    // @ResponseBody
    // public String currentUserNameSimple() {
    //     Authentication authentication = authenticationFacade.getAuthentication();
    //     return authentication.getName();
    // }

    /* ********************************************************************* */
    /* ******************** DIETS ****************** */
    /* ********************************************************************* */

    // PROFESIONAL LISTA TODAS SUS DIETAS
    @GetMapping("/" + URL_MY_DIETS)
    public String listMyDiets(Model model) {
        // para saber eel usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        model.addAttribute("diets_list", dietService.listDietsByProfessional(userPrincipal.getUsername()));
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

    @GetMapping("/" + URL_MY_ROUTINES)
    public String listMyRoutines(Model model) {
        // para saber el usuario que está dentro
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        // vamos a buscar por user_name
        model.addAttribute("routines", routineService.listRoutinesByProfessional(userPrincipal.getUsername()));
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
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();

        routineService.saveNewRoutine(routineDAO, userPrincipal.getUsername());
        return "redirect:" + URL_MY_DIETS;    // nos REDIRECCIONA a la pagina con la lista de clientes
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

    @GetMapping("/set-diet/{id}")
	public String setDiet(@PathVariable(value="id") Long client_id) {

		return "redirect:../" + URL_MY_DIETS;
	}

    @GetMapping("/set-routine/{id}")
	public String setRoutine(@PathVariable(value="id") Long client_id) {

		return "redirect:../" + URL_MY_ROUTINES;
	}
}