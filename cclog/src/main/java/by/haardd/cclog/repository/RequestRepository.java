package by.haardd.cclog.repository;

import by.haardd.cclog.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}