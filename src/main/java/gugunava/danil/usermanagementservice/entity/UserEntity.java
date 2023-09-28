package gugunava.danil.usermanagementservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "_user")
@SequenceGenerator(name = "user_gen", sequenceName = "user_id_seq")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    Long id;

    @Column(nullable = false)
    String userName;

    @Column(nullable = false, unique = true)
    String email;

    @JsonIgnore
    @Column(nullable = false, length = 64)
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
