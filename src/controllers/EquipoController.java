
package controllers;

import dao.EquipoDAO;
import models.EquipoModel;
import java.util.ArrayList;

public class EquipoController {

    private EquipoDAO equipoDAO = new EquipoDAO();

    public boolean registrar(EquipoModel equipo) {
        if (equipo == null || equipo.getCodigo() <= 0 ||
                equipo.getDescripcion() == null ||
                equipo.getDescripcion().trim().isEmpty() ||
                equipo.getTarifaDia() <= 0) {
            return false;
        }

        return equipoDAO.registrar(equipo);
    }

    public ArrayList<EquipoModel> listar() {
        return equipoDAO.listar();
    }

    public boolean actualizarDisponibilidad(int codigo, boolean disponible) {
        if (codigo <= 0) {
            return false;
        }

        return equipoDAO.actualizarDisponibilidad(codigo, disponible);
    }

    public boolean eliminar(int codigo) {
        if (codigo <= 0) {
            return false;
        }

        return equipoDAO.eliminar(codigo);
    }
}
