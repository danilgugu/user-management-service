package gugunava.danil.usermanagementservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@SequenceGenerator(name = "role_gen", sequenceName = "role_id_seq")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String scope;
}
