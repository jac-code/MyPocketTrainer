// package com.example.demo.controller;

// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.demo.exceptions.UserAlreadyExistsException;
// import com.example.demo.model.Professional;
// import com.example.demo.service.ProfessionalsService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;

// @Controller
// @RequestMapping("professionals")
// public class ProfessionalsController {
//     @Autowired
//     ProfessionalsService professionalsService;

//     @GetMapping("/home")
//     public String ClientsHomePage() {
//         return "hub/index";
//     }

//     // @GetMapping("/signup")
//     // public String TrainerSignUpPage(Model model) {
//     //     model.addAttribute("trainer", new Trainer());
//     //     return "trainers/trainer_signup";
//     // }

//     @GetMapping("/sign-up-trainer")
//     public String SignUpClientPage(Model model) {
//         model.addAttribute("trainer", new Professional());
//         return "sign-up-professional";
//     }
    
//     @PostMapping("/sign-up-validation")
// 	    public String processRegistration(Professional professional) {
// 		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
// 		String encodePassword = encoder.encode(professional.getPassword());
// 		professional.setPassword(encodePassword);

//         try {
//             Professional p = professionalsService.signUpNewProfessional(professional);
//         } catch (UserAlreadyExistsException uaeEx) {
//             return "sign-up-professional";
//         }

//         return "team";
// 	}
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
@RequestMapping("professionals")
public class ProfessionalsController {

    @Autowired
    ProfessionalsService professionalsService;

    @GetMapping("/professional-basic/home")
    public String showProfessionalsBasicPage(ModelAndView modelAndView) {
        return "hub/professional/professional-basic";
    }

    @GetMapping("/professional-business/home")
    public String showProfessionalsBusinessPage(ModelAndView modelAndView) {
        return "hub/professional/professional-business";
    }
}