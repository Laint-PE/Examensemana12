
package views;

import java.util.Scanner;

public class MainView {

    private Scanner scanner = new Scanner(System.in);
    private ClienteView clienteView = new ClienteView(scanner);
    private EquipoView equipoView = new EquipoView(scanner);
    private AlquilerView alquilerView = new AlquilerView(scanner);

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== EQUIPAYA =====");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Gestionar equipos");
            System.out.println("3. Gestionar alquileres");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        clienteView.mostrarMenu();
                        break;

                    case 2:
                        equipoView.mostrarMenu();
                        break;

                    case 3:
                        alquilerView.mostrarMenu();
                        break;

                    case 4:
                        System.out.println("Gracias por utilizar EquipaYa.");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 4);
    }
}
