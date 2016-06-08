/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Emilio
 */
public class Sorteo {
    private int id;
    private int pozo;
    private String numeros_sorteo;
    private String rut_admin;
    private int total_ganadores;
    private boolean abierto;

    public Sorteo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPozo() {
        return pozo;
    }

    public void setPozo(int pozo) {
        this.pozo = pozo;
    }

    public String getNumeros_sorteo() {
        return numeros_sorteo;
    }

    public void setNumeros_sorteo(String numeros_sorteo) {
        this.numeros_sorteo = numeros_sorteo;
    }

    public String getRut_admin() {
        return rut_admin;
    }

    public void setRut_admin(String rut_admin) {
        this.rut_admin = rut_admin;
    }

    public int getTotal_ganadores() {
        return total_ganadores;
    }

    public void setTotal_ganadores(int total_ganadores) {
        this.total_ganadores = total_ganadores;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    @Override
    public String toString() {
        return "Sorteo{" + "id=" + id + ", pozo=" + pozo + ", numeros_sorteo=" + numeros_sorteo + ", rut_admin=" + rut_admin + ", total_ganadores=" + total_ganadores + ", abierto=" + abierto + '}';
    }
    
    
    
}
