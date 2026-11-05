
package controllers;

import dao.AlquilerDAO;
import models.AlquilerModel;
import models.EquipoModel;
import services.AlquilerService;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AlquilerController {

    private AlquilerDAO alquilerDAO = new AlquilerDAO();
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
        return alquilerDAO.listar();
    }

    public AlquilerModel buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }

        return alquilerDAO.buscarPorId(id);
    }

    public boolean actualizarDevolucion(int id, boolean devuelto) {
        if (id <= 0 || !devuelto) {
            return false;
        }

        return alquilerService.devolver(id);
    }

    public ArrayList<AlquilerModel> listarPendientes() {
        return alquilerDAO.listarPendientes();
    }

    public ArrayList<String> reporteMensual(int mes, int anio) {
        if (mes < 1 || mes > 12 || anio < 1) {
            return new ArrayList<>();
        }

        return alquilerDAO.reporteMensual(mes, anio);
    }
}
