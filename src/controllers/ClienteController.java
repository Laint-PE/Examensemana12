package controllers;

import dao.ClienteDAO;
import models.ClienteModel;
import java.util.ArrayList;

public class ClienteController {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public boolean registrar(ClienteModel cliente) {
        if (cliente == null) {
            return false;
        }

        return clienteDAO.registrar(cliente);
    }

    public ArrayList<ClienteModel> listar() {
        return clienteDAO.listar();
    }

    public ClienteModel buscarPorDni(int dni) {
        return clienteDAO.buscarPorDni(dni);
    }

    public boolean eliminar(int dni) {
        return clienteDAO.eliminar(dni);
    }
}
