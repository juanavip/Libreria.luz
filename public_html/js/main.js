// Cargar libros guardados o datos iniciales de ejemplo
let libros = JSON.parse(localStorage.getItem("libros_libreria")) || [
    { id: 1, titulo: "Programar desde cero", isbn: "1001", precio: 45000, stock: 10 },
    { id: 2, titulo: "Spring Boot Avanzado", isbn: "2045", precio: 85000, stock: 5 }
];

document.addEventListener("DOMContentLoaded", () => {
    renderTable();

    // Evento para guardar nuevo libro
    const form = document.getElementById("bookForm");
    if (form) {
        form.addEventListener("submit", (e) => {
            e.preventDefault();

            const nuevoLibro = {
                id: libros.length > 0 ? Math.max(...libros.map(l => l.id)) + 1 : 1,
                titulo: document.getElementById("titulo").value,
                isbn: document.getElementById("isbn").value,
                precio: parseFloat(document.getElementById("precio").value),
                stock: parseInt(document.getElementById("stock").value)
            };

            libros.push(nuevoLibro);
            guardarEnLocalStorage();
            renderTable();
            form.reset();
        });
    }

    // Evento para el botón de actualizar lista
    const btnReload = document.getElementById("btnReload");
    if (btnReload) {
        btnReload.addEventListener("click", () => {
            renderTable();
        });
    }
});

// Función para renderizar la tabla de libros
function renderTable() {
    const tbody = document.getElementById("tablaLibros");
    if (!tbody) return;

    tbody.innerHTML = "";

    if (libros.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align: center; color: #64748b; padding: 1.5rem;">
                    No hay libros registrados en el catálogo.
                </td>
            </tr>
        `;
        return;
    }

    libros.forEach(libro => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td><strong>${libro.id}</strong></td>
            <td>${libro.titulo}</td>
            <td><code>${libro.isbn}</code></td>
            <td>$${libro.precio.toLocaleString()}</td>
            <td>${libro.stock}</td>
            <td>
                <button class="btn-danger-icon" onclick="eliminarLibro(${libro.id})" title="Eliminar Libro">
                    <i class="fa-solid fa-trash-can"></i>
                </button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

// Función para eliminar un libro
function eliminarLibro(id) {
    if (confirm("¿Estás seguro de que deseas eliminar este libro?")) {
        libros = libros.filter(libro => libro.id !== id);
        guardarEnLocalStorage();
        renderTable();
    }
}

// Función para guardar en LocalStorage
function guardarEnLocalStorage() {
    localStorage.setItem("libros_libreria", JSON.stringify(libros));
}

