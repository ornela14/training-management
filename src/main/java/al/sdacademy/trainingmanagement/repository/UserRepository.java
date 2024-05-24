package al.sdacademy.trainingmanagement.repository;

import al.sdacademy.trainingmanagement.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseJpaRepository<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
}
