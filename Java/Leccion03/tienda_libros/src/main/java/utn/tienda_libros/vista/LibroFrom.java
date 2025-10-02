package utn.tienda_libros.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.tienda_libros.servicio.LibroServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class LibroFrom extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel; // Panel principal que contendrá la interfaz
    private JTable tablaLibros;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroFrom(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
        iniciarForma();
    }

    private void iniciarForma() {
        // Crear panel principal con BorderLayout
        panel = new JPanel(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Tienda de Libros");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        tablaModeloLibros = new DefaultTableModel(0, 5);
        String[] cabecera = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        tablaModeloLibros.setColumnIdentifiers(cabecera);
        tablaLibros = new JTable(tablaModeloLibros);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        // Panel intermedio para separar título y tabla
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // 20 px de espacio arriba
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        // Agregar el panel intermedio al panel principal
        panel.add(panelCentro, BorderLayout.CENTER);

        // Llamar a listar los libros desde la base de datos
        listarLibros();

        // Configurar JFrame
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }


//    private void createUIComponents() {
//        this.tablaModeloLibros = new DefaultTableModel(0, 5);
//        String[] cabecera = {"Id", "Libro", "Autor", "Precio", "Existencias"};
//        this.tablaModeloLibros.setColumnIdentifiers(cabecera);
//        // Instanciar el objeto de JTable
//        this.tablaLibros = new JTable(tablaModeloLibros);
//        listarLibros();
//    }

    private void listarLibros(){
        // Limpiar la tabla
        tablaModeloLibros.setRowCount(0);
        // Obtener los libros de la DB
        var libros = libroServicio.listarLibros();
        // Iterar cada libro
        libros.forEach((libro) -> { // Función Lambda
            // Creamos cada registro para agregarlos a la tabla
            Object [] renglonLibro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(renglonLibro);
        });
    }


}
