
package models;

public class ClienteModel {

    private int dni;
    private String nombre;
    private String telefono;

    public ClienteModel(int dni, String nombre, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }
}
