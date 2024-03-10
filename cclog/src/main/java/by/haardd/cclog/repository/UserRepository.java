package by.haardd.cclog.repository;

import by.haardd.cclog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}