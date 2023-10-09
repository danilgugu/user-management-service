package gugunava.danil.usermanagementservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleEntity.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity implements Serializable {

    @Id
    @Column(nullable = false)
    Long userId;

    @Id
    @Column(nullable = false)
    Long roleId;
}
