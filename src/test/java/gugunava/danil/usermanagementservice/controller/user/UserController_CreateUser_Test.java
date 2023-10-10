package gugunava.danil.usermanagementservice.controller.user;

import gugunava.danil.usermanagementservice.entity.UserEntity;
import gugunava.danil.usermanagementservice.entity.UserRoleEntity;
import gugunava.danil.usermanagementservice.generator.CreateUserCommandGenerator;
import gugunava.danil.usermanagementservice.generator.UserEntityGenerator;
import gugunava.danil.usermanagementservice.model.CreateUserCommand;
import gugunava.danil.usermanagementservice.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static gugunava.danil.usermanagementservice.config.CachingConfig.USERS;
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

    @Value("${spring.liquibase.parameters.db.default.role.id}")
    private Long defaultRoleId;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private Cache cache;

    @BeforeEach
    void setUp() {
        given(cacheManager.getCache(USERS)).willReturn(cache);
    }

    @Test
    void whenUserIsNotRegistered_thenStatusCreated_andReturnUser_andGrantDefaultRole() throws Exception {
        CreateUserCommand command = CreateUserCommandGenerator.valid();
        UserEntity expected = UserEntityGenerator.validWithRawPassword();
        given(userRepository.existsByEmail(command.getEmail())).willReturn(false);
        given(userRepository.save(any())).willReturn(expected);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expected.getId().intValue())))
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
        ArgumentCaptor<UserRoleEntity> userRoleEntityArgumentCaptor = ArgumentCaptor.forClass(UserRoleEntity.class);
        verify(userRoleRepository).save(userRoleEntityArgumentCaptor.capture());
        UserRoleEntity actualUserRole = userRoleEntityArgumentCaptor.getValue();
        then(actualUserRole.getUserId()).isEqualTo(expected.getId());
        then(actualUserRole.getRoleId()).isEqualTo(defaultRoleId);
        verify(cache).clear();
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
        verify(userRoleRepository, never()).save(any());
        verify(cache, never()).clear();
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
        verify(userRoleRepository, never()).save(any());
        verify(cache, never()).clear();
    }
}
