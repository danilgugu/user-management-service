package gugunava.danil.usermanagementservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "_user")
@SequenceGenerator(name = "user_gen", sequenceName = "user_id_seq")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    Long id;

    @NotBlank
    String userName;

    @NotBlank
    String email;

    @NotBlank
    String password;

    public static UserEntity createNew(
            String userName,
            String email,
            String password
    ) {
        return new UserEntity(null, userName, email, password);
    }

    public static UserEntity buildExisting(
            long id,
            String userName,
            String email,
            String password
    ) {
        return new UserEntity(id, userName, email, password);
    }
}
