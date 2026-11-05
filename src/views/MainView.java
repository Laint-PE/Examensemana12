
package views;

import dao.AlquilerDAO;
import dao.ClienteDAOCache;
import dao.EquipoDAO;
import repositories.AlquilerRepository;
import repositories.ClienteRepository;
import repositories.EquipoRepository;
import services.AlquilerService;
import controllers.AlquilerController;
import controllers.ClienteController;
import controllers.EquipoController;
import java.util.Scanner;

public class MainView {

    private final Scanner scanner;

    private final ClienteController clienteController;
    private final EquipoController equipoController;
    private final AlquilerController alquilerController;

    private final ClienteView clienteView;
    private final EquipoView equipoView;
    private final AlquilerView alquilerView;

    public MainView() {
        scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteDAOCache();

        EquipoRepository equipoRepository = new EquipoDAO();

        AlquilerRepository alquilerRepository = new AlquilerDAO();

        AlquilerService alquilerService = new AlquilerService(
                alquilerRepository,
                equipoRepository
        );

        clienteController = new ClienteController(clienteRepository);
        equipoController = new EquipoController(equipoRepository);

        alquilerController = new AlquilerController(
                alquilerRepository,
                alquilerService
        );

        clienteView = new ClienteView(scanner, clienteController);

        equipoView = new EquipoView(scanner, equipoController);

        alquilerView = new AlquilerView(
                scanner,
                alquilerController,
                clienteController,
                equipoController
        );
    }

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
