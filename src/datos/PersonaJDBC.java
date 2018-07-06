/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;
import domain.Persona;
import java.sql.*;
import java.util.*;
/**
 *
 * @author rperez
 */
public class PersonaJDBC {
    
    private Connection userConnection;
    
    private final String SQL_INSERT = "INSERT INTO persona(nombre_persona, apellido_persona) VALUES(?,?)";
    private final String SQL_UPDATE = "UPDATE persona SET nombre_persona = ?, apellido_persona = ? WHERE id_persona = ?";
    private final String SQL_DELETE = "DELETE FROM persona WHERE id_persona = ?";
    private final String SQL_SELECT = "SELECT id_persona, nombre_persona, apellido_persona FROM persona ORDER BY id_persona";
    
    public PersonaJDBC(){
        
    }
    
    public PersonaJDBC(Connection connection){
        this.userConnection = connection;
    }
    public int insert(String nombre, String apellido) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        int rows  = 0;
        try{
            connection = (this.userConnection != null) ? this.userConnection : Conexion.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            System.out.println("Ejecutando query: " + SQL_INSERT);
            rows = preparedStatement.executeUpdate();
            System.out.println("Registros afectados: " + rows);
        }finally{
            Conexion.close(preparedStatement);
            if(this.userConnection == null){
                Conexion.close(connection);
            }
        }
        return rows;
    }
    
    public int update(int id, String nombre, String apellido)throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try{
            connection = (this.userConnection != null) ? this.userConnection : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setInt(3, id);
            rows = preparedStatement.executeUpdate();
            System.out.println("Registros actualizados " + rows);
        }finally{
            Conexion.close(preparedStatement);
            if(this.userConnection == null){
                Conexion.close(connection);
            }
        }
        return rows;
    }
    
    public int delete(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try{
            connection = (this.userConnection != null) ? this.userConnection : Conexion.getConnection();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            rows = preparedStatement.executeUpdate();
            System.out.println("Registros eliminados: " + rows);
        }finally{
          Conexion.close(preparedStatement);
          if(this.userConnection == null){
            Conexion.close(connection);
          }
        }
        return rows;
    }
    
    public List<Persona> select() throws SQLException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Persona persona = null;
        List<Persona> personas = new ArrayList<>();
        try{
            connection = (this.userConnection != null) ? this.userConnection : Conexion.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                int id = resultSet.getInt("id_persona");
                String nombre = resultSet.getString("nombre_persona");
                String apellido = resultSet.getString("apellido_persona");
                persona = new Persona();
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                personas.add(persona);
            }
        }finally{
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            if(this.userConnection == null){
                Conexion.close(connection);
            }
        }
        return personas;
    }
}
