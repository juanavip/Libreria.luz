package com.librerialuz.repository;

import com.librerialuz.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para la gestión de usuarios en la base de datos MySQL.
 * Permite realizar operaciones CRUD de forma automatizada.
 * 
 * @author Juan Sebastian Avila Patiño
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su correo electrónico (necesario para el inicio de sesión).
     */
    Optional<Usuario> findByCorreo(String correo);
}