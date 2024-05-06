package com.example.Login.controller;

import com.example.Login.dtos.request.AuthencationRequest;
import com.example.Login.dtos.request.IntrospectRequest;
import com.example.Login.dtos.request.response.AuthenticationResponse;
import com.example.Login.dtos.request.response.IntrospectResponse;
import com.example.Login.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/token")

    AuthenticationResponse authenticate(@RequestBody AuthencationRequest request){
        return authenticationService.authenticate(request);
    }
    @PostMapping("/introspect")
    IntrospectResponse authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return authenticationService.introspect(request);

    }
}
