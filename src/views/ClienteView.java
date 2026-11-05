
package views;

import controllers.ClienteController;
import models.ClienteModel;
import java.util.Scanner;

public class ClienteView {

    private Scanner scanner;
    private ClienteController clienteController = new ClienteController();

    public ClienteView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== GESTION DE CLIENTES =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Eliminar cliente");
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
                        buscar();
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
            System.out.print("DNI: ");
            int dni = Integer.parseInt(scanner.nextLine());

            if (dni < 10000000 || dni > 99999999) {
                System.out.println("El DNI debe tener 8 digitos.");
                return;
            }

            if (clienteController.buscarPorDni(dni) != null) {
                System.out.println("El cliente ya se encuentra registrado.");
                return;
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Telefono: ");
            String telefono = scanner.nextLine();

            if (nombre.trim().isEmpty() || telefono.trim().isEmpty()) {
                System.out.println("Complete todos los datos.");
                return;
            }

            ClienteModel cliente = new ClienteModel(dni, nombre, telefono);

            if (clienteController.registrar(cliente)) {
                System.out.println("Cliente registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el cliente.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El DNI debe ser numerico.");
        }
    }

    private void listar() {
        for (ClienteModel cliente : clienteController.listar()) {
            System.out.println("\nDNI: " + cliente.getDni());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Telefono: " + cliente.getTelefono());
        }
    }

    private void buscar() {
        try {
            System.out.print("Ingrese el DNI: ");
            int dni = Integer.parseInt(scanner.nextLine());

            ClienteModel cliente = clienteController.buscarPorDni(dni);

            if (cliente != null) {
                System.out.println("\nDNI: " + cliente.getDni());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Telefono: " + cliente.getTelefono());
            } else {
                System.out.println("Cliente no encontrado.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un DNI valido.");
        }
    }

    private void eliminar() {
        try {
            System.out.print("Ingrese el DNI del cliente: ");
            int dni = Integer.parseInt(scanner.nextLine());

            if (clienteController.eliminar(dni)) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar. Verifique el DNI o si tiene alquileres registrados.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un DNI valido.");
        }
    }
}
