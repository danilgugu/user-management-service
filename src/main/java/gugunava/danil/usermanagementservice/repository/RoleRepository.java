package gugunava.danil.usermanagementservice.repository;

import gugunava.danil.usermanagementservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(nativeQuery = true, value = "select r.id, r.name, r.scope from users_schema.role r join users_schema.user_role ur on r.id = ur.role_id where ur.user_id = :userId")
    List<RoleEntity> findAllByUserId(long userId);
}
