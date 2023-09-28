package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.entity.UserEntity;

import java.util.List;

public class UserEntityGenerator {

    public static UserEntity valid() {
        return UserEntity.buildExisting(
                1L,
                "Nick Fisher",
                "example@mail.com",
                "$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2"
        );
    }

    public static UserEntity validWithRawPassword() {
        return UserEntity.buildExisting(
                1L,
                "Nick Fisher",
                "example@mail.com",
                "pass"
        );
    }

    public static UserEntity other() {
        return UserEntity.buildExisting(
                2L,
                "Brad Greedy",
                "some.other@mail.com",
                "$2a$10$/iS1ifiyTHY7OKW4tg5jy.5XTM1hvbdhXMa2zcF1H9kByUvn26Z/6"
        );
    }

    public static List<UserEntity> list() {
        return List.of(valid(), other());
    }
}
