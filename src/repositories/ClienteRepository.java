
package repositories;

import models.ClienteModel;
import java.util.ArrayList;

public interface ClienteRepository {
    boolean registrar(ClienteModel cliente);
    ArrayList<ClienteModel> listar();
    ClienteModel buscarPorDocumento(String documento);
    ArrayList<ClienteModel> buscarPorNombre(String nombre);
    boolean eliminar(String documento);
}
