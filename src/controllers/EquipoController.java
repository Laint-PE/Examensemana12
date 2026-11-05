
package controllers;

import dao.EquipoDAO;
import models.EquipoModel;
import java.util.ArrayList;

public class EquipoController {

    private EquipoRepository equipoRepository = new EquipoDAO();

    public boolean registrar(EquipoModel equipo) {
        if (equipo == null || equipo.getCodigo() <= 0 ||
                equipo.getDescripcion() == null ||
                equipo.getDescripcion().trim().isEmpty() ||
                equipo.getTarifaDia() == null ||
                equipo.getTarifaDia().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        return equipoRepository.registrar(equipo);
    }

    public ArrayList<EquipoModel> listar() {
        return equipoRepository.listar();
    }

    public EquipoModel buscarPorCodigo(int codigo) {
        if (codigo <= 0) {
            return null;
        }

        return equipoRepository.buscarPorCodigo(codigo);
    }

    public EquipoModel buscarPorCodigo(int codigo) {
        if (codigo <= 0) {
            return null;
        }

        return equipoDAO.buscarPorCodigo(codigo);
    }

    public boolean actualizarDisponibilidad(int codigo, boolean disponible) {
        if (codigo <= 0) {
            return false;
        }

        return equipoRepository.actualizarDisponibilidad(codigo, disponible);
    }

    public boolean eliminar(int codigo) {
        if (codigo <= 0) {
            return false;
        }

        return equipoRepository.eliminar(codigo);
    }
}
