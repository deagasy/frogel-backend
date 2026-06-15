package tinywins_backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
    List<GoalEntity> findAllByOwnerOrderByIdAsc(UserEntity owner);
    Optional<GoalEntity> findByIdAndOwner(Long id, UserEntity owner);
}