/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejopersonas;
import datos.Conexion;
import datos.PersonaJDBC;
import domain.Persona;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rperez
 */
public class ManejoPersonas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        PersonaJDBC personaJDBC = new PersonaJDBC();
        //personaJDBC.insert("Juan", "De la Sierra");
        //personaJDBC.update(1, "Mijis", "Locote");
        //personaJDBC.delete(1);
        
        /*List<Persona> personas = personaJDBC.select();
        for (Persona persona : personas) {
            System.out.println("" + persona.toString());
            System.out.println("--------------------------------------");
        }*/
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
            if(connection.getAutoCommit()){
            connection.setAutoCommit(false);
        }
        
        PersonaJDBC personas = new PersonaJDBC(connection);
        
        personas.update(2, "Francisco", "Cespedes");
        //personas.insert("Ramon", "Perezsgfgfdsgsdfgggggggggggggggggggggggggggggggggggggggggggggggggggsssssssssssssssssssssssssssssssssssssggggggggggggggggggggggggggggggggggggg");
        personas.insert("Ramon", "Perez");
        
        connection.commit();
        } catch (SQLException ex) {
            System.out.println("Entramos al rollback");
            ex.printStackTrace(System.out);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
        
        
        
    }
    
}
