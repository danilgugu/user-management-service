package gugunava.danil.usermanagementservice.controller.authentication;

import gugunava.danil.usermanagementservice.generator.AuthenticationGenerator;
import gugunava.danil.usermanagementservice.generator.AuthenticationRequestGenerator;
import gugunava.danil.usermanagementservice.generator.JwtGenerator;
import gugunava.danil.usermanagementservice.model.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationController_Login_Test extends AbstractAuthenticationControllerTest {

    @Test
    void whenAuthenticationRequestIsValid_thenStatusSuccess_andReturnToken() throws Exception {
        Authentication authentication = AuthenticationGenerator.valid();
        AuthenticationRequest request = AuthenticationRequestGenerator.valid();
        Jwt jwt = JwtGenerator.valid();
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(jwtEncoder.encode(any(JwtEncoderParameters.class))).willReturn(jwt);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(jwt.getTokenValue())));

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationArgumentCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(authenticationArgumentCaptor.capture());
        UsernamePasswordAuthenticationToken actualAuthentication = authenticationArgumentCaptor.getValue();
        then(actualAuthentication.isAuthenticated()).isFalse();
        then(actualAuthentication.getPrincipal()).isEqualTo("admil@mail.com");
        then(actualAuthentication.getCredentials()).isEqualTo("$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2");

        ArgumentCaptor<JwtEncoderParameters> jwtEncoderParametersArgumentCaptor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(jwtEncoderParametersArgumentCaptor.capture());
        JwtEncoderParameters actualJwtEncoderParameters = jwtEncoderParametersArgumentCaptor.getValue();
        then(actualJwtEncoderParameters.getClaims().getClaimAsString("scope")).isEqualTo("USER.DELETE");
        then(actualJwtEncoderParameters.getClaims().getClaimAsString("iss")).isEqualTo("self");
        then(actualJwtEncoderParameters.getClaims().getClaimAsString("sub")).isEqualTo("admin@mail.com");
        then(actualJwtEncoderParameters.getJwsHeader()).isNull();
    }

    @Test
    void whenCredentialsAreIncorrect_thenStatusUnauthorized() throws Exception {
        AuthenticationRequest request = AuthenticationRequestGenerator.valid();
        given(authenticationManager.authenticate(any())).willThrow(new BadCredentialsException("Bad credentials"));

        thenThrownBy(() -> mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))))
                .isInstanceOf(NestedServletException.class)
                .getCause()
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Bad credentials");

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationArgumentCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(authenticationArgumentCaptor.capture());
        UsernamePasswordAuthenticationToken actualAuthentication = authenticationArgumentCaptor.getValue();
        then(actualAuthentication.isAuthenticated()).isFalse();
        then(actualAuthentication.getPrincipal()).isEqualTo("admil@mail.com");
        then(actualAuthentication.getCredentials()).isEqualTo("$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2");

        verify(jwtEncoder, never()).encode(any());
    }

    @Test
    void whenAuthenticationRequestIsInvalid_thenStatusBadRequest() throws Exception {
        AuthenticationRequest request = AuthenticationRequestGenerator.invalid();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid request: ")))
                .andExpect(jsonPath("$.message", containsString("email")))
                .andExpect(jsonPath("$.message", containsString("must be a well-formed email address")))
                .andExpect(jsonPath("$.message", containsString("password")))
                .andExpect(jsonPath("$.message", containsString("must not be blank")))
                .andExpect(jsonPath("$.time", notNullValue()));

        verify(authenticationManager, never()).authenticate(any());
        verify(jwtEncoder, never()).encode(any());
    }
}
