package utn.tienda_libros.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.tienda_libros.modelo.Libro;
import utn.tienda_libros.repositorio.LibroRepositorio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LibroServicio implements ILibroServicio {

    private static final Logger logger = LoggerFactory.getLogger(LibroServicio.class);

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listarLibros() {
        try {
            return libroRepositorio.findAll();
        } catch (Exception e) {
            logger.error("Error al listar libros", e);
            throw new ServicioException("No se pudo listar los libros.", e);
        }
    }

    @Override
    public Libro buscarLibroPorId(Integer idLibro) {
        try {
            return libroRepositorio.findById(idLibro).orElse(null);
        } catch (Exception e) {
            logger.error("Error al buscar libro con ID {}", idLibro, e);
            throw new ServicioException("No se pudo buscar el libro con ID " + idLibro, e);
        }
    }

    @Override
    public void guardarLibro(Libro libro) {
        try {
            libroRepositorio.save(libro);
        } catch (Exception e) {
            logger.error("Error al guardar libro: {}", libro, e);
            throw new ServicioException("No se pudo guardar el libro: " + libro.getNombreLibro(), e);
        }
    }

    @Override
    public void eliminarLibro(Libro libro) {
        try {
            libroRepositorio.delete(libro);
        } catch (Exception e) {
            logger.error("Error al eliminar libro: {}", libro, e);
            throw new ServicioException("No se pudo eliminar el libro: " + libro.getIdLibro(), e);
        }
    }
}
