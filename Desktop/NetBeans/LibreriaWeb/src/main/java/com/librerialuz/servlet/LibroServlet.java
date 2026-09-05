package com.librerialuz.servlet;

import com.librerialuz.dao.LibroDAO;
import com.librerialuz.dao.impl.LibroDAOImpl;
import com.librerialuz.model.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controlador de peticiones HTTP para la gestión de Libros.
 * Permite interactuar con clientes web y servicios modularizados.
 * 
 * @author Juan Sebastian Avila
 * @version 1.0
 */
@WebServlet(name = "LibroServlet", urlPatterns = {"/LibroServlet"})
public class LibroServlet extends HttpServlet {

    private final LibroDAO libroDAO = new LibroDAOImpl();

    /**
     * Maneja las peticiones HTTP GET para listar los libros registrados.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            List<Libro> libros = libroDAO.obtenerTodos();
            
            
           out.println("<!DOCTYPE html>");
            out. println("<html>");
            out.println("<head><title>Catálogo de Libros - Librería Luz</title></head>");
            out.println("<body>");
            out.println("<h1>Listado de Libros Registrados</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Título</th><th>ISBN</th><th>Precio</th><th>Stock</th></tr>");
            
            for (Libro libro : libros) {
                out.println("<tr>");
                out.println("<td>" + libro.getId() + "</td>");
                out.println("<td>" + libro.getTitulo() + "</td>");
                out.println("<td>" + libro.getIsbn() + "</td>");
                out.println("<td>$" + libro.getPrecio() + "</td>");
                out.println("<td>" + libro.getStock() + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("<br><a href='index.html'>Volver al Inicio</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Maneja las peticiones HTTP POST para registrar nuevos libros.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");
        String isbn = request.getParameter("isbn");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        Integer stock = Integer.parseInt(request.getParameter("stock"));

        Libro nuevoLibro = new Libro(titulo, isbn, precio, stock);
        libroDAO.crear(nuevoLibro);

        response.sendRedirect("LibroServlet");
    }
}