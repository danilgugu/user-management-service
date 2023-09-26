package gugunava.danil.usermanagementservice.repository;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
}
