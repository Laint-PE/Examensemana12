package models;

public class AlquilerModel {

    private int id;
    private ClienteModel cliente;
    private EquipoModel equipo;
    private int dias;
    private double montoCobrado;
    private boolean devuelto;

    public AlquilerModel(int id, ClienteModel cliente, EquipoModel equipo, int dias, double montoCobrado, boolean devuelto) {
        this.id = id;
        this.cliente = cliente;
        this.equipo = equipo;
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