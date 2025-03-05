package xyz.tomorrowlearncamp.outsourcing.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    default boolean existsByEmail(String email) {
        return existsAllByEmail(email) > 0;
    }

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)", nativeQuery = true)
    Integer existsAllByEmail(@Param("email") String email);

    Optional<UserEntity> findByEmail(String email);
}
