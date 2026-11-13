package modelo;

public class Pedido {
    private static int consecutivo = 1;
    private final int numero;
    private Cliente cliente;
    private String descripcion;
    private boolean esGrande;
    private double km;
    private String repartidorAsignado;
    private double costo;
    private double tiempo;
    private String estado;

    public Pedido(Cliente cliente, String descripcion, boolean esGrande, double km) {
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
    public boolean isEsGrande() {
        return esGrande;
    }
    public double getKm() {
        return km;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
}
