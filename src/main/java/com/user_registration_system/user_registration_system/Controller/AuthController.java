package com.user_registration_system.user_registration_system.Controller;

import com.user_registration_system.user_registration_system.Entity.User;
import com.user_registration_system.user_registration_system.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        User userExisting = userService.getUserByEmail(user.getEmail());
        if(userExisting != null){
            bindingResult.rejectValue("email",null, "email.exists");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("login")
    public String login(Model model){
        return "login";
    }

}
