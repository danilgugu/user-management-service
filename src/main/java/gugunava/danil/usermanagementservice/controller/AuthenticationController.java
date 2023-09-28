package gugunava.danil.usermanagementservice.controller;

import gugunava.danil.usermanagementservice.model.AuthenticationRequest;
import gugunava.danil.usermanagementservice.model.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("api/login")
public interface AuthenticationController {

    @PostMapping
    ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request);
}
