package com.example.managertask.client.auth;

import com.example.managertask.client.Client;
import com.example.managertask.client.ClientService;
import com.example.managertask.client.Role;
import com.example.managertask.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    AuthResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        clientService.save(client);
        String jwtToken = jwtService.generateToken(client);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
