package web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.model.User;
import web.repository.RoleRepository;
import web.model.Role;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class BdInit implements InitializingBean {
    private List<User> users;
    private Random rand = new Random();

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BdInit(UserService userService, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    private void testUsers() {
        User user = new User("user_name", "user_second_name", (byte) 25, "user_sex");
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("user"));

        User admin = new User("admin", "admin", (byte) 1, "admin");
        admin.setUserName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));

        Role roleUser = new Role("ROLE_USER");
        roleUser.setRedirect("/user");

        Role roleAdmin = new Role("ROLE_ADMIN");
        roleAdmin.setRedirect("/admin");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        user.getRoles().add(roleUser);

        users = new ArrayList<>();
        users.add(user);
        users.add(admin);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testUsers();
        for (User user : users) {
            userService.addUser(user);
        }
    }
}