package gugunava.danil.usermanagementservice.controller;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController_DeleteUser_Test extends AbstractUserControllerTest {

    @Test
    void whenUserExists_thenStatusSuccess_andReturnEmptyBody() throws Exception {
        long userId = 1L;
        given(userRepository.existsById(userId)).willReturn(true);

        mockMvc.perform(delete(BASE_URL + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void whenUserNotExists_thenStatusSuccess_andReturnEmptyBody() throws Exception {
        long userId = 1L;
        given(userRepository.existsById(userId)).willReturn(false);

        mockMvc.perform(delete(BASE_URL + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
