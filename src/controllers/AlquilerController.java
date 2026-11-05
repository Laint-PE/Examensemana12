
package controllers;

import dao.AlquilerDAO;
import models.AlquilerModel;
import java.util.ArrayList;

public class AlquilerController {

    private AlquilerRepository alquilerRepository = new AlquilerDAO();
    private AlquilerService alquilerService = new AlquilerService();

    public BigDecimal calcularMonto(EquipoModel equipo, int dias) {
        if (equipo == null || dias <= 0) {
            return BigDecimal.ZERO;
        }

        return equipo.calcularMonto(dias);
    }

    public boolean registrar(AlquilerModel alquiler) {
        if (alquiler == null || alquiler.getCliente() == null ||
                alquiler.getEquipo() == null || alquiler.getFechaInicio() == null ||
                alquiler.getDias() <= 0 || alquiler.getMontoCobrado() == null ||
                alquiler.getMontoCobrado().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return alquilerService.registrar(alquiler);
    }

    public ArrayList<AlquilerModel> listar() {
        return alquilerRepository.listar();
    }

    public AlquilerModel buscarPorId(int id) {
        return alquilerDAO.buscarPorId(id);
    }

    public boolean actualizarDevolucion(int id, boolean devuelto) {
        return alquilerDAO.actualizarDevolucion(id, devuelto);
    }
}
