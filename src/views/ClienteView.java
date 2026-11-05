
package views;

import controllers.ClienteController;
import models.ClienteModel;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteView {

    private final Scanner scanner;
    private final ClienteController clienteController;

    public ClienteView(Scanner scanner, ClienteController clienteController) {
        this.scanner = scanner;
        this.clienteController = clienteController;
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n===== GESTION DE CLIENTES =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar por documento");
            System.out.println("4. Buscar por nombre");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Volver");
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
                        buscarPorDocumento();
                        break;
                    case 4:
                        buscarPorNombre();
                        break;
                    case 5:
                        eliminar();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 6);
    }

    private void registrar() {
        System.out.print("DNI o RUC: ");
        String documento = scanner.nextLine().trim();

        if (!documento.matches("[0-9]{8}|[0-9]{11}")) {
            System.out.println("El documento debe tener 8 o 11 digitos.");
            return;
        }

        if (clienteController.buscarPorDocumento(documento) != null) {
            System.out.println("El cliente ya se encuentra registrado.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            System.out.println("Complete todos los datos.");
            return;
        }

        ClienteModel cliente = new ClienteModel(documento, nombre, telefono);

        if (clienteController.registrar(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private void listar() {
        ArrayList<ClienteModel> clientes = clienteController.listar();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (ClienteModel cliente : clientes) {
            mostrarInformacion(cliente);
        }
    }

    private void buscarPorDocumento() {
        System.out.print("Ingrese el DNI o RUC: ");
        String documento = scanner.nextLine().trim();

        if (documento.isEmpty()) {
            System.out.println("Ingrese un documento.");
            return;
        }

        ClienteModel cliente = clienteController.buscarPorDocumento(documento);

        if (cliente != null) {
            mostrarInformacion(cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void buscarPorNombre() {
        System.out.print("Ingrese el nombre o parte del nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("Ingrese un nombre para realizar la busqueda.");
            return;
        }

        ArrayList<ClienteModel> clientes = clienteController.buscarPorNombre(nombre);

        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes.");
            return;
        }

        for (ClienteModel cliente : clientes) {
            mostrarInformacion(cliente);
        }
    }

    private void eliminar() {
        System.out.print("Ingrese el DNI o RUC del cliente: ");
        String documento = scanner.nextLine().trim();

        if (documento.isEmpty()) {
            System.out.println("Ingrese un documento.");
            return;
        }

        if (clienteController.eliminar(documento)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar. Verifique el documento o sus alquileres registrados.");
        }
    }

    private void mostrarInformacion(ClienteModel cliente) {
        System.out.println("\n===== CLIENTE =====");
        System.out.println("Documento: " + cliente.getDocumento());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Telefono: " + cliente.getTelefono());
    }
}
