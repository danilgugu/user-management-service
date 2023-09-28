package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.generator.UpdateUserCommandGenerator;
import gugunava.danil.usermanagementservice.generator.UserEntityGenerator;
import gugunava.danil.usermanagementservice.model.UpdateUserCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController_UpdateUser_Test extends AbstractUserControllerTest {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void whenUserIsRegistered_andCommandIsValid_thenStatusOk_andReturnUpdatedUser() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.valid();
        UserEntity expected = UserEntityGenerator.valid();
        long userId = expected.getId();
        given(userRepository.findById(userId)).willReturn(Optional.of(expected));
        given(userRepository.existsByEmail(command.getEmail())).willReturn(false);
        given(userRepository.save(any())).willAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(((int) userId))))
                .andExpect(jsonPath("$.userName", is(command.getUserName())))
                .andExpect(jsonPath("$.email", is(command.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmail(command.getEmail());
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        UserEntity actual = userEntityArgumentCaptor.getValue();
        then(actual.getId()).isEqualTo(userId);
        then(actual.getUserName()).isEqualTo(command.getUserName());
        then(actual.getEmail()).isEqualTo(command.getEmail());
        then(encoder.matches(command.getPassword(), actual.getPassword())).isTrue();
    }

    @Test
    void whenUserIsRegistered_andUpdateIsNotNeeded_thenStatusOk_andReturnSameUser() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.updateNotNeeded();
        UserEntity expected = UserEntityGenerator.valid();
        long userId = expected.getId();
        given(userRepository.findById(userId)).willReturn(Optional.of(expected));

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(((int) userId))))
                .andExpect(jsonPath("$.userName", is(command.getUserName())))
                .andExpect(jsonPath("$.email", is(command.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenUserIsRegistered_andNewEmailIsInUse_thenStatusBadRequest_andReturnErrorMessage() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.valid();
        UserEntity expected = UserEntityGenerator.valid();
        long userId = expected.getId();
        given(userRepository.findById(userId)).willReturn(Optional.of(expected));
        given(userRepository.existsByEmail(command.getEmail())).willReturn(true);

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User with email 'updated@mail.com' already registered.")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmail(command.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenUserIsRegistered_andCommandIsEmpty_thenStatusOk_andReturnSameUser() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.empty();
        UserEntity expected = UserEntityGenerator.valid();
        long userId = expected.getId();
        given(userRepository.findById(userId)).willReturn(Optional.of(expected));

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(((int) userId))))
                .andExpect(jsonPath("$.userName", is(expected.getUserName())))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenUserIsNotRegistered_thenStatusNotFound_andReturnErrorMessage() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.valid();
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("User with id 1 not found.")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenCommandIsInvalid_thenStatusBadRequest_andReturnErrorMessage() throws Exception {
        UpdateUserCommand command = UpdateUserCommandGenerator.invalid();
        long userId = 1L;

        mockMvc.perform(put(BASE_URL + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid request: ")))
                .andExpect(jsonPath("$.message", containsString("email")))
                .andExpect(jsonPath("$.message", containsString("must be a well-formed email address")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }
}
