package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.service.ClientsService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;

@Controller
public class MainController {
    private static final String REDIRECT = "redirect:";
    private static final String REDIRECT_LOGIN= "redirect:/sign-in";

    @Autowired
    ClientsService clientsService;

    @Autowired
    ProfessionalsService professionalsService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelUserService modelUserService;

    @GetMapping("")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/sign-in")
    public String showSignInPage(@RequestParam(value = "error", defaultValue = "false") boolean loginError) {
        if (loginError) {
            // custom error handling
        }
        return "signin";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        UserDAO userDAO = new UserDAO();
        model.addAttribute("userDAO", userDAO);
        return "signup";
    }

    @PostMapping("/sign-up")
    public String registerModelUserAccount(final @Valid  UserDAO userDAO, final BindingResult bindingResult, final Model model) {
        String url = "";

        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userDAO);
            return REDIRECT + "/sign-up";
        }
        try {
            switch (userDAO.getRole_description_form()){
                case "ROLE_CLIENT_FREE":
                    clientsService.signUpNewClient(userDAO);
                    url = "/clients/client-free/home";
                    break;
                case "ROLE_CLIENT_PREMIUM":
                    clientsService.signUpNewClient(userDAO);
                    url = "/clients/client-premium/home";
                    break;
                case "ROLE_PROFESSIONAL_BASIC":
                    professionalsService.signUpNewProfessional(userDAO);
                    url = "/professionals/professional-basic/home";
                    break;
                case "ROLE_PROFESSIONAL_BUSINESS":
                    professionalsService.signUpNewProfessional(userDAO);
                    url = "/professionals/professional-business/home";
                    break;
                default:
                    break;
            }
        } catch (UserAlreadyExistsException e){
            bindingResult.rejectValue("email", "userDAO.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userDAO);
            return REDIRECT + "/sign-up";
        }
        return REDIRECT + url;
    }

    @GetMapping("/sign-up/verification")
    public String verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){
        if(token.isEmpty()){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        try {
            modelUserService.verifyUser(token);
        } catch (InvalidVerificationTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null, LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }
}