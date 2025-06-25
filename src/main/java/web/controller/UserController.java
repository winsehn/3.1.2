package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping(value = "/users")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("editingId", null);
        return "users";
    }

    @PostMapping(value = "/users/create")
    public String addUser(@RequestParam(name = "name") String name,
                          @RequestParam(name = "second_name") String second_name,
                          @RequestParam(name = "years") Byte years,
                          @RequestParam(name = "sex") String sex) {
        userService.addUser(new User(name, second_name, years, sex));
        return "redirect:/users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping(value = "/users/edit")
    public String editUser(Model model, @RequestParam(name = "id") Long id) {
        model.addAttribute("editingId", id);
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @PostMapping(value = "/users/update")
    public String updateUser(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "second_name", required = false) String second_name,
                             @RequestParam(name = "years", required = false) Byte years,
                             @RequestParam(name = "sex", required = false) String sex) {
        User user = new User(name, second_name, years, sex);
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/users";
    }
}
