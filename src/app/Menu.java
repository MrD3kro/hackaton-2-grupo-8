package app;


import modelo.Cliente;
import servicio.DomiService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu extends DomiService {
    Scanner scanner = new Scanner(System.in);
    boolean salir = false;

    private void registrarCliente() {

        System.out.println("/*Registrando cliente*/\n");
            Cliente cliente = new Cliente();
            do {

            }while ()
            try {
                System.out.println("Ingrese la cedula: ");
                String cedula = scanner.next();
                cliente.setCedula(cedula);

            } catch (Exception e) {
                System.out.println("Vuelve a intentarlo");
            }

            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nombre: ");
            String telefono = scanner.nextLine();
            System.out.println("Ingrese el telefono: ");
            String barrio = scanner.nextLine();
            System.out.println("Ingrese barrio: ");
            String direccion = scanner.nextLine();
            System.out.println("Ingrese la Direccion: ");



        clientes.put(cliente.getCedula(),cliente);

    }

    private void listarClientes(){
        if (clientes.isEmpty()){
            System.out.println("No hay clintes para mostrar");
            return;
        }
        System.out.println(clientes.values());

    }

    public  void correrMenu() {
        do {

            System.out.println("================= Domi º Operador =================\nClientes\t1. Registrar Cliente" +
                    "\n\t\t\t2. Listar CLientes");
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

}
