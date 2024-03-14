package by.haardd.cclog.repository;

import by.haardd.cclog.entity.Status;
import by.haardd.cclog.entity.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByName(StatusEnum roleNameEnum);

}