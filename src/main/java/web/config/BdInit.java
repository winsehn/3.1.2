package web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class BdInit implements InitializingBean {
    private List<User> users;
    private Random rand = new Random();
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BdInit(UserService userService) {
        this.userService = userService;
    }

    private void testUsers() {
        User user = new User("user_name", "user_second_name", (byte) 25, "user_sex");
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("user"));

        User admin = new User("admin", "admin", (byte) 1, "admin");
        admin.setUserName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));

        userService.setRoleForUser(admin,"ROLE_ADMIN","/admin");
        userService.setRoleForUser(admin,"ROLE_USER","/user");
        userService.setRoleForUser(admin,"ROLE_GUEST","/guest");
        userService.setRoleForUser(user,"ROLE_USER","/user");
        userService.setRoleForUser(user,"ROLE_GUEST","/guest");

        users = new ArrayList<>();
        users.add(admin);
        users.add(user);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testUsers();
        for (User user : users) {
            userService.addUser(user);
        }
    }
}