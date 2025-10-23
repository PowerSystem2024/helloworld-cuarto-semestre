package utn.tienda_libros.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.tienda_libros.modelo.Libro;
import utn.tienda_libros.servicio.LibroServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utn.tienda_libros.servicio.ServicioException;

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

    private static final Logger logger = LoggerFactory.getLogger(LibroForm.class);

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
        if (libro == null){
//            limpiarFormulario();
            return;
        }

        try {
            libroServicio.guardarLibro(libro);
            mostrarMensaje("El libro ha sido agregado.");
            limpiarFormulario();
            listarLibros();
        } catch (ServicioException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void modificarLibro() {
        var renglon = tablaLibros.getSelectedRow();
        if (idTexto.getText().isEmpty() || renglon == -1) {
            mostrarMensaje("Se debe seleccionar un registro en la tabla.");
            return;
        }

        int idLibro = Integer.parseInt(idTexto.getText());
        Libro libro = validarFormulario(idLibro);
        if (libro == null) return;

        try {
            libroServicio.guardarLibro(libro);
            mostrarMensaje("El libro ha sido modificado.");
            limpiarFormulario();
            listarLibros();
        } catch (ServicioException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void eliminarLibro() {
        var renglon = tablaLibros.getSelectedRow();
        if (renglon == -1){
            mostrarMensaje("No se ha seleccionado ningún libro de la tabla a eliminar.");
            return;
        }

        String idLibro = tablaLibros.getModel().getValueAt(renglon, 0).toString();
        var libro = new Libro();
        libro.setIdLibro(Integer.parseInt(idLibro));

        try {
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("El libro " + idLibro + " ha sido eliminado");
            limpiarFormulario();
            listarLibros();
        } catch (ServicioException e) {
            mostrarMensaje(e.getMessage());
        }
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

    private void createUIComponents(){
    }

    private void iniciarForma() {
        // ===== Look and Feel moderno =====
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            UIManager.put("Button.arc", 15);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new Dimension(0, 5));
            UIManager.put("Table.selectionBackground", new Color(60, 120, 200));
            UIManager.put("Table.selectionForeground", Color.WHITE);
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.foreground", new Color(60, 60, 60));
        } catch (UnsupportedLookAndFeelException e) {
            logger.error("Error al aplicar FlatLaf", e);
        }

        // ===== Panel principal =====
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(245, 247, 250));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Tienda de Libros");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(40, 60, 90));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // ===== Tabla =====
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloLibros = new DefaultTableModel(0, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cabecera = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabecera);

        this.tablaLibros = new JTable(tablaModeloLibros);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.getTableHeader().setReorderingAllowed(false);
        tablaLibros.setRowHeight(28);
        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaLibros.getTableHeader().setBackground(new Color(230, 230, 230));
        tablaLibros.getTableHeader().setOpaque(false);
        tablaLibros.setGridColor(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 0, 25, 0));
        panelCentro.setBackground(new Color(245, 247, 250));
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelCentro, BorderLayout.CENTER);

        // ===== Panel izquierdo (formulario) =====
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 0, 25, 30));
        panelIzquierdo.setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension campoDimension = new Dimension(180, 28);

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
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lbl.setForeground(new Color(70, 70, 70));
            panelIzquierdo.add(lbl, gbc);

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

        panelIzquierdo.setPreferredSize(new Dimension(300, 0));
        panel.add(panelIzquierdo, BorderLayout.WEST);

        // ===== Panel inferior (botones modernos) =====
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 30, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 100, 15, 100));
        panelBotones.setBackground(new Color(245, 247, 250));

        JButton[] botones = {agregarButton, modificarButton, eliminarButton};
        Color colorPrimario = new Color(50, 130, 200);
        Color colorHover = new Color(35, 100, 170);

        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setBackground(colorPrimario);
            b.setForeground(Color.WHITE);
            b.setBorderPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(150, 40));

            // Efecto hover simple
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBackground(colorHover);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBackground(colorPrimario);
                }
            });

            panelBotones.add(b);
        }

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
