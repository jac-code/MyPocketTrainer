// package com.example.demo.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.context.request.WebRequest;
// import org.springframework.web.servlet.ModelAndView;

// import java.util.Calendar;
// import java.util.Locale;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;
// import javax.validation.Valid;

// import com.example.demo.exceptions.UserAlreadyExistsException;
// import com.example.demo.model.Client;
// import com.example.demo.model.VerificationToken;
// // import com.example.demo.registration.OnRegistrationCompleteEvent;
// import com.example.demo.service.ClientsService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationEventPublisher;
// import org.springframework.context.MessageSource;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.Errors;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;

// @Controller
// @RequestMapping("clients")
// public class ClientsController {

//     @Autowired
//     private ClientsService clientsService;

//     @Autowired
//     private ApplicationEventPublisher eventPublisher;

//     @Autowired
//     private MessageSource messages;

//     @GetMapping("/home")
//     public String ClientsHomePage() {
//         return "/hub/index";
//     }

//     @GetMapping("/my-recipe-finder")
//     public String ClientsMyRecipeFinder() {
//         return "/hub/myRecipeFinder";
//     }

//     @GetMapping("/sign-up-client")
//     public String SignUpClientPage(Model model) {
//         model.addAttribute("client", new Client());
//         return "sign-up-client";
//     }

//     @PostMapping("/sign-up-validation")
//     public String RegisterClientAccount(Client client, HttpServletRequest request) {
//         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//         String encodePassword = encoder.encode(client.getPassword());
//         client.setPassword(encodePassword);
//         try {
//             Client c = clientsService.signUpNewClient(client);
//         } catch (UserAlreadyExistsException uaeEx) {
//             new UserAlreadyExistsException("message");
//             return "sign-up-client";
//         }

//         HttpSession session = request.getSession();
//         session.setAttribute("username", client.getUser_name());

//         return "/admin/index";
//     }

//     // @PostMapping("/sign-up-validation")
//     // public ModelAndView registerUserAccount(@ModelAttribute("client") @Valid Client client, HttpServletRequest request, Errors errors) {
//     //     try {
//     //         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//     //         String encodePassword = encoder.encode(client.getPassword());
//     //         client.setPassword(encodePassword);

//     //         Client registered = clientsService.signUpNewClient(client);

//     //         String appUrl = request.getContextPath();
//     //         eventPublisher.publishEvent(new OnRegistrationCompleteEvent((User)registered, request.getLocale(), appUrl));
//     //     } catch (UserAlreadyExistsException uaeEx) {
//     //         ModelAndView mav = new ModelAndView("registration", "client", client);
//     //         mav.addObject("message", "An account for that username/email already exists.");
//     //         return mav;
//     //     } catch (RuntimeException ex) {
//     //         return new ModelAndView("emailError", "client", client);
//     //     }

//     //     return new ModelAndView("successRegister", "client", client);
//     // }

//     // @GetMapping("/sign-up-confirmation")
//     // public String confirmRegistration (WebRequest request, Model model, @RequestParam("token") String token) {
//     //     Locale locale = request.getLocale();
        
//     //     VerificationToken verificationToken = clientsService.getVerificationToken(token);
//     //     if (verificationToken == null) {
//     //         String message = messages.getMessage("auth.message.invalidToken", null, locale);
//     //         model.addAttribute("message", message);
//     //         return "redirect:/bad-user.html?lang=" + locale.getLanguage();
//     //     }
        
//     //     Client client = (Client)verificationToken.getUser();
//     //     Calendar cal = Calendar.getInstance();
//     //     if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//     //         String messageValue = messages.getMessage("auth.message.expired", null, locale);
//     //         model.addAttribute("message", messageValue);
//     //         return "redirect:/bad-user.html?lang=" + locale.getLanguage();
//     //     } 
        
//     //     client.setEnabled(true); 
//     //     clientsService.saveRegisteredUser(client);
//     //     return "redirect:/sign-in.html?lang=" + request.getLocale().getLanguage(); 
//     // }
// }

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.service.ClientsService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;

@Controller
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    ClientsService clientsService;

    @Autowired
    ProfessionalsService professionalsService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelUserService modelUserService;

    @GetMapping("/client-free/home")
    public String showClientsFreePage(ModelAndView modelAndView) {
        return "hub/client/client-free";
    }

    @GetMapping("/client-free/my-recipe-finder")
    public String showClientsMyRecipeFinderFreePage(ModelAndView modelAndView) {
        return "hub/client/my-recipe-finder-free";
    }

    @GetMapping("/client-premium/my-recipe-finder")
    public String showClientsMyRecipeFinderPremiumPage(ModelAndView modelAndView) {
        return "hub/client/my-recipe-finder-premium";
    }

    // @GetMapping("/client-free/my-recipe-finder")
    // public String showClientsMyExerciseFinderPage(ModelAndView modelAndView) {
    //     return "hub/client/my-exercise-finder";
    // }

    @GetMapping("/client-premium/home")
    public String showClientsPremiumPage(ModelAndView modelAndView) {
        return "hub/client/client-premium";
    }
}