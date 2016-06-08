/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bd.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Emilio
 */
public class Data {
    private Conexion c;
    private String query;
    private ResultSet rs;
    private ResultSet rs2;
    
    public Data() throws SQLException {        
        c = new Conexion(
                "localhost",
                "db_loteria",
                "root",
                "123456"
        );
    }
    
    public int getIDUltimoSorteo() throws SQLException{
        query="select max(id) from tbl_sorteo";
        rs=c.ejecutarSelect(query);
        
        int id=0;
        if(rs.next()){
            id=rs.getInt(1);
        }
        
        c.desconectar();
        return id;
    }
    
    public int getCantidadSorteos() throws SQLException{
        query="select count(id) from tbl_sorteo";
        rs=c.ejecutarSelect(query);
        
        int n=0;
        if(rs.next()){
            n=rs.getInt(1);
        }
        
        c.desconectar();
        return n;        
    }
    
    public void registrarBoleto(ArrayList juego) throws SQLException{
        //ordenar numeros
//        int[] arreglo=new int[4];
//        
//        for(int i=0; i<numeros.length(); i++){
//            char c=numeros.charAt(i);
//            String s=String.valueOf(c);
//            int n=Integer.parseInt(s);
//            arreglo[i]=n;
//        }

          //Arrays.sort(arreglo);
        juego.sort(null);
          
        StringBuffer numeros=new StringBuffer();
        String n;
        for(Object o: juego.toArray()){
            n=String.valueOf(o);
            numeros.append(n);
        }
        
        String jugada=numeros.toString();
        
        int id_sorteo=getIDUltimoSorteo();
        query="insert into tbl_boleto values(null, "+id_sorteo+", "+jugada+", 0, 0);";
        c.ejecutar(query);
    }
    
    public void nuevoSorteo() throws SQLException{
        query="insert into tbl_sorteo values(null, 0, '1234', '111-1', 0, 1)";
        c.ejecutar(query);
    }
    
    public void actualizarPozo(int cantidad, int idSorteo) throws SQLException{
        query="update tbl_sorteo set pozo="+cantidad+" where id="+idSorteo+"";
        c.ejecutar(query);
    }
    
    public void actualizarAciertos(int idSorteo) throws SQLException{
        query="select "
                + "tbl_boleto.id as 'id_boleto', "
                + "tbl_boleto.numeros_boleto, "
                + "tbl_sorteo.numeros_sorteo as 'numeros sorteo' "
                + "from tbl_boleto "
                + "inner join tbl_sorteo "
                + "on tbl_boleto.id_sorteo= tbl_sorteo.id "
                + "where tbl_Sorteo.id="+idSorteo+"";
        rs=c.ejecutarSelect(query);

        
        String nBoleto=null;
        String nSorteo=null;
        int idBoleto=0;
        
        while (rs.next()) {            
            idBoleto = rs.getInt(1);
            nBoleto = rs.getString(2);
            nSorteo = rs.getString(3);
            
            int aciertos=0;
            for (int i = 0; i < nBoleto.length(); i++) {
                char l = nBoleto.charAt(i);
                String ll = String.valueOf(l);
                if (nSorteo.contains(ll)) {
                    aciertos++;
                }
            }
            
            String query2 = "update tbl_boleto set aciertos=" + aciertos + " where id=" + idBoleto + "";
            c.ejecutar(query2);                       
        }
        
        c.desconectar();
    }
        
    public Sorteo getSorteo(int idSorteo) throws SQLException{
        query="select*from tbl_sorteo where id="+idSorteo+"";
        rs=c.ejecutarSelect(query);
        
        Sorteo sorteo=new Sorteo();
        if(rs.next()){
            sorteo.setId(rs.getInt(1));
            sorteo.setPozo(rs.getInt(2));
            sorteo.setNumeros_sorteo(rs.getString(3));
            sorteo.setRut_admin(rs.getString(4));
            sorteo.setTotal_ganadores(rs.getInt(5));
            sorteo.setAbierto(rs.getBoolean(6));            
        }
        
        c.desconectar();
        return sorteo;
    }    

    public void cerrarSorteo(int idSorteo) throws SQLException{
        query="update tbl_sorteo set abierto=0 where id="+idSorteo+" ";
        c.ejecutar(query);
    }

