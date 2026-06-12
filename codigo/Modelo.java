/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import CONEXIONBD.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author marce
 */
public class ModeloProductos {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadActual;
    private int cantidadMin;

    public boolean insertarBD() {
        String sql = "INSERT INTO productos (codigo_producto, nombre, descripcion, precio, cantidad_actual, cantidad_minima) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cnn = new Conexion().getConnection(); PreparedStatement pstmt = cnn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.setString(2, nombre);
            pstmt.setString(3, descripcion);
            pstmt.setDouble(4, precio);
            pstmt.setInt(5, cantidadActual);
            pstmt.setInt(6, cantidadMin);

            int resultado = pstmt.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void buscarId() {
        String sql = """
                     SELECT
                     nombre, descripcion, precio, cantidad_actual, cantidad_minima
                     FROM productos
                     WHERE id_producto = ?;
                     """;
        try (Connection cnn = new Conexion().getConnection(); PreparedStatement pstmt = cnn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                setNombre(rs.getString("nombre"));
                setDescripcion(rs.getString("descripcion"));
                setPrecio(rs.getDouble("precio"));
                setCantidadActual(rs.getInt("cantidad_actual"));
                setCantidadMin(rs.getInt("cantidad_minima"));
            } else {
                JOptionPane.showMessageDialog(null, "Producto con codigo " + codigo + " no existe en la base de datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final DefaultTableModel actualizarTabla(TableModel modelo) {
        DefaultTableModel modeloTabProductos = (DefaultTableModel) modelo;
        String sql = """
                     SELECT id_producto, codigo_producto, nombre, descripcion, precio, cantidad_actual, cantidad_minima
                     FROM productos;
                     """;
        try (Connection cnn = new Conexion().getConnection(); Statement stmtConsulta = cnn.createStatement();) {
            ResultSet rs = stmtConsulta.executeQuery(sql);

            modeloTabProductos.setRowCount(0);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_actual"),
                    rs.getInt("cantidad_minima")};
                modeloTabProductos.addRow(fila);
            }
            System.out.println("tabla lista");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modeloTabProductos;
    }
    
    public final DefaultTableModel actualizarTablaFiltro(TableModel modelo, String filtro) {
        DefaultTableModel modeloTabProductos = (DefaultTableModel) modelo;
        String sql = """
                     SELECT id_producto, codigo_producto, nombre, descripcion, precio, cantidad_actual, cantidad_minima
                     FROM productos
                     WHERE LOWER(codigo_producto) LIKE ? OR LOWER(nombre) LIKE ?;
                     """;
        try (Connection cnn = new Conexion().getConnection(); PreparedStatement pstmtConsulta = cnn.prepareStatement(sql);) {
            pstmtConsulta.setString(1, filtro + "%");
            pstmtConsulta.setString(2, filtro + "%");
            ResultSet rs = pstmtConsulta.executeQuery();

            modeloTabProductos.setRowCount(0);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_actual"),
                    rs.getInt("cantidad_minima")};
                modeloTabProductos.addRow(fila);
            }
            System.out.println("tabla lista");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modeloTabProductos;
    }

    public boolean actualizarBD() {
        String sql = """
                     UPDATE productos
                     SET nombre = ?, descripcion = ?, precio = ?, cantidad_actual = ?, cantidad_minima = ?
                     WHERE id_producto = ?;
                     """;
        try (Connection cnn = new Conexion().getConnection(); PreparedStatement pstmt = cnn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, cantidadActual);
            pstmt.setInt(5, cantidadMin);
            pstmt.setInt(6, id);
            int resultado = pstmt.executeUpdate();
            return resultado > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminarBD() {
        String sql = "DELETE FROM productos WHERE id_producto = ?;";
        try (Connection cnn = new Conexion().getConnection(); PreparedStatement pstmt = cnn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            int resultado = pstmt.executeUpdate();
            return resultado > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public ModeloProductos() {
        this.id = 0;
        this.codigo = "";
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0.0;
        this.cantidadActual = 0;
        this.cantidadMin = 0;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the cantidadActual
     */
    public int getCantidadActual() {
        return cantidadActual;
    }

    /**
     * @param cantidadActual the cantidadActual to set
     */
    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    /**
     * @return the cantidadMin
     */
    public int getCantidadMin() {
        return cantidadMin;
    }

    /**
     * @param cantidadMin the cantidadMin to set
     */
    public void setCantidadMin(int cantidadMin) {
        this.cantidadMin = cantidadMin;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}