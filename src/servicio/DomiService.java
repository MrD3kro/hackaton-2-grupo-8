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


}
