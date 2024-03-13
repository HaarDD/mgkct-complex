package by.haardd.cclog.repository;

import by.haardd.cclog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByRefreshToken(String refreshToken);

    Boolean existsByLogin(String login);
}