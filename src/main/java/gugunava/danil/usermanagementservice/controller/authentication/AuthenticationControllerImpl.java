package gugunava.danil.usermanagementservice.controller.authentication;

import gugunava.danil.usermanagementservice.model.AuthenticationRequest;
import gugunava.danil.usermanagementservice.model.AuthenticationResponse;
import gugunava.danil.usermanagementservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        String token = authenticationService.login(request);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
