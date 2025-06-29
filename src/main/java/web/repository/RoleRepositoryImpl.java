package web.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    @Transactional
    public Optional<Role> findByName(String name) {
      return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
              .setParameter("name", name)
              .getResultStream()
              .findFirst();
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

}
