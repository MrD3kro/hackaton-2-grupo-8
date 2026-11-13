package modelo;

public class Carro extends Repartidor{
    public Carro (String codigo, String nombre, boolean disponible){
        super(codigo, nombre,disponible);
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public double calcularCosto(double km) {
        return 5000+(km*1200);
    }

    @Override
    public double calcularTiempo(double km) {
        return km/25 * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande) {
        return true;
    }
}
