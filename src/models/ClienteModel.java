
package models;

public class ClienteModel {

    private String documento;
    private String nombre;
    private String telefono;

    public ClienteModel(String documento, String nombre, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }
}
