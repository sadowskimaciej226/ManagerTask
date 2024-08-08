package com.example.managertask.client.auth;

import com.example.managertask.client.Client;
import com.example.managertask.client.ClientService;
import com.example.managertask.client.Role;
import com.example.managertask.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    AuthResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return getAuthResponse(client);
    }

    private AuthResponse getAuthResponse(Client client) {
        clientService.save(client);
        String jwtToken = jwtService.generateToken(client);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    AuthResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    Client client = clientService.getUserByEmail(request.getEmail())
            .orElseThrow();
    return getAuthResponse(client);
    }

}
