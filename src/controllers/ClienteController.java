
package controllers;

import models.ClienteModel;
import repositories.ClienteRepository;
import java.util.ArrayList;

public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean registrar(ClienteModel cliente) {
        if (cliente == null || cliente.getDocumento() == null ||
                cliente.getNombre() == null || cliente.getTelefono() == null) {
            return false;
        }

        String documento = cliente.getDocumento().trim();
        String nombre = cliente.getNombre().trim();
        String telefono = cliente.getTelefono().trim();

        if (!documento.matches("[0-9]{8}|[0-9]{11}") ||
                nombre.isEmpty() || telefono.isEmpty()) {
            return false;
        }

        ClienteModel nuevoCliente = new ClienteModel(documento, nombre, telefono);

        return clienteRepository.registrar(nuevoCliente);
    }

    public ArrayList<ClienteModel> listar() {
        return clienteRepository.listar();
    }

    public ClienteModel buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }

        return clienteRepository.buscarPorDocumento(documento.trim());
    }

    public ArrayList<ClienteModel> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return clienteRepository.buscarPorNombre(nombre.trim());
    }

    public boolean eliminar(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }

        return clienteRepository.eliminar(documento.trim());
    }
}
