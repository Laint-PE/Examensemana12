
package controllers;

import dao.ClienteDAO;
import models.ClienteModel;
import java.util.ArrayList;

public class ClienteController {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public boolean registrar(ClienteModel cliente) {
        if (cliente == null || cliente.getDocumento() == null) {
            return false;
        }

        String documento = cliente.getDocumento();

        if (!documento.matches("[0-9]{8}|[0-9]{11}")) {
            return false;
        }

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }

        return clienteDAO.registrar(cliente);
    }

    public ArrayList<ClienteModel> listar() {
        return clienteDAO.listar();
    }

    public ClienteModel buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }

        return clienteDAO.buscarPorDocumento(documento);
    }

    public ArrayList<ClienteModel> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return clienteDAO.buscarPorNombre(nombre);
    }

    public boolean eliminar(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }

        return clienteDAO.eliminar(documento);
    }
}
