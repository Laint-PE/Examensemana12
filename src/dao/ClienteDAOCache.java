
package dao;

import models.ClienteModel;
import repositories.ClienteRepository;
import java.util.ArrayList;
import java.util.HashMap;

public class ClienteDAOCache implements ClienteRepository {

    private ClienteRepository clienteDAO;
    private HashMap<String, ClienteModel> cache = new HashMap<>();

    public ClienteDAOCache() {
        this.clienteDAO = new ClienteDAO();
    }

    public ClienteDAOCache(ClienteRepository clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public boolean registrar(ClienteModel cliente) {
        boolean registrado = clienteDAO.registrar(cliente);

        if (registrado) {
            cache.put(cliente.getDocumento(), cliente);
        }

        return registrado;
    }

    @Override
    public ArrayList<ClienteModel> listar() {
        ArrayList<ClienteModel> clientes = clienteDAO.listar();

        for (ClienteModel cliente : clientes) {
            cache.put(cliente.getDocumento(), cliente);
        }

        return clientes;
    }

    @Override
    public ClienteModel buscarPorDocumento(String documento) {
        if (cache.containsKey(documento)) {
            return cache.get(documento);
        }

        ClienteModel cliente = clienteDAO.buscarPorDocumento(documento);

        if (cliente != null) {
            cache.put(documento, cliente);
        }

        return cliente;
    }

    @Override
    public ArrayList<ClienteModel> buscarPorNombre(String nombre) {
        ArrayList<ClienteModel> clientes = clienteDAO.buscarPorNombre(nombre);

        for (ClienteModel cliente : clientes) {
            cache.put(cliente.getDocumento(), cliente);
        }

        return clientes;
    }

    @Override
    public boolean eliminar(String documento) {
        boolean eliminado = clienteDAO.eliminar(documento);

        if (eliminado) {
            cache.remove(documento);
        }

        return eliminado;
    }
}
