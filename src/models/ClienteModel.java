package models;

public class ClienteModel {

    private final String documento;
    private final String nombre;
    private final String telefono;

    public ClienteModel(String documento, String nombre, String telefono) {
        if (documento == null || !documento.trim().matches("\\d{8}|\\d{11}")) {
            throw new IllegalArgumentException("El documento debe tener 8 u 11 dígitos.");
        }

        if (nombre == null || nombre.trim().isEmpty() || nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre no es válido.");
        }

        if (telefono == null || telefono.trim().isEmpty() || telefono.trim().length() > 15) {
            throw new IllegalArgumentException("El teléfono no es válido.");
        }

        this.documento = documento.trim();
        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
    }

    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
}