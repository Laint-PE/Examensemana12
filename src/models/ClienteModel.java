package models;

public class ClienteModel {

    private final String documento;   // DNI (8) o RUC (11)
    private final String nombre;
    private final String telefono;

    public ClienteModel(String documento, String nombre, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
}