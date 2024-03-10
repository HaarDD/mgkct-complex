package by.haardd.cclog.repository;

import by.haardd.cclog.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}