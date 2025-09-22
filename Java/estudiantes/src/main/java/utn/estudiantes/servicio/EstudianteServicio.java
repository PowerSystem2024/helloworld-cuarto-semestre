package utn.estudiantes.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.estudiantes.modelo.Estudiantes;
import utn.estudiantes.repositorio.EstudianteRepositorio;

import java.util.List;

@Service
public class EstudianteServicio implements IEstudianteServicio {
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @Override
    public List<Estudiantes> listarEstudiantes() {
        List<Estudiantes> estudiantes = estudianteRepositorio.findAll();
        return estudiantes;
    }

    @Override
    public Estudiantes buscarEstudiantePorId(Integer idestudiantes) {
        Estudiantes estudiante = estudianteRepositorio.findById(idestudiantes).orElse(null);
        return estudiante;
    }

    @Override
    public void guardarEstudiante(Estudiantes estudiante) {
        estudianteRepositorio.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Estudiantes estudiante) {
        estudianteRepositorio.delete(estudiante);
    }
}
