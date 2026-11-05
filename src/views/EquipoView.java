
package views;

import controllers.EquipoController;
import models.EquipoModel;
import models.EquipoFactory;
import java.util.Scanner;

public class EquipoView {

    private final Scanner scanner;
    private final EquipoController equipoController = new EquipoController();

    public EquipoView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== GESTION DE EQUIPOS =====");
            System.out.println("1. Registrar equipo");
            System.out.println("2. Listar equipos");
            System.out.println("3. Actualizar disponibilidad");
            System.out.println("4. Eliminar equipo");
            System.out.println("5. Volver");
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
                        actualizarDisponibilidad();
                        break;
                    case 4:
                        eliminar();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 5);
    }

    private void registrar() {
        try {
            System.out.println("\n1. Camara");
            System.out.println("2. Audio");
            System.out.println("3. Iluminacion");
            System.out.print("Seleccione el tipo: ");

            int opcion = Integer.parseInt(scanner.nextLine());
            String tipo;

            switch (opcion) {
                case 1:
                    tipo = "CAMARA";
                    break;
                case 2:
                    tipo = "AUDIO";
                    break;
                case 3:
                    tipo = "ILUMINACION";
                    break;
                default:
                    System.out.println("Tipo no valido.");
                    return;
            }

            System.out.print("Codigo: ");
            int codigo = Integer.parseInt(scanner.nextLine());

            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine().trim();

            System.out.print("Tarifa por dia: ");
            double tarifa = Double.parseDouble(scanner.nextLine());

            if (descripcion.isEmpty() || !Double.isFinite(tarifa) || tarifa <= 0) {
                System.out.println("Ingrese datos validos.");
                return;
            }

            EquipoModel equipo = EquipoFactory.crearEquipo(
                    tipo, codigo, descripcion, tarifa, true
            );

            if (equipoController.registrar(equipo)) {
                System.out.println("Equipo registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el equipo.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese valores numericos validos.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listar() {
        if (equipoController.listar().isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return;
        }

        for (EquipoModel equipo : equipoController.listar()) {
            System.out.println("\nCodigo: " + equipo.getCodigo());
            System.out.println("Descripcion: " + equipo.getDescripcion());
            System.out.println("Tipo: " + equipo.getTipo());
            System.out.printf("Tarifa diaria: S/ %.2f%n", equipo.getTarifaDia());
            System.out.println("Disponible: " + (equipo.isDisponible() ? "Si" : "No"));
        }
    }

    private void actualizarDisponibilidad() {
        try {
            System.out.print("Codigo del equipo: ");
            int codigo = Integer.parseInt(scanner.nextLine());

            System.out.print("¿Esta disponible? (1. Si / 2. No): ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opcion no valida.");
                return;
            }

            if (equipoController.actualizarDisponibilidad(codigo, opcion == 1)) {
                System.out.println("Disponibilidad actualizada.");
            } else {
                System.out.println("No se pudo actualizar el equipo.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese valores numericos validos.");
        }
    }

    private void eliminar() {
        try {
            System.out.print("Codigo del equipo: ");
            int codigo = Integer.parseInt(scanner.nextLine());

            if (equipoController.eliminar(codigo)) {
                System.out.println("Equipo eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar. Verifique el codigo o sus alquileres registrados.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un codigo valido.");
        }
    }
}
