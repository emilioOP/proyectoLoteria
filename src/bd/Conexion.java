/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Emilio
 */
public class Conexion {
    private Connection con;
    private Statement sen;
    
    public Conexion(String server, String bd, String user, String pass) throws SQLException{
        String protocolo="jdbc:mysql://";
        String lineaUser="user="+user;
        String lineaPass="password="+pass;
        
        String url=protocolo+server+"/"+bd+"?"+lineaUser+"&"+lineaPass;
        System.out.println(url);
        
        con=DriverManager.getConnection(url);
    }
    
    public void ejecutar(String query) throws SQLException{
        sen=con.createStatement();
        sen.executeUpdate(query);
        sen.close();
    }
    
    public ResultSet ejecutarSelect(String query) throws SQLException{
        sen=con.createStatement();
//        rs=sen.executeQuery(query);
        return sen.executeQuery(query);
    }
    
    public void desconectar() throws SQLException{
        sen.close();
    }
}
