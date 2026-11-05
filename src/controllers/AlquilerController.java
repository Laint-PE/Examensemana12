
package controllers;

import dao.AlquilerDAO;
import models.AlquilerModel;
import java.util.ArrayList;

public class AlquilerController {

    private AlquilerDAO alquilerDAO = new AlquilerDAO();

    public boolean registrar(AlquilerModel alquiler) {
        if (alquiler == null || alquiler.getCliente() == null || alquiler.getEquipo() == null) {
            return false;
        }

        if (alquiler.getDias() <= 0 || alquiler.getMontoCobrado() < 0) {
            return false;
        }

        return alquilerDAO.registrar(alquiler);
    }

    public ArrayList<AlquilerModel> listar() {
        return alquilerDAO.listar();
    }

    public AlquilerModel buscarPorId(int id) {
        return alquilerDAO.buscarPorId(id);
    }

    public boolean actualizarDevolucion(int id, boolean devuelto) {
        return alquilerDAO.actualizarDevolucion(id, devuelto);
    }
}
