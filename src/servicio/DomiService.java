package servicio;

import modelo.Bici;
import modelo.Carro;
import modelo.Cliente;
import modelo.Moto;
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
        initRepartidores();
        initClientes();
    }

    private void initRepartidores(){
        repartidores.add(new Bici(1, "Camila Torres", true));
        repartidores.add(new Moto(2, "Andres Mejia", true));
        repartidores.add(new Carro(3, "Jorge Pardo", true));
    }

    private void initClientes(){
        Cliente laura = new Cliente("1020304050", "Laura Gomez", "3109876543", "Teusaquillo", "diagonal 38 #12 - 23");
        clientes.put(laura.getCedula(), laura);

        Cliente mateo = new Cliente("1030405060", "Mateo Rincon", "3109876543", "Usaquen", "calle 100 #15 - 40");
        clientes.put(mateo.getCedula(), mateo);
    }

    public double obtenerKm(String barrio) {
        Double km = distanciasBarriosKm.get(barrio);
        if (km == null) {
            throw new IllegalArgumentException("Barrio no cubierto: " + barrio);
        }
        return km;
    }


    public void crearPedido(String cedula, String descripcion, boolean esPedidoGrande){

        Cliente cliente = clientes.get(cedula);

        if(cliente == null){
            throw new IllegalArgumentException("El cliente no esta registrado en nuestro servicio");
        }

        double distancia = obtenerKm(cliente.getBarrio());

        System.out.println(String.format("Cotizando pedido para %s | %s | (%.2f km) ", cliente.getNombre(), cliente.getBarrio(), distancia));

        Repartidor repartidorAsignado = asignarRepartidor(distancia, esPedidoGrande);

        if (repartidorAsignado != null) {

            double costoBase = repartidorAsignado.calcularCosto(distancia);
            double costoFinal = aplicarRecargoLluvia(costoBase);
            double tiempo = repartidorAsignado.calcularTiempo(distancia);

            repartidorAsignado.setDisponible(false);

            Pedido nuevoPedido = new Pedido(cliente, descripcion, esPedidoGrande, distancia);
            nuevoPedido.setCosto(costoFinal);
            nuevoPedido.setTiempo(tiempo);
            nuevoPedido.setRepartidorAsignado(repartidorAsignado.getNombre());
            pedidos.add(nuevoPedido);

            System.out.println(String.format("✔ Pedido #%d asignado a %s (%s) · EN_CAMINO · $%.2f · %.2f min",
                    nuevoPedido.getNumero(), repartidorAsignado.getNombre(), repartidorAsignado.getTipo(), costoFinal, tiempo));
        } else {
            throw new IllegalArgumentException("No hay repartidores disponibles que puedan llevar este pedido.");
        }

    }

    public Repartidor asignarRepartidor(double distancia, boolean esPedidoGrande){

        double menorCosto = Double.MAX_VALUE;
        double menorTiempo = Double.MAX_VALUE;
        Repartidor mejorRepartidor = null;

        for(Repartidor repartidor: repartidores){
            if(puedeAsignarse(repartidor, distancia, esPedidoGrande)){

                double costoRepartidor = aplicarRecargoLluvia(repartidor.calcularCosto(distancia));
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

    private boolean modoLluvia = false;

    public boolean ismodoLluvia(){
        return modoLluvia;
    }

    public void setModoLluvia(boolean modoLluvia){
        this.modoLluvia = modoLluvia;
    }

    private boolean puedeAsignarse(Repartidor repartidor, double km, boolean esGrande){
        if(!repartidor.isDisponible()){
            return false;
        }
        if(!repartidor.puedeLlevar(km,esGrande)){
            return false;
        }
        if(modoLluvia && !repartidor.operarlluvia()){
            return false;
        }
        return true;
    }

    private double aplicarRecargoLluvia(double costoBase){
        if(modoLluvia){
            double recargo = costoBase * 0.20;
            return costoBase + recargo;
        }
        return costoBase;
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
