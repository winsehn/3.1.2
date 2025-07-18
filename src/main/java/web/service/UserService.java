package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> getAllUsers();

    List<User> getAllUsersWithRole();

    Optional<User> findByUserName(String userName);

    void setRoleForUser(User user, String roleName, String redirect);
}
