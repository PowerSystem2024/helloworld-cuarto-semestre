package utn.tienda_libros.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.tienda_libros.modelo.Libro;
import utn.tienda_libros.servicio.LibroServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private DefaultTableModel tablaModeloLibros;

    // Campos de texto
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;

    // Botones
    JButton agregarButton = new JButton("Agregar");
    JButton modificarButton = new JButton("Modificar");
    JButton eliminarButton = new JButton("Eliminar");

    @Autowired
    public LibroForm(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> agregarLibro());
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
    }

    private void agregarLibro(){
        // Leer los valores del formulario
        if(libroTexto.getText().equals("")){
            mostrarMensaje("Ingresar nombre del libro.");
            libroTexto.requestFocusInWindow();
            return;
        }
        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());
        // Crear objeto Libro
        var libro = new Libro(null, nombreLibro, autor, precio, existencias);
//        libro.setNombreLibro(nombreLibro);
//        libro.setAutor(autor);
//        libro.setPrecio(precio);
//        libro.setExistencias(existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("El libro ha sido agregado exitosamente.");
        limpiarFormulario();
        listarLibros();
    }

    private void limpiarFormulario(){
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void modificarLibro(){

    }

    private void eliminarLibro(){

    }

    private void iniciarForma() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Tienda de Libros");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // ===== Tabla =====
        tablaModeloLibros = new DefaultTableModel(0, 5);
        String[] cabecera = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        tablaModeloLibros.setColumnIdentifiers(cabecera);
        tablaLibros = new JTable(tablaModeloLibros);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 0, 25, 0));
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelCentro, BorderLayout.CENTER);

        // ===== Panel izquierdo con espaciado simétrico =====
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 0, 25, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tamaño uniforme para JTextField
        Dimension campoDimension = new Dimension(150, 25);

        // Labels y TextFields
        String[] labels = {"Libro", "Autor", "Precio", "Existencias"};
        JTextField[] textFields = new JTextField[4];
        textFields[0] = new JTextField();
        textFields[1] = new JTextField();
        textFields[2] = new JTextField();
        textFields[3] = new JTextField();

        for (int i = 0; i < 4; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.WEST;
            panelIzquierdo.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.EAST;
            textFields[i].setPreferredSize(campoDimension);
            panelIzquierdo.add(textFields[i], gbc);
        }

        libroTexto = textFields[0];
        autorTexto = textFields[1];
        precioTexto = textFields[2];
        existenciasTexto = textFields[3];

        // Espacios verticales para distribuir simétricamente
        for (int i = 0; i < 4; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weighty = 1.0;
            panelIzquierdo.add(Box.createVerticalGlue(), gbc);
        }

        panelIzquierdo.setPreferredSize(new Dimension(300, 0));
        panel.add(panelIzquierdo, BorderLayout.WEST);

        // ===== Panel inferior con botones =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 0));

        Dimension botonDimension = new Dimension(150, 35);
        agregarButton.setPreferredSize(botonDimension);
        modificarButton.setPreferredSize(botonDimension);
        eliminarButton.setPreferredSize(botonDimension);

        panelBotones.add(agregarButton);
        panelBotones.add(modificarButton);
        panelBotones.add(eliminarButton);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // ===== Mostrar datos =====
        listarLibros();

        // ===== Configuración del JFrame =====
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void listarLibros() {
        tablaModeloLibros.setRowCount(0);
        var libros = libroServicio.listarLibros();
        libros.forEach(libro -> {
            Object[] renglonLibro = {
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
