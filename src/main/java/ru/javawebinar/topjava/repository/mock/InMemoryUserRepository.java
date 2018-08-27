package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public static final Comparator<User> USER_NAME_EMAIL_COMP =
            Comparator.comparing(User::getName).thenComparing(User::getEmail);

    public InMemoryUserRepository(){
        save(new User(2, "admin", "admin@gmail.com", "adminPassword", Role.ROLE_ADMIN));
        save(new User(1, "user", "user@gmail.com", "userPassword", Role.ROLE_USER));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew()) {
            user.setId(id.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> result = new ArrayList<>(repository.values());
        result.sort(USER_NAME_EMAIL_COMP);
        return result;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElseThrow(NullPointerException::new);

    }
}
