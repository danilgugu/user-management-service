package gugunava.danil.usermanagementservice.converter;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserConverter implements Converter<UserEntity, User> {

    @Override
    public User convert(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getEmail()
        );
    }
}
