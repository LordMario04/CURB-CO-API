package com.curbco.api.service;

import com.curbco.api.dto.AuthResponse;
import com.curbco.api.dto.RegisterRequest;
import com.curbco.api.model.Role;
import com.curbco.api.model.User;
import com.curbco.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        // Verificar si el correo ya está registrado
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Crear el usuario
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        // Guardar en la base de datos
        userRepository.save(user);

        // Temporalmente retornamos un token vacío.
        // Cuando implementemos JwtService lo reemplazaremos.
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);

        // Más adelante será:
        // String jwt = jwtService.generateToken(user);
        // return new AuthResponse(jwt);
    }
}