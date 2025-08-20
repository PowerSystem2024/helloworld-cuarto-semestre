package UTN.presentacion;

import UTN.conexion.Conexion;
import UTN.datos.EstudianteDAO;
import UTN.dominio.Estudiante;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);

        var estudianteDao = new EstudianteDAO();

        while(!salir){
            try{
                mostrarMenu();
                salir = ejecutarOpciones(consola, estudianteDao); // Puede arrojar excepciones, devuelve booleano
            } catch (Exception e) {
                System.out.println();
                System.out.println("Ocurrió un error al ejecutar la operación: " + e.getMessage());
            }
        } // Fin while

//        var conexion = Conexion.getConnection();
//        if(conexion != null){
//            System.out.println("Conexion exitosa: " + conexion);
//        }
//        else
//            System.out.println("Error al conectarse");

    } // Fin main

    private static void mostrarMenu(){
        System.out.println();
        System.out.print("""
                ******* Sistema de Estudiantes *******
                1. Listar Estudiantes
                2. Buscar Estudiantes
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                """);
        System.out.print("Eligir opción: ");

    }

    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        System.out.println();
        var salir = false;
        switch (opcion) {
            case 1 -> { // Listar estudiantes
                System.out.println("******* Listado de Estudiantes *******");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
            } // Fin caso 1
            case 2 -> { // Buscar estudiante por id
                System.out.print("Introducir ID del Estudiante a Buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                if (encontrado) {
                    System.out.println("Estudiante Encontrado: " + estudiante);
                } else {
                    System.out.println("Estudiante NO Encontrado: " + estudiante);
                }
            } // Fin caso 2
            case 3 -> { //Agregar estudiante
                System.out.println("******* Agregar Estudiante *******");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //crear objeto estudiante(sin id)
                var estudiante = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);
                if (agregado)
                    System.out.println("Estudiante Agregado: " + estudiante);
                else
                    System.out.println("Estudiante NO Agregado: " + estudiante);
            }//Fin caso 3

            case 4 -> { //Modificar estudiante
                System.out.println("******* Modificar Estudiante *******");
                //Aquí lo primero es especificar cuál es el id del objeto a modificar
                System.out.print("ID del Estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //crea el objeto estudiante a modificar
                var estudiante = new Estudiante(idEstudiante, nombre, apellido, telefono, email);
                var modificado = estudianteDAO.modificarEstudiante(estudiante);
                if (modificado)
                    System.out.println("Estudiante Modificado: " + estudiante);
                else
                    System.out.println("Estudiante NO Modificado: " + estudiante);
            }//Fin caso 4

            case 5 -> { //Eliminar estudiante
                System.out.println("******* Eliminar Estudiante *******");
                System.out.print("ID Estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var eliminado = estudianteDAO.eliminarEstudiante(estudiante);
                if (eliminado)
                    System.out.println("Estudiante Eliminado: " + estudiante);
                else
                    System.out.println("Estudiante NO Eliminado: " + estudiante);
            }//Fin caso 5

            case 6 -> { //salir
                System.out.println("Saliendo del Sistema...");
                salir = true;
            }//Fin caso 6

            default -> System.out.println("Opción no reconocida, ingresar otra opción.");


        } //Fin switch
        return salir;
    }

} // Fin clase