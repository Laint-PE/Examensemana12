package models;

import java.time.LocalDate;

public class AlquilerModel {

    private final int id;
    private final ClienteModel cliente;
    private final EquipoModel equipo;
    private final LocalDate fechaInicio;
    private final int dias;
    private final double montoCobrado;
    private final boolean devuelto;

    public AlquilerModel(int id, ClienteModel cliente, EquipoModel equipo,
                         LocalDate fechaInicio, int dias, double montoCobrado, boolean devuelto) {
        this.id = id;
        this.cliente = cliente;
        this.equipo = equipo;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.montoCobrado = montoCobrado;
        this.devuelto = devuelto;
    }

    public int getId() { return id; }
    public ClienteModel getCliente() { return cliente; }
    public EquipoModel getEquipo() { return equipo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public int getDias() { return dias; }
    public double getMontoCobrado() { return montoCobrado; }
    public boolean isDevuelto() { return devuelto; }
}