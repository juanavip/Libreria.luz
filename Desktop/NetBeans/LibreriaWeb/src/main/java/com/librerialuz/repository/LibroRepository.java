package com.librerialuz.repository;

import com.librerialuz.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio administrado por Spring Data JPA para la gestión de persistencia.
 * 
 * @author Juan Sebastian Avila Patiño
 * @version 1.0
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
