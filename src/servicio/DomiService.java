package servicio;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Repartidor;

import java.util.*;

public class DomiService {
    public Map<String, Cliente> clientes = new HashMap<>(); //Hashmap y no lista porque queremos que no se repita cliente por cedula
    private static final HashSet<String> barriosCubiertos = new HashSet<>(Set.of(
            "Chapinero", "Teusaquillo", "La Candelaria",
            "Usaquen", "Cedritos", "Fontibon",
            "Suba", "Kennedy", "Bosa"
    )); //
    private final HashMap<String, Double> distanciasBarriosKm = new HashMap<>(Map.of(
            "Chapinero", 1.5d,
            "Teusaquillo", 3.0d,
            "La Candelaria", 4.5d,
            "Usaquen", 6.0d,
            "Cedritos", 8.0d,
            "Fontibon", 11.0d,
            "Suba", 12.0d,
            "Kennedy", 14.0d,
            "Bosa", 18.0d

    )); //El operador nunca escribe los km porque podria equivocarse
    protected ArrayList<Repartidor> repartidores = new ArrayList<>();
    protected ArrayList<Pedido> pedidos = new ArrayList<>();

    public DomiService(){
/*
        initRepartidores();
*/
        initClientes();
    }

    /*private void initRepartidores(){
        repartidores.add(new Bici("R-01", "Camila Torres"));

        repartidores.add(new Moto("R-02", "Andres Mejia"));

        repartidores.add(new Carro("R-03", "Jorge Pardo"));
    }*/

    private void initClientes(){
        Cliente cliente = new Cliente("1020304050","Laura Gomez","3109876543","Teusaquillo","diagonal 38 #12 - 23");
        clientes.put(cliente.getCedula(),cliente);
        Cliente cliente2 = new Cliente("1030405060","Mateo Rincon","3109876543","Usaquen","diagonal 38 #12 - 23");
        clientes.put(cliente2.getCedula(),cliente2);
    }

    public static boolean esBarrioValido(String barrio) {
        if (barrio == null) return false;
        return barriosCubiertos.contains(barrio.trim());
    }

    public double obtenerKm(String barrio) {
        Double km = distanciasBarriosKm.get(barrio);
        if (km == null) {
            throw new IllegalArgumentException("Barrio no cubierto: " + barrio);
        }
        return km;
    }


    public void crearPedido(String cedula, String descripcion, boolean esPedidoGrande) {
        Cliente cliente = clientes.get(cedula);

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no esta registrado en nuestro servicio");
        }

        Double distancia = distanciasBarriosKm.get(cliente.getBarrio());

        System.out.println(String.format("Cotizando pedido para %s | %s | (%.2f km) ", cliente.getNombre(), cliente.getBarrio(), distancia));

        Repartidor repartidorAsignado = asignarRepartidor(distancia, esPedidoGrande);

        if (repartidorAsignado != null) {
            repartidorAsignado.setDisponible(false);

            int costoFinal = (int) Math.round(repartidorAsignado.calcularCosto(distancia));
            int tiempoFinal = (int) Math.round(repartidorAsignado.calcularTiempo(distancia));

            Pedido nuevoPedido = new Pedido(cliente, descripcion, esPedidoGrande, distancia, repartidorAsignado, costoFinal, tiempoFinal);
            pedidos.add(nuevoPedido);

            System.out.println(String.format("✔ Pedido #%d asignado a %s (%s) · EN_CAMINO",
                    nuevoPedido.getNumero(), repartidorAsignado.getNombre(), repartidorAsignado.getTipo()));
        } else {
            throw new IllegalArgumentException("No hay repartidores disponibles que puedan llevar este pedido.");
        }

    }

    public Repartidor asignarRepartidor(Double distancia, boolean esPedidoGrande) {

        double menorCosto = Double.MAX_VALUE;
        double menorTiempo = Integer.MAX_VALUE;
        Repartidor mejorRepartidor = null;

        for (Repartidor repartidor : repartidores) {
            if (repartidor.isDisponible() && repartidor.puedeLlevar(distancia, esPedidoGrande)) {

                double costoRepartidor = repartidor.calcularCosto(distancia);
                double tiempoRepartidor = repartidor.calcularTiempo(distancia);

                System.out.println(String.format("%s   %s   (%s)   %.2f   %.2f min",
                        repartidor.getCodigo(), repartidor.getNombre(), repartidor.getTipo(), costoRepartidor, tiempoRepartidor));

                if (mejorRepartidor == null || costoRepartidor < menorCosto) {
                    mejorRepartidor = repartidor;
                    menorCosto = costoRepartidor;
                    menorTiempo = tiempoRepartidor;
                }
                else if (costoRepartidor == menorCosto && tiempoRepartidor < menorTiempo) {
                    mejorRepartidor = repartidor;
                    menorCosto = costoRepartidor;
                    menorTiempo = tiempoRepartidor;
                }
            }
        }
        return mejorRepartidor;
    }


    private Pedido buscarPedido(int numero) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == numero) {
                return p;
            }
        }
        return null;
    }

    public void entregarPedido(int numero) {
        Pedido pedido = buscarPedido(numero);
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido #" + numero + " no existe.");
        }

        if (!pedido.getEstado().equals("EN_CAMINO")) {
            throw new IllegalArgumentException("El pedido no se puede entregar porque su estado es: " + pedido.getEstado());
        }

        pedido.setEstado("ENTREGADO");
        pedido.getRepartidorAsignado().setDisponible(true);
    }

    public void cancelarPedido(int numero) {
        Pedido pedido = buscarPedido(numero);
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido #" + numero + " no existe.");
        }

        if (!pedido.getEstado().equals("EN_CAMINO")) {
            throw new IllegalArgumentException("El pedido no se puede cancelar porque su estado es: " + pedido.getEstado());
        }

        pedido.setEstado("CANCELADO");
        pedido.getRepartidorAsignado().setDisponible(true);
    }

    public ArrayList<Pedido> listarPedidos() {
        return pedidos;
    }

    public void eliminarCliente(String cedula) {
        if (!clientes.containsKey(cedula)) {
            throw new IllegalArgumentException("El cliente con esa cédula no existe.");
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getCedula().equals(cedula) && pedido.getEstado().equals("EN_CAMINO")) {
                throw new IllegalArgumentException("No se puede eliminar el cliente porque tiene un pedido EN_CAMINO.");
            }
        }

        clientes.remove(cedula);
    }
}

    /*MENU
public void opcionCrearPedido() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Cedula del cliente: ");
    String cedula = sc.nextLine();

    System.out.print("Descripcion del pedido: ");
    String descripcion = sc.nextLine();

    System.out.print("¿El pedido es grande? (si/no): ");
    boolean esPedidoGrande = sc.nextLine().trim().equalsIgnoreCase("si");

    // Aquí LLAMAS a la función de tu DomiService pasándole los datos
    domiService.crearPedido(cedula, descripcion, esPedidoGrande);
}
    * */

}
