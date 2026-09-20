package com.librerialuz.controller;

import com.librerialuz.model.Libro;
import com.librerialuz.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador RESTful que expone las operaciones CRUD del catálogo de libros.
 * Compatible con clientes Web, Stand-alone y Móvil.
 * 
 * @author Juan Sebastian Avila Patiño
 * @version 2.0
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Consulta el listado completo de libros registrados.
     * @return Lista de objetos {@link Libro} en formato JSON.
     */
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    /**
     * Registra un nuevo libro en la base de datos MySQL.
     * @param libro Objeto deserializado del cuerpo de la petición (JSON).
     * @return Objeto {@link Libro} guardado con su ID generado.
     */
    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Busca un libro por su identificador único.
     * @param id Identificador numérico del libro.
     * @return {@link ResponseEntity} con el libro o respuesta HTTP 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(libro -> ResponseEntity.ok().body(libro))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de un libro existente.
     * @param id Identificador del libro a modificar.
     * @param libroDetalles Datos actualizados.
     * @return Objeto actualizado o respuesta HTTP 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        return libroRepository.findById(id).map(libro -> {
            libro.setTitulo(libroDetalles.getTitulo());
            libro.setIsbn(libroDetalles.getIsbn());
            libro.setPrecio(libroDetalles.getPrecio());
            libro.setStock(libroDetalles.getStock());
            Libro libroActualizado = libroRepository.save(libro);
            return ResponseEntity.ok(libroActualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un libro de la base de datos por su ID.
     * @param id Identificador del libro a eliminar.
     * @return Respuesta HTTP 200 OK si se eliminó o 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}