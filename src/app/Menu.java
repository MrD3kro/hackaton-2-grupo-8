package app;


import modelo.Cliente;
import servicio.DomiService;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

public class Menu  {
    Scanner scanner = new Scanner(System.in);
    boolean salir = false;
    DomiService domi = new DomiService();
    private boolean leerDato(String etiqueta, Consumer<String> setter, boolean permiteCancelar) {
        while (true) {
            System.out.print(etiqueta + (permiteCancelar ? " (0 para cancelar): " : ": "));
            String entrada = scanner.nextLine();

            if (permiteCancelar && entrada.trim().equals("0")) {
                return false; // el usuario canceló
            }
            try {
                setter.accept(entrada);   // aquí se dispara la validación del setter
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage()); // y vuelve a pedir el dato
            }
        }
    }

        private void registrarCliente() {
            System.out.println("/*Registrando cliente*/\n");
            Cliente cliente = new Cliente();

            if (!leerDato("Ingrese la cédula", cliente::setCedula, true)) {
                System.out.println("Registro cancelado");
                return;
            }
            if (domi.clientes.containsKey(cliente.getCedula())) {
                System.out.println("Ya existe un cliente con esa cédula");
                return;
            }

            leerDato("Ingrese el nombre", cliente::setNombre, false);
            leerDato("Ingrese el teléfono", cliente::setTelefono, false);
            leerDato("Ingrese el barrio", cliente::setBarrio, false);
            leerDato("Ingrese la dirección", cliente::setDireccion, false);

            domi.clientes.put(cliente.getCedula(), cliente);
            System.out.println("Cliente registrado");
        }





private void listarClientes() {
    if (domi.clientes.isEmpty()) {
        System.out.println("No hay clintes para mostrar");
        return;
    }

    for (var entry : domi.clientes.entrySet()) {
        Cliente c = entry.getValue();
        System.out.println("Cedula: " + entry.getKey() + " | Nombre: " + c.getNombre() + "| telefono: "+ c.getTelefono() + " | Barrio: " +
               c.getBarrio() + " | Direccion: " + c.getDireccion());
    }

}

public void correrMenu() {
    do {

        System.out.println("================= Domi º Operador =================\nClientes\t1. Registrar Cliente" +
                "\n\t\t\t2. Listar CLientes" +
                "\n\t\t\t8. Salir");
        System.out.print("Ingresa una opcion: ");
        try {
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> listarClientes();

                case 8 -> salir = true;
                default -> System.out.println("Opcion invalida");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Ingresa el numero de la opcion que deseas realizar");
            scanner.next();

        }

    } while (!salir);
    scanner.close();

}

    public static class Main {
        static void main(String[] args) {

            Menu menu = new Menu();

            menu.correrMenu();

        }
    }
}
