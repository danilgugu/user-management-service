package gugunava.danil.usermanagementservice.generator;

import gugunava.danil.usermanagementservice.entity.UserEntity;

import java.util.List;

public class UserEntityGenerator {

    public static UserEntity valid() {
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
                "fizzbuzz"
        );
    }

    public static List<UserEntity> list() {
        return List.of(valid(), other());
    }
}
