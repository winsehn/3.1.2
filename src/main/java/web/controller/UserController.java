package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    @GetMapping(value = "/user")
    public String user(Model model, @AuthenticationPrincipal User user) {
        System.out.println("USER: " + user);
        if (user == null) {
            throw new RuntimeException("Пользователь не авторизован");
        }
        model.addAttribute("user", user);
        return "user";
    }
}
