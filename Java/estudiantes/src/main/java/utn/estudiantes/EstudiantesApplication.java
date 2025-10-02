package utn.estudiantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.estudiantes.modelo.Estudiantes;
import utn.estudiantes.servicio.EstudianteServicio;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicio estudianteServicio;
	private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación...");
		// Levantar Fábrica de Spring
		SpringApplication.run(EstudiantesApplication.class, args);
		logger.info("Aplicación Finalizada.");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(nl + "Ejecutando método run de Spring..." + nl + nl);
		var salir = false;
		var consola = new Scanner(System.in);
		while(!salir){
			mostrarMenu();
			salir = ejecutarOpciones(consola);
			logger.info(nl);
		} // Fin del Ciclo While
	}

	private void mostrarMenu() {
		//logger.info(nl);
		logger.info("""
				******* Sistema de Estudiantes *******
				1. Listar Estudiantes
				2. Buscar Estudiantes
				3. Agregar Estudiante
				4. Modificar Estudiante
				5. Eliminar Estudiante
				6. Salir
				Eligir opción: """);
	}

	private boolean ejecutarOpciones(Scanner consola){
		var salir = false;
		try {
			var opcion = Integer.parseInt(consola.nextLine());
			logger.info(nl);
			switch (opcion) {
				case 1 -> { // Listar estudiantes
					logger.info(nl + "******* Listado de Estudiantes *******" + nl);
					List<Estudiantes> estudiantes = estudianteServicio.listarEstudiantes();
					estudiantes.forEach(estudiante -> logger.info(estudiante.toString() + nl));
				} // Fin caso 1

				case 2 -> { // Buscar estudiante por id
					logger.info("Introducir ID del Estudiante a Buscar: ");
					var idEstudiante = Integer.parseInt(consola.nextLine());
					var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
					if (estudiante != null) {
						logger.info("Estudiante Encontrado: " + estudiante + nl);
					} else {
						logger.info("Estudiante NO Encontrado con id: " + idEstudiante + nl);
					}
				} // Fin caso 2
				case 3 -> { //Agregar estudiante
					logger.info("******* Agregar Estudiante *******" + nl);
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Telefono: ");
					var telefono = consola.nextLine();
					logger.info("Email: ");
					var email = consola.nextLine();
					//crear objeto estudiante(sin id)
					var estudiante = new Estudiantes();
					estudiante.setNombre(nombre);
					estudiante.setApellido(apellido);
					estudiante.setTelefono(telefono);
					estudiante.setEmail(email);
					estudianteServicio.guardarEstudiante(estudiante);
					logger.info("Estudiante Agregado: " + estudiante + nl);
				}//Fin caso 3

				case 4 -> { //Modificar estudiante
					logger.info("******* Modificar Estudiante *******" + nl);
					//Aquí lo primero es especificar cuál es el id del objeto a modificar
					logger.info("ID del Estudiante: ");
					var idEstudiante = Integer.parseInt(consola.nextLine());
					// Buscamos estudiante a modificar
					Estudiantes estudiante =
							estudianteServicio.buscarEstudiantePorId(idEstudiante);
					if (estudiante != null){
						logger.info("Nombre: ");
						var nombre = consola.nextLine();
						logger.info("Apellido: ");
						var apellido = consola.nextLine();
						logger.info("Telefono: ");
						var telefono = consola.nextLine();
						logger.info("Email: ");
						var email = consola.nextLine();
						estudiante.setNombre(nombre);
						estudiante.setApellido(apellido);
						estudiante.setTelefono(telefono);
						estudiante.setEmail(email);
						estudianteServicio.guardarEstudiante(estudiante);
						logger.info("Estudiante Modificado: " + estudiante + nl);
					} else {
						logger.info("Estudiante NO Modificado con el id: " + idEstudiante + nl);
					}
				}//Fin caso 4

				case 5 -> { //Eliminar estudiante
					logger.info("******* Eliminar Estudiante *******" + nl);
					logger.info("ID del Estudiante: ");
					var idEstudiante = Integer.parseInt(consola.nextLine());
					// Buscar estudiante a eliminar
					var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
					if (estudiante != null){
						logger.info("Estudiante Eliminado: " + estudiante + nl);
						estudianteServicio.eliminarEstudiante(estudiante);
					}
					else
						logger.info("Estudiante NO Eliminado con id: " + idEstudiante + nl);
				}//Fin caso 5

				case 6 -> { //salir
					logger.info("Saliendo del Sistema..." + nl + nl);
					salir = true;
				}//Fin caso 6

				default -> logger.info("Opción no reconocida, ingresar otra opción." + nl);

			} //Fin switch
		} catch (NumberFormatException e) {
			logger.info(nl + "Entrada inválida, debe ingresar un número." + nl);
		}
		return salir;
	}

}
