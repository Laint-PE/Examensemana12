package views;

import controllers.EquipoController;
import models.EquipoModel;
import models.EquipoFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class EquipoView {

    private final Scanner scanner;
    private final EquipoController equipoController;

    public EquipoView(Scanner scanner, EquipoController equipoController) {
        this.scanner = scanner;
        this.equipoController = equipoController;
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== GESTION DE EQUIPOS =====");
            System.out.println("1. Registrar equipo");
            System.out.println("2. Listar equipos");
            System.out.println("3. Eliminar equipo");
            System.out.println("4. Volver");
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
                        eliminar();
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 4);
    }

    private void registrar() {
        try {
            ArrayList<String> tipos = EquipoFactory.getTiposRegistrados();

            System.out.println("\n===== TIPOS DE EQUIPO =====");

            for (int i = 0; i < tipos.size(); i++) {
                System.out.println((i + 1) + ". " + tipos.get(i));
            }

            System.out.print("Seleccione el tipo: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion < 1 || opcion > tipos.size()) {
                System.out.println("Tipo no valido.");
                return;
            }

            String tipo = tipos.get(opcion - 1);

            System.out.print("Codigo: ");
            int codigo = Integer.parseInt(scanner.nextLine());

            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine().trim();

            System.out.print("Tarifa por dia: ");
            BigDecimal tarifa = new BigDecimal(scanner.nextLine().trim());

            if (descripcion.isEmpty() || tarifa.compareTo(BigDecimal.ZERO) <= 0) {
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
        ArrayList<EquipoModel> equipos = equipoController.listar();

        if (equipos.isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return;
        }

        for (EquipoModel equipo : equipos) {
            System.out.println("\nCodigo: " + equipo.getCodigo());
            System.out.println("Descripcion: " + equipo.getDescripcion());
            System.out.println("Tipo: " + equipo.getTipo());
            System.out.println("Tarifa diaria: S/ " + equipo.getTarifaDia().toPlainString());
            System.out.println("Disponible: " + (equipo.isDisponible() ? "Si" : "No"));
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