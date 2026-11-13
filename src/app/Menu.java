package app;

import modelo.*;
import servicio.DomiService;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    boolean salir = false;
    DomiService domi = new DomiService();

    private boolean leerDato(String etiqueta, Consumer<String> setter, boolean permiteCancelar) {
        while (true) {
            System.out.print(etiqueta + (permiteCancelar ? " (0 para cancelar): " : ": "));
            String entrada = scanner.nextLine();

            if (permiteCancelar && entrada.trim().equals("0")) {
                return false;
            }
            try {
                setter.accept(entrada);
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registrarCliente() {
        System.out.println("\n--- Registrando cliente ---");
        Cliente cliente = new Cliente();

        if (!leerDato("Ingrese la cédula", cliente::setCedula, true)) {
            System.out.println("Registro cancelado");
            return;
        }

        // Asumiendo que agregaste un getClientes() en DomiService, si no, usa domi.clientes
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
            System.out.println("No hay clientes para mostrar");
            return;
        }

        for (var entry : domi.clientes.entrySet()) {
            Cliente c = entry.getValue();
            System.out.println("Cédula: " + entry.getKey() + " | Nombre: " + c.getNombre() + " | Teléfono: " + c.getTelefono() + " | Barrio: " + c.getBarrio() + " | Dirección: " + c.getDireccion());
        }
    }

    public void correrMenu() {
        do {
            System.out.println("\n================= Domi º Operador =================");
            System.out.println("1. Registrar Cliente \t\t 2. Listar Clientes");
            System.out.println("3. Editar Cliente \t\t 4. Eliminar Cliente");
            System.out.println("5. Registrar Repartidor \t 6. Listar Repartidores");
            System.out.println("7. Cambiar Disponibilidad \t 8. Crear Pedido");
            System.out.println("9. Entregar Pedido \t\t 10. Cancelar Pedido");
            System.out.println("11. Listar Pedidos \t\t 12. Salir");
            System.out.print("Ingresa una opcion: ");

            try {
                // CORRECCIÓN: Evitar trampa del buffer[cite: 8]
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1 -> registrarCliente();
                    case 2 -> listarClientes();
                    case 3 -> editarCliente();
                    case 4 -> eliminarCliente();
                    case 5 -> registrarRepartidor();
                    case 6 -> listarRepartidor();
                    case 7 -> cambiarDisponibilidad();
                    case 8 -> crearPedido();
                    case 9 -> entregarPedido();
                    case 10 -> cancelarPedido();
                    case 11 -> listarPedidos();
                    case 12 -> salir = true;
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresa el número de la opción que deseas realizar.");
            } catch (IllegalArgumentException e) {
                // CORRECCIÓN: Atrapa todos los errores del servicio para que no se caiga[cite: 8]
                System.out.println("Error de operación: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }

        } while (!salir);
    }

    void editarCliente() {
        System.out.println("\n--- Actualizando cliente ---");
        System.out.print("Cédula del cliente a actualizar: ");
        String cedula = scanner.nextLine().trim();
        Cliente cliente = domi.clientes.get(cedula);

        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        leerDato("Ingrese el nuevo nombre", cliente::setNombre, false);
        leerDato("Ingrese el nuevo teléfono", cliente::setTelefono, false);
        leerDato("Ingrese el nuevo barrio", cliente::setBarrio, false);
        leerDato("Ingrese la nueva dirección", cliente::setDireccion, false);

        System.out.println("Cliente Actualizado");
    }

    void eliminarCliente() {
        System.out.println("\n--- Eliminar cliente ---");
        System.out.print("Cédula del cliente a eliminar: ");
        String cedula = scanner.nextLine().trim();

        // CORRECCIÓN: Usa el método del servicio para que respete la regla EN_CAMINO[cite: 15]
        domi.eliminarCliente(cedula);
        System.out.println("Cliente eliminado correctamente.");
    }

    void registrarRepartidor() {
        System.out.println("\n--- Registrar Repartidor ---");
        System.out.print("Tipo de vehículo (Bici/Moto/Carro): ");
        String opc = scanner.nextLine().trim().toLowerCase();

        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        switch (opc) {
            case "bici" -> domi.repartidores.add(new Bici(codigo, nombre, true));
            case "moto" -> domi.repartidores.add(new Moto(codigo, nombre, true));
            case "carro" -> domi.repartidores.add(new Carro(codigo, nombre, true));
            default -> System.out.println("Opción de vehículo no válida.");
        }
        System.out.println("Repartidor registrado.");
    }

    void listarRepartidor() {
        System.out.println("\n--- Lista de Repartidores ---");
        for (Repartidor repartidor : domi.repartidores) {
            System.out.println("Código: " + repartidor.getCodigo() + " | Nombre: " + repartidor.getNombre() + " | Disponible: " + (repartidor.isDisponible() ? "Sí" : "No") + " | Vehículo: " + repartidor.getTipo());
        }
    }

    void cambiarDisponibilidad() {
        System.out.print("Código del repartidor: ");
        String codigo = scanner.nextLine().trim();
        boolean encontrado = false;

        for (Repartidor repartidor : domi.repartidores) {
            if (repartidor.getCodigo().equalsIgnoreCase(codigo)) {
                repartidor.setDisponible(!repartidor.isDisponible());
                System.out.println("Disponibilidad de " + repartidor.getNombre() + " cambiada a: " + (repartidor.isDisponible() ? "Disponible" : "Ocupado"));
                encontrado = true;
                break;
            }
        }
        if (!encontrado) System.out.println("Repartidor no encontrado.");
    }

    void crearPedido() {
        System.out.println("\n--- CREAR PEDIDO ---");
        System.out.print("Cédula del cliente: ");
        String cedula = scanner.nextLine().trim();

        System.out.print("Descripción del pedido: ");
        String descripcion = scanner.nextLine().trim();

        System.out.print("¿El pedido es grande? (si/no): ");
        boolean esPedidoGrande = scanner.nextLine().trim().equalsIgnoreCase("si");

        Pedido pedidoCreado = domi.crearPedido(cedula, descripcion, esPedidoGrande);

        System.out.println(String.format("✔ Pedido #%d asignado a %s (%s) · EN_CAMINO",
                pedidoCreado.getNumero(),
                pedidoCreado.getRepartidorAsignado().getNombre(),
                pedidoCreado.getRepartidorAsignado().getTipo()));
    }

    void entregarPedido() {
        System.out.print("Número de pedido a entregar: ");
        int numeroPedido = Integer.parseInt(scanner.nextLine().trim());
        domi.entregarPedido(numeroPedido);
        System.out.println("✔ Pedido #" + numeroPedido + " entregado exitosamente.");
    }

    void cancelarPedido() {
        System.out.print("Número de pedido a cancelar: ");
        int numeroPedido = Integer.parseInt(scanner.nextLine().trim());
        domi.cancelarPedido(numeroPedido);
        System.out.println("✔ Pedido #" + numeroPedido + " cancelado exitosamente.");
    }

    void listarPedidos() {
        System.out.println("\n--- LISTA DE PEDIDOS ---");
        ArrayList<Pedido> listaPedidos = domi.listarPedidos();

        if (listaPedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido p : listaPedidos) {
                // CORRECCIÓN: Imprimir los datos reales de la lista[cite: 17]
                System.out.println(String.format("Pedido #%d | Cliente: %s | Repartidor: %s | Costo: $%,.0f | Tiempo: %.0f min | Estado: %s",
                        p.getNumero(),
                        p.getCliente().getNombre(),
                        p.getRepartidorAsignado().getNombre(),
                        p.getCosto(),
                        p.getTiempo(),
                        p.getEstado()));
            }
        }
    }
}