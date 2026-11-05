
package views;

import controllers.AlquilerController;
import controllers.ClienteController;
import controllers.EquipoController;
import models.AlquilerModel;
import models.ClienteModel;
import models.EquipoModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AlquilerView {

    private Scanner scanner;
    private AlquilerController alquilerController = new AlquilerController();
    private ClienteController clienteController = new ClienteController();
    private EquipoController equipoController = new EquipoController();

    public AlquilerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== GESTION DE ALQUILERES =====");
            System.out.println("1. Registrar alquiler");
            System.out.println("2. Listar alquileres");
            System.out.println("3. Registrar devolucion");
            System.out.println("4. Buscar alquiler");
            System.out.println("5. Listar alquileres pendientes");
            System.out.println("6. Reporte mensual");
            System.out.println("7. Volver");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        listar();
                        break;
                    case 3:
                        devolver();
                        break;
                    case 4:
                        buscar();
                        break;
                    case 5:
                        listarPendientes();
                        break;
                    case 6:
                        reporteMensual();
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 7);
    }

    private void registrar() {
        try {
            System.out.print("DNI o RUC del cliente: ");
            String documento = scanner.nextLine().trim();

            ClienteModel cliente = clienteController.buscarPorDocumento(documento);

            if (cliente == null) {
                System.out.println("El cliente no existe.");
                return;
            }

            System.out.print("Codigo del equipo: ");
            int codigo = Integer.parseInt(scanner.nextLine());

            EquipoModel equipo = null;

            for (EquipoModel elemento : equipoController.listar()) {
                if (elemento.getCodigo() == codigo) {
                    equipo = elemento;
                    break;
                }
            }

            if (equipo == null) {
                System.out.println("El equipo no existe.");
                return;
            }

            if (!equipo.isDisponible()) {
                System.out.println("El equipo no esta disponible.");
                return;
            }

            System.out.print("Cantidad de dias: ");
            int dias = Integer.parseInt(scanner.nextLine());

            if (dias <= 0) {
                System.out.println("Los dias deben ser mayores a cero.");
                return;
            }

            double monto = alquilerController.calcularMonto(equipo, dias);

            System.out.println("Monto del alquiler: S/ " + monto);
            System.out.print("¿Desea confirmar el alquiler? (1. Si / 2. No): ");
            int confirmar = Integer.parseInt(scanner.nextLine());

            if (confirmar != 1) {
                System.out.println("Registro cancelado.");
                return;
            }

            AlquilerModel alquiler = new AlquilerModel(
                    0,
                    cliente,
                    equipo,
                    LocalDate.now(),
                    dias,
                    monto,
                    false
            );

            if (alquilerController.registrar(alquiler)) {
                System.out.println("Alquiler registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el alquiler. Verifique la disponibilidad del equipo.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese valores numericos validos.");
        }
    }

    private void listar() {
        ArrayList<AlquilerModel> alquileres = alquilerController.listar();

        if (alquileres.isEmpty()) {
            System.out.println("No hay alquileres registrados.");
            return;
        }

        for (AlquilerModel alquiler : alquileres) {
            mostrarInformacion(alquiler);
        }
    }

    private void buscar() {
        try {
            System.out.print("Ingrese el ID del alquiler: ");
            int id = Integer.parseInt(scanner.nextLine());

            AlquilerModel alquiler = alquilerController.buscarPorId(id);

            if (alquiler != null) {
                mostrarInformacion(alquiler);
            } else {
                System.out.println("Alquiler no encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un ID valido.");
        }
    }

    private void devolver() {
        try {
            System.out.print("Ingrese el ID del alquiler: ");
            int id = Integer.parseInt(scanner.nextLine());

            AlquilerModel alquiler = alquilerController.buscarPorId(id);

            if (alquiler == null) {
                System.out.println("Alquiler no encontrado.");
                return;
            }

            if (alquiler.isDevuelto()) {
                System.out.println("Este alquiler ya fue devuelto.");
                return;
            }

            if (alquilerController.actualizarDevolucion(id, true)) {
                System.out.println("Devolucion registrada correctamente.");
            } else {
                System.out.println("No se pudo registrar la devolucion.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un ID valido.");
        }
    }

    private void listarPendientes() {
        ArrayList<AlquilerModel> alquileres = alquilerController.listarPendientes();

        if (alquileres.isEmpty()) {
            System.out.println("No hay alquileres pendientes.");
            return;
        }

        for (AlquilerModel alquiler : alquileres) {
            mostrarInformacion(alquiler);
        }
    }

    private void reporteMensual() {
        try {
            System.out.print("Ingrese el mes (1-12): ");
            int mes = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese el anio: ");
            int anio = Integer.parseInt(scanner.nextLine());

            ArrayList<String> reporte = alquilerController.reporteMensual(mes, anio);

            if (reporte.isEmpty()) {
                System.out.println("No hay ingresos registrados para ese periodo.");
                return;
            }

            System.out.println("\n===== REPORTE MENSUAL =====");

            for (String linea : reporte) {
                System.out.println(linea);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un mes y anio validos.");
        }
    }

    private void mostrarInformacion(AlquilerModel alquiler) {
        System.out.println("\n===== ALQUILER =====");
        System.out.println("ID: " + alquiler.getId());
        System.out.println("Cliente: " + alquiler.getCliente().getNombre());
        System.out.println("Documento: " + alquiler.getCliente().getDocumento());
        System.out.println("Equipo: " + alquiler.getEquipo().getDescripcion());
        System.out.println("Codigo: " + alquiler.getEquipo().getCodigo());
        System.out.println("Fecha de inicio: " + alquiler.getFechaInicio());
        System.out.println("Dias: " + alquiler.getDias());
        System.out.println("Monto: S/ " + alquiler.getMontoCobrado());
        System.out.println("Devuelto: " + (alquiler.isDevuelto() ? "Si" : "No"));
    }
}
