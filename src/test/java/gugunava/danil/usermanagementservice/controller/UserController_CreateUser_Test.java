package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.generator.CreateUserCommandGenerator;
import gugunava.danil.usermanagementservice.generator.UserEntityGenerator;
import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController_CreateUser_Test extends AbstractUserControllerTest {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void whenUserIsNotRegistered_thenStatusCreated_andReturnUser() throws Exception {
        CreateUserCommand command = CreateUserCommandGenerator.valid();
        UserEntity expected = UserEntityGenerator.valid();
        given(userRepository.existsByEmail(command.getEmail())).willReturn(false);
        given(userRepository.save(any())).willReturn(expected);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expected.getId())))
                .andExpect(jsonPath("$.userName", is(expected.getUserName())))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userRepository).existsByEmail(command.getEmail());
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        UserEntity actual = userEntityArgumentCaptor.getValue();
        then(actual.getId()).isNull();
        then(actual.getUserName()).isEqualTo(expected.getUserName());
        then(actual.getEmail()).isEqualTo(expected.getEmail());
        then(encoder.matches(expected.getPassword(), actual.getPassword())).isTrue();
    }

    @Test
    void whenUserIsRegistered_thenStatusBadRequest_andReturnErrorMessage() throws Exception {
        CreateUserCommand command = CreateUserCommandGenerator.valid();
        given(userRepository.existsByEmail(command.getEmail())).willReturn(true);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User with email 'example@mail.com' already registered.")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository).existsByEmail(command.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenCommandIsInvalid_thenStatusBadRequest_andReturnErrorMessage() throws Exception {
        CreateUserCommand command = CreateUserCommandGenerator.invalid();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid request: ")))
                .andExpect(jsonPath("$.message", containsString("userName")))
                .andExpect(jsonPath("$.message", containsString("must not be blank")))
                .andExpect(jsonPath("$.message", containsString("email")))
                .andExpect(jsonPath("$.message", containsString("must be a well-formed email address")))
                .andExpect(jsonPath("$.message", containsString("password")))
                .andExpect(jsonPath("$.message", containsString("must not be blank")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }
}