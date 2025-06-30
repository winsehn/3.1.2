package web.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        usersRepository.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        usersRepository.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        usersRepository.updateUser(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    @Transactional
    public List<User> getAllUsersWithRole() {
        return usersRepository.getAllUsersWithRole();
    }

    @Override
    @Transactional
    public Optional<User> findByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public void setRoleForUser(User user, String roleName, String redirect) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        Role role;
        if (optionalRole.isPresent()) {
            role = optionalRole.get();
        } else {
            role = new Role(roleName);
            role.setRedirect(redirect);
        }
        user.getRoles().add(role);
    }
}
