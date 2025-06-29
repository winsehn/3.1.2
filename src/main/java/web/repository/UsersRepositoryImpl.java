package web.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User findUser = em.find(User.class, id);
        if (findUser != null) {
            em.remove(findUser);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        Query query = em.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<User> findByUserName(String userName) {
        return em.createQuery("FROM User u WHERE u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getResultList()
                .stream()
                .findFirst();
    }
}
