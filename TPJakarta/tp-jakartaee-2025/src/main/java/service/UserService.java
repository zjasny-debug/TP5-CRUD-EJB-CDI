package service;

import entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.List;

@ApplicationScoped
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public Long createUser(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findUserById(Long id) {
        return em.find(User.class, id);
    }

    public User updateUser(User user) {
        return em.merge(user);
    }

    public boolean deleteUserById(Long id) {
        User u = em.find(User.class, id);
        if (u == null) return false;
        em.remove(u);
        return true;
    }

    public List<User> findAllUser() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList();
    }
}
