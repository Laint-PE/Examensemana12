package controllers;

import dao.EquipoDAO;
import models.EquipoModel;
import java.util.ArrayList;

public class EquipoController {

    private EquipoDAO equipoDAO = new EquipoDAO();

    public boolean registrar(EquipoModel equipo, String tipo) {
        if (equipo == null) {
            return false;
        }

        return equipoDAO.registrar(equipo, tipo);
    }

    public ArrayList<EquipoModel> listar() {
        return equipoDAO.listar();
    }

    public boolean actualizarDisponibilidad(int codigo, boolean disponible) {
        return equipoDAO.actualizarDisponibilidad(codigo, disponible);
    }

    public boolean eliminar(int codigo) {
        return equipoDAO.eliminar(codigo);
    }
}