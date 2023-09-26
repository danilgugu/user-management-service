package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.generator.UserEntityGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserController_GetUsers_Test extends AbstractUserControllerTest {

    @Test
    void whenUsersExist_thenReturnListOfUsers() throws Exception {
        List<UserEntity> usersInDB = UserEntityGenerator.list();
        given(userRepository.findAll()).willReturn(usersInDB);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(usersInDB.get(0).getId())))
                .andExpect(jsonPath("$[0].userName", is(usersInDB.get(0).getUserName())))
                .andExpect(jsonPath("$[0].email", is(usersInDB.get(0).getEmail())))
                .andExpect(jsonPath("$[0].password").doesNotExist())
                .andExpect(jsonPath("$[1].id", is(usersInDB.get(1).getId())))
                .andExpect(jsonPath("$[1].userName", is(usersInDB.get(1).getUserName())))
                .andExpect(jsonPath("$[1].email", is(usersInDB.get(1).getEmail())))
                .andExpect(jsonPath("$[0].password").doesNotExist());

        verify(userRepository).findAll();
    }

    @Test
    void whenUsersNotExist_thenReturnEmptyList() throws Exception {
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(Collections.emptyList())));

        verify(userRepository).findAll();
    }
}
