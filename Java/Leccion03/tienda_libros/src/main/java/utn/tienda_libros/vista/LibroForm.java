package utn.tienda_libros.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.tienda_libros.modelo.Libro;
import utn.tienda_libros.servicio.LibroServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
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
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
    }

    private void agregarLibro() {
        Libro libro = validarFormulario(null);
        if (libro == null) return;

        libroServicio.guardarLibro(libro);
        mostrarMensaje("El libro ha sido agregado.");
        limpiarFormulario();
        listarLibros();
    }


    private void cargarLibroSeleccionado(){
        // Los índices de las columnas inician en 0
        var renglon = tablaLibros.getSelectedRow();
        if (renglon != -1){
            String idLibro = tablaLibros.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(idLibro);
            String nombreLibro =
                    tablaLibros.getModel().getValueAt(renglon, 1).toString();
            libroTexto.setText(nombreLibro);
            String autor =
                    tablaLibros.getModel().getValueAt(renglon, 2).toString();
            autorTexto.setText(autor);
            String precio =
                    tablaLibros.getModel().getValueAt(renglon, 3).toString();
            precioTexto.setText(precio);
            String existencias =
                    tablaLibros.getModel().getValueAt(renglon, 4).toString();
            existenciasTexto.setText(existencias);
        }
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

    private void modificarLibro() {
        if (idTexto.getText().isEmpty()) {
            mostrarMensaje("Se debe seleccionar un registro en la tabla.");
            return;
        }

        int idLibro = Integer.parseInt(idTexto.getText());
        Libro libro = validarFormulario(idLibro);
        if (libro == null) return;

        libroServicio.guardarLibro(libro);
        mostrarMensaje("El libro ha sido modificado.");
        limpiarFormulario();
        listarLibros();
    }



    private void eliminarLibro(){
        var renglon = tablaLibros.getSelectedRow();
        if (renglon != -1){
            String idLibro =
                    tablaLibros.getModel().getValueAt(renglon, 0).toString();
            var libro = new Libro();
            libro.setIdLibro(Integer.parseInt(idLibro));
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("El libro " + idLibro + " ha sido eliminado");
            limpiarFormulario();
            listarLibros();
        }
        else {
            mostrarMensaje("No se ha seleccionado ningún libro de la tabla a eliminar.");
        }
    }

    // private void createUIComponents(){
    //
    //}

    private void iniciarForma() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Tienda de Libros");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // ===== Tabla =====
        idTexto = new JTextField("");
        idTexto.setVisible(false);
        this.tablaModeloLibros = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] cabecera = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabecera);
        this.tablaLibros = new JTable(tablaModeloLibros);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Evita que se seleccionen varios registros
        tablaLibros.getTableHeader().setReorderingAllowed(false);
        // Evita que se puedan reordenar las columnas
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

    private Libro validarFormulario(Integer idActual) {
        var nombreLibro = libroTexto.getText().trim();
        var autor = autorTexto.getText().trim();
        double precio;
        int existencias;

        if (nombreLibro.isEmpty()) {
            mostrarMensaje("Ingresar nombre del libro.");
            libroTexto.requestFocusInWindow();
            return null;
        }

        if (autor.isEmpty()) {
            mostrarMensaje("Ingresar autor del libro.");
            autorTexto.requestFocusInWindow();
            return null;
        }

        try {
            precio = Double.parseDouble(precioTexto.getText());
            if (precio < 0) {
                mostrarMensaje("El precio no puede ser negativo.");
                precioTexto.requestFocusInWindow();
                return null;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Precio debe ser un valor numérico válido.");
            precioTexto.requestFocusInWindow();
            return null;
        }

        try {
            existencias = Integer.parseInt(existenciasTexto.getText());
            if (existencias < 0) {
                mostrarMensaje("Las existencias no pueden ser negativas.");
                existenciasTexto.requestFocusInWindow();
                return null;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Existencias debe ser un valor entero válido.");
            existenciasTexto.requestFocusInWindow();
            return null;
        }

        // Verificar Duplicados
        var libros = libroServicio.listarLibros();
        boolean existe = libros.stream()
                .anyMatch(l ->
                        l.getNombreLibro().equalsIgnoreCase(nombreLibro) &&
                                l.getAutor().equalsIgnoreCase(autor) &&
                                (idActual == null || !idActual.equals(l.getIdLibro()))
                );
        if (existe) {
            mostrarMensaje("Ya existe un libro con ese nombre.");
            return null;
        }

        return new Libro(idActual, nombreLibro, autor, precio, existencias);
    }

}
