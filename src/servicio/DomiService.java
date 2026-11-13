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

}
