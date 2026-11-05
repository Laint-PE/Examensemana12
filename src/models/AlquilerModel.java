
package models;

import java.time.LocalDate;

public class AlquilerModel {

    private int id;
    private ClienteModel cliente;
    private EquipoModel equipo;
    private LocalDate fechaInicio;
    private int dias;
    private double montoCobrado;
    private boolean devuelto;

    public AlquilerModel(int id, ClienteModel cliente, EquipoModel equipo, LocalDate fechaInicio, int dias, double montoCobrado, boolean devuelto) {
        this.id = id;
        this.cliente = cliente;
        this.equipo = equipo;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.montoCobrado = montoCobrado;
        this.devuelto = devuelto;
    }

    public int getId() {
        return id;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public EquipoModel getEquipo() {
        return equipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getDias() {
        return dias;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public boolean isDevuelto() {
        return devuelto;
    }
}
