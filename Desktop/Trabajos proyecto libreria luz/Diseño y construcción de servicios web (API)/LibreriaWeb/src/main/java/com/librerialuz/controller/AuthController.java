package com.librerialuz.controller;

import com.librerialuz.model.Usuario;
import com.librerialuz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<Object> registrar(@RequestBody Usuario usuario) {
        try {
            authService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("mensaje", "Usuario registrado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "No se pudo registrar el usuario. El correo ya está en uso."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario credenciales) {
        boolean autenticado = authService.autenticarUsuario(credenciales.getCorreo(), credenciales.getContrasena());

        if (autenticado) {
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Autenticación satisfactoria"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Error en la autenticación"));
        }
    }
}