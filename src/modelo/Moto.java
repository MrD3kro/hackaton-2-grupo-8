package modelo;

public class Moto extends Repartidor{

    public Moto (int codigo, String nombre, boolean disponible){
        super(codigo, nombre,disponible);
    }

    @Override
    public double calcularCosto(double km) {
        return 3000+(km*1000);
    }

    @Override
    public double calcularTiempo(double km) {
        return km/30 * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande) {
        return km <= 15 && !esGrande;
    }
}
