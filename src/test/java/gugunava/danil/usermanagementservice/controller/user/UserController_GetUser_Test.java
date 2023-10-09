package gugunava.danil.usermanagementservice.controller.user;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.generator.UserEntityGenerator;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController_GetUser_Test extends AbstractUserControllerTest {

    @Test
    void whenUserExists_thenStatusSuccess_andReturnUser() throws Exception {
        UserEntity userInDB = UserEntityGenerator.valid();
        long userId = userInDB.getId();
        given(userRepository.findById(userId)).willReturn(Optional.of(userInDB));

        mockMvc.perform(get(BASE_URL + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userInDB.getId().intValue())))
                .andExpect(jsonPath("$.userName", is(userInDB.getUserName())))
                .andExpect(jsonPath("$.email", is(userInDB.getEmail())))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(userRepository).findById(userId);
    }

    @Test
    void whenUserNotExists_thenStatusNotFound_andReturnErrorMessage() throws Exception {
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("User with id 1 not found.")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository).findById(userId);
    }
}
