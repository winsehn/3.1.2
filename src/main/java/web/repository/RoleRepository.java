package web.repository;

import web.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    void save(Role role);
    Optional<Role> findByName(String name);
    List<Role> findAll();
}
