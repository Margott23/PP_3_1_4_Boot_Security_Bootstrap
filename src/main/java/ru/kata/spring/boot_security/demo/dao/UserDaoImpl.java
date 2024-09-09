package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User updateUser) {
        User user = entityManager.merge(updateUser);
        entityManager.persist(user);
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.login = :username");
        query.setParameter("username", login);
        return Optional.ofNullable((User) query.getSingleResult());

    }
}
