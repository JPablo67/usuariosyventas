/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClsArticulo;
import Modelo.ClsConexion;
import Modelo.ClsUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Julian
 */
public class ClsGestionUsuario {

    ClsConexion conexion = new ClsConexion();

    public ClsGestionUsuario() {

    }
    
    public boolean guardarUser(String nombre, String apellido, 
            String cedula, String correo, String contrasena) {
        ClsUsuario usuario = new ClsUsuario(nombre, 
                apellido, cedula, correo, contrasena);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("insert into usuario(nombre,apellido,cedula,correo,contrasena) "
                + "values('" + usuario.getNombre() + "','" +
                usuario.getApellido() + "','" +
                usuario.getCedula() + "','" +
                usuario.getCorreo() + "','" +
                usuario.getContrasena() + "')");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }
    
    public boolean guardarArticulo(String cedula,String nombre,String precio,String cantidad,String descripcion,String categoria) {
        ClsArticulo arti = new ClsArticulo(cedula, nombre, precio, cantidad, descripcion, categoria);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("insert into articulo(cedu,nombre,precio,cantidad,descripcion,categoria) "
                + "values('" + arti.getCedula() + "','" +
                arti.getNombre() + "','" +
                arti.getPrecio() + "','" +
                arti.getCantidad() + "','" +
                arti.getDescripcion() + "','" +
                arti.getCategoria() + "')");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }
    
    
    
    

    public List<String> buscar(String cedula) {
        
        List<String> temp = new ArrayList<String>();
        
        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,cedula,correo,"
                            + "contrasena from usuario where "
                            + "cedula='" + cedula + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contrasena")+"");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionUsuario.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificarUser(String nombre, String apellido, 
            String cedula, String correo, String contrasena) {
        ClsUsuario usuario = new ClsUsuario(nombre, apellido, cedula, correo,contrasena );
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("update usuario set nombre='" + usuario.getNombre() + 
                "',apellido='" + usuario.getApellido() + "', cedula='"  + usuario.getCedula() + "', correo='" + usuario.getCorreo()+ "'," + 
                "contrasena=" + usuario.getCedula()
                + " where cedula='" + usuario.getCedula()+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String cedula) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute
        ("delete from usuario where cedula='" + cedula+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,nombre,apellido,"
                            + "edad from usuario order by cedula"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("cedula"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("apellido"),
                    conexion.getResultadoDB().getInt("edad")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionUsuario.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }
    
    public DefaultTableModel listarArtoculos(String cedula) {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Nombre", "Precio", "Cantidad", "Descripcion", "Categoria"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedu,nombre,precio,cantidad,descripcion,"
                            + "categoria from articulo where cedu='"+cedula+"'"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("precio"),
                    conexion.getResultadoDB().getString("cantidad"),
                    conexion.getResultadoDB().getString("descripcion"),
                    conexion.getResultadoDB().getString("categoria")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionUsuario.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

}
