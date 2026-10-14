package com.librerialuz.service;

import com.librerialuz.model.Usuario;
import com.librerialuz.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario registrarUsuario(Usuario usuario) {
        String contraseñaEncriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contraseñaEncriptada);
        return usuarioRepository.save(usuario);
    }

    public boolean autenticarUsuario(String correo, String contrasenaPlana) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return passwordEncoder.matches(contrasenaPlana, usuario.getContrasena());
        }

        return false;
    }
}