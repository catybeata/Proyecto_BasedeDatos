/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

/**
 *
 * @author Catherine Beata
 */
public class Conexion {

    private DataSource ds;
    private Connection conn;

    public Conexion() {
        ds = SQLDatasource.getSQLLiteDataSource();
        conn = null;
    }

    private Connection connectDB() {
        try {
            conn = this.ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void disconnectDB(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean verificarUsuario(String correo) {

        conn = connectDB();
        String query = " Select CorreoUsuario from  Usuarios;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;

        try {
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            while (resultadotabla.next()) {
                if (correo.equals(resultadotabla.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean verificarClave(String clave, String correo) {

        conn = connectDB();
        String query = " Select ClaveUsuario from Usuarios where CorreoUsuario = ?;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, correo);
            resultadotabla = consulta.executeQuery();

            if (clave.equals(resultadotabla.getString(1))) {
                return true;
            }

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void insertarUsuario(String correo, String clave) {

        conn = connectDB();
        String query = "Insert into Usuarios (CorreoUsuario, ClaveUsuario) Values(?, ?);";
        PreparedStatement consulta = null;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, correo);
            consulta.setString(2, clave);

            boolean resultado = consulta.execute();

            if (resultado) {
                System.out.println("**Su Usuario no pudo ser registrado.");
            } else {
                System.out.println();
                System.out.println(".....................Usuario Registrado Existosamente.......................");
                System.out.println();
            }

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void mostrarListaEmpleados() {
        conn = connectDB();
        String query = "select UsuarioID, CorreoUsuario from Usuarios;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        try {
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            System.out.println("....................................................");
            System.out.println(".............Tabla de Usuarios Activos..............");
            System.out.printf("%-12s%-40s%n", "UsuarioID", "CORREO/USUARIO");
            System.out.println("....................................................");
            while (resultadotabla.next()) {
                System.out.printf("%-12s%-43s%n", resultadotabla.getInt(1), resultadotabla.getString(2));
            }
            System.out.println("....................................................");

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean verificarUsuarioID(int id) {

        conn = connectDB();
        String query = " Select UsuarioID from  Usuarios;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;

        try {
            consulta = conn.prepareStatement(query);
            resultadotabla = consulta.executeQuery();
            while (resultadotabla.next()) {
                if (id == (resultadotabla.getInt(1))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    public String retornarCorreo(int id) {

        conn = connectDB();
        String query = "Select CorreoUsuario from Usuarios where UsuarioID = ?;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        String correo = "";

        try {
            consulta = conn.prepareStatement(query);
            consulta.setInt(1, id);
            resultadotabla = consulta.executeQuery();
            correo = resultadotabla.getString(1);

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return correo;
    }
    
    public void insertarProyecto(String nombre, String descripcion, String fechaInicio, String fechaFin, int encargado) {

        conn = connectDB();
        String query = "Insert into Proyectos (NombreProyecto, DescripcionProyecto, FechaInicio, FechaFin, EncargadoProyecto) values(?, ?, ?, ?, ?);";
        PreparedStatement consulta = null;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, nombre);
            consulta.setString(2, descripcion);
            consulta.setString(3, fechaInicio);
            consulta.setString(4, fechaFin);
            consulta.setInt(5, encargado);

            boolean resultado = consulta.execute();

            if (resultado) {
                System.out.println("**Su Usuario no pudo ser registrado.");
            } else {
                System.out.println();
                System.out.println(".....................Proyecto Registrado Existosamente......................");
                System.out.println();
            }

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void mostrarMisProyectos(int id) {
        conn = connectDB();
        String query = "Select ProyectoID, NombreProyecto, DescripcionProyecto, FechaInicio, FechaFin from Proyectos where EncargadoProyecto = ?";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        try {
            consulta = conn.prepareStatement(query);
            consulta.setInt(1, id);
            resultadotabla = consulta.executeQuery();
            System.out.println("..........................................................................................");
            System.out.println("......................................Mis Proyecto........................................");
            System.out.printf("%-6s%-20s%-40s%-12s%-12s%n", "ID", "PROYECTO", "DESCRIPCION", "INICIO", "FIN");
            System.out.println("..........................................................................................");
            while (resultadotabla.next()) {
                System.out.printf("%-6s%-20s%-40s%-12s%-12s%n", resultadotabla.getInt(1), resultadotabla.getString(2), resultadotabla.getString(3), resultadotabla.getString(4), resultadotabla.getString(5));
            }
            System.out.println("..........................................................................................");

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int retornarid(String correo) {

        conn = connectDB();
        String query = "Select UsuarioID from Usuarios where CorreoUsuario = ?;";
        PreparedStatement consulta = null;
        ResultSet resultadotabla = null;
        int id = 0;

        try {
            consulta = conn.prepareStatement(query);
            consulta.setString(1, correo);
            resultadotabla = consulta.executeQuery();
            id = resultadotabla.getInt(1);

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
                if (conn != null) {
                    disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }
    
}
