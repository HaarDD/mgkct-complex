package by.haardd.cclog.repository;

import by.haardd.cclog.entity.UserStatus;
import by.haardd.cclog.entity.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    Optional<UserStatus> findByName(UserStatusEnum userStatusEnum);

}