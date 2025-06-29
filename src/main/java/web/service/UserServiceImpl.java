package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.User;
import web.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void addUser(User user) {
        usersRepository.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        usersRepository.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    @Override
    public Optional<User> findByUserName(String userName){
        return usersRepository.findByUserName(userName);
    }
}
