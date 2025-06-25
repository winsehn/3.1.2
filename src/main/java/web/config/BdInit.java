package web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BdInit(UserService userService) {
        this.userService = userService;
    }

    private void testUsers() {
        User victor = new User("Виктор", "Петров", (byte) 25, "Мужской");
        User petr = new User("Petr", "Stepanov", (byte) 44, "Male");
        User vasiliy = new User("Vasiliy", "Krasnov", (byte) 31, "Male");
        User anastasya = new User("Anastasya", "Kuznecova", (byte) 53, "Female");
        users = new ArrayList<>();
        users.add(victor);
        users.add(petr);
        users.add(vasiliy);
        users.add(anastasya);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        testUsers();
        for (User user : users) {
            userService.addUser(user);
        }
    }
}