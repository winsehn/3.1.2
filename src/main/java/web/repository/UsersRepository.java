package web.repository;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> getAllUsers();

    List<User> getAllUsersWithRole();

    Optional<User> findByUserName(String userName);
}
