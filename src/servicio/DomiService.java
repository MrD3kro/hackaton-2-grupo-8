package servicio;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Repartidor;

import java.util.*;

public class DomiService {
    protected Map<String, Cliente> clientes = new HashMap<>(); //Hashmap y no lista porque queremos que no se repita cliente por cedula
    private final HashSet<String> barriosCubiertos = new HashSet<>(Set.of(
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
        Cliente cliente = new Cliente("Laura Gomez","1020304050","3109876543","Teusaquillo","diagonal 38 #12 - 23");
        clientes.put(cliente.getCedula(),cliente);
        cliente.setNombre("Mateo Rincon");
        cliente.setCedula("1030405060");
        cliente.setTelefono("3109876543");
        cliente.setBarrio("Usaquen");
        clientes.put(cliente.getCedula(),cliente);
    }

    public double obtenerKm(String barrio) {
        Double km = distanciasBarriosKm.get(barrio);
        if (km == null) {
            throw new IllegalArgumentException("Barrio no cubierto: " + barrio);
        }
        return km;
    }


    public void crearPedido(String cedula, String descripcion, boolean esPedidoGrande){

        System.out.println("Cedula del cliente: ");

        Cliente cliente = clientes.get(cedula);

        if(cliente == null){
            throw new IllegalArgumentException("El cliente no esta registrado en nuestro servicio");
        }

        Double distancia = distanciasBarriosKm.get(cliente.getBarrio());

        System.out.println(String.format("Cotizando pedido para %s | %s | (%.2f km) ", cliente.getNombre(), cliente.getBarrio(), distancia));

        Repartidor repartidorAsignado= asignarRepartidor(distancia, esPedidoGrande);

        if (repartidorAsignado != null) {
            repartidorAsignado.setDisponible(false);

            Pedido nuevoPedido = new Pedido(cliente, descripcion, esPedidoGrande, distancia);
            pedidos.add(nuevoPedido);

            System.out.println(String.format("✔ Pedido #%d asignado a %s (%s) · EN_CAMINO",
                    nuevoPedido.getNumero(), repartidorAsignado.getNombre(), repartidorAsignado.getTipo()));
        } else {
            throw new IllegalArgumentException("No hay repartidores disponibles que puedan llevar este pedido.");
        }
        pedidos.add(new Pedido(cliente, descripcion, esPedidoGrande, distancia));
    }

    public Repartidor asignarRepartidor(Double distancia, boolean esPedidoGrande){

        double menorCosto = Double.MAX_VALUE;
        double menorTiempo = Integer.MAX_VALUE;
        Repartidor mejorRepartidor = null;

        for(Repartidor repartidor: repartidores){
            if(repartidor.isDisponible() && (!esPedidoGrande || repartidor.getTipo().equals("Carro"))){

                double costoRepartidor = repartidor.calcularCosto(distancia);
                double tiempoRepartidor = repartidor.calcularTiempo(distancia);

                System.out.println(String.format("%s   %s   (%s)   %.2f   %.2f min", repartidor.getCodigo(), repartidor.getNombre(), repartidor.getTipo(), costoRepartidor, tiempoRepartidor));

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
