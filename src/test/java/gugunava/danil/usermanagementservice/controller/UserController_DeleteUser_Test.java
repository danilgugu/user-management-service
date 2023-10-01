package gugunava.danil.usermanagementservice.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class UserController_DeleteUser_Test extends AbstractUserControllerTest {

    @Test
    @WithMockJwtAuth(authorities = "SCOPE_USER.DELETE")
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
    @WithMockJwtAuth(authorities = "SCOPE_USER.DELETE")
    void whenUserNotExists_thenStatusNotFound_andReturnErrorMessage() throws Exception {
        long userId = 1L;
        given(userRepository.existsById(userId)).willReturn(false);

        mockMvc.perform(delete(BASE_URL + userId))
                .andExpect(jsonPath("$.message", is("User with id 1 not found.")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @WithMockJwtAuth(authorities = "NO_AUTHORITY")
    void whenClientNotAuthorized_thenStatusForbidden() {
        long userId = 1L;
        given(userRepository.existsById(userId)).willReturn(true);
        ThrowableAssert.ThrowingCallable delete = () -> mockMvc.perform(delete(BASE_URL + userId));

        thenThrownBy(delete)
                .isInstanceOf(NestedServletException.class)
                .getCause()
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("Access Denied");

        verify(userRepository, never()).existsById(anyLong());
        verify(userRepository, never()).deleteById(anyLong());
    }
}