    public void actualizarGanadores(int idSorteo) throws SQLException {
        int cont=0;
        query="select count(id) from tbl_boleto where id_sorteo="+idSorteo+" and aciertos>=2";
        rs=c.ejecutarSelect(query);
        
        if(rs.next()){
            cont=rs.getInt(1);
        }
        
        String query2="update tbl_sorteo set total_ganadores="+cont+" where id="+idSorteo+"";
        c.ejecutar(query2);
        
        c.desconectar();
    }
    
    public int getCantidadAciertosSorteo(int aciertos, int idSorteo) throws SQLException{
        int n=0;
        query="select count(id) from tbl_boleto where id_sorteo="+idSorteo+" and aciertos="+aciertos+"";
        rs2=c.ejecutarSelect(query);
        
        if(rs2.next()){
            n=rs2.getInt(1);
        }
        
        c.desconectar();
        return n;
    }

    public Boleto getBoleto(int idBoleto) throws SQLException{
        query="select*from tbl_boleto where id="+idBoleto+"";
        rs=c.ejecutarSelect(query);
        
        Boleto boleto=new Boleto();
        if(rs.next()){
            boleto.setId(rs.getInt(1));
            boleto.setId_sorteo(rs.getInt(2));
            boleto.setNumeros(rs.getString(3));
            boleto.setAciertos(rs.getInt(4));
            boleto.setPremio(rs.getInt(5));
        }
        
        c.desconectar();
        return boleto;
    }
    
    public void actualizarPremiosSorteo(int idSorteo) throws SQLException{        
        query="select tbl_boleto.id as 'id_boleto',"
                + " tbl_boleto.aciertos, "
                + "tbl_sorteo.pozo "
                + "from tbl_boleto inner join tbl_sorteo "
                + "on tbl_boleto.id_sorteo=tbl_sorteo.id "
                + "where tbl_boleto.id_sorteo="+idSorteo+"";
        rs=c.ejecutarSelect(query);
        
        float pozo=0;
        int premio=0;
        int idBoleto=0;
        float aciertos=0;
        float ganadores=0;
        
        while(rs.next()){
            idBoleto=rs.getInt(1);
            aciertos=rs.getInt(2);
            pozo=rs.getInt(3);
            
            if(aciertos==2.0){
                ganadores=getCantidadAciertosSorteo(2, idSorteo); //tener otro rs
                premio=(int)(pozo*0.1/ganadores);
            }else if(aciertos==3.0){
                ganadores=getCantidadAciertosSorteo(3, idSorteo);
                premio=(int)(pozo*0.2/ganadores);                
            }else if(aciertos==4.0){
                ganadores=getCantidadAciertosSorteo(4, idSorteo);
                premio = (int) (pozo * 0.7 / ganadores);
            }else{
                ganadores=0;
                premio=0;
            }
            
            String query2 = "update tbl_boleto set premio="+premio+" where id="+idBoleto+"";
            c.ejecutar(query2);
        }
        //crear otro objeto conexion
        c.desconectar();        
    }
    
    public void sortear(int idSorteo) throws SQLException{
        Random rd=new Random();
        int[] numeros=new int[4];
        int i=0;
        
        int n=0;        
        while(i<4){
            n=rd.nextInt(9);
            if(n!=0 & n!=numeros[0] & n!=numeros[1] & n!=numeros[2] & n!=numeros[3]){
                numeros[i]=n;                                
                i++;                
            }            
        }
        
        Arrays.sort(numeros);
        String ordenado=numeros[0]+""+numeros[1]+""+numeros[2]+""+numeros[3];
        
        query="update tbl_sorteo set numeros_sorteo="+ordenado+" where id="+idSorteo+"";
        c.ejecutar(query);
    }
    
    public int getPremio(int aciertos, int idSorteo) throws SQLException{
        query="select premio from tbl_boleto where id_sorteo="+idSorteo+" and aciertos="+aciertos+"";
        rs2=c.ejecutarSelect(query);
        
        int premio=0;
        if(rs2.next()){
            premio=rs2.getInt(1);
        }

        c.desconectar();        
        return premio;
    }
}
