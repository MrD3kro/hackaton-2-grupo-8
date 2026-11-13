package modelo;

public class Pedido {
    private static int consecutivo = 1;
    private final int numero;
    private Cliente cliente;
    private String descripcion;
    private boolean esGrande;
    private double km;
    private Repartidor repartidorAsignado;
    private int costo;
    private int tiempo;
    private String estado;

    public Pedido(Cliente cliente, String descripcion, boolean esGrande, double km, Repartidor repartidorAsignado, int costoFinal, int tiempoFinal) {
        if (cliente == null) {
            throw new IllegalArgumentException("El pedido debe tener un cliente");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (km <= 0) {
            throw new IllegalArgumentException("Los km deben ser mayores a 0");
        }

        this.numero = consecutivo++;
        this.cliente = cliente;
        this.descripcion = descripcion.trim();
        this.esGrande = esGrande;
        this.km = km;
        this.estado = "EN_CAMINO";
        this.repartidorAsignado = repartidorAsignado;
        this.costo = costoFinal;
        this.tiempo = tiempoFinal;
    }

    public int getNumero() {
        return numero;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getCosto() {
        return costo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEsGrande() {
        return esGrande;
    }
    public double getKm() {
        return km;
    }
}
