package gugunava.danil.usermanagementservice.repository;

import gugunava.danil.usermanagementservice.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity> {
}
