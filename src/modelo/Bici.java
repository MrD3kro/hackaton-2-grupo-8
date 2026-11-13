package modelo;

public class Bici extends Repartidor{
    public Bici (String codigo, String nombre, boolean disponible){
        super(codigo, nombre,disponible);
    }

    @Override
    public String getTipo() {
        return "Bici";
    }

    @Override
    public double calcularCosto(double km) {
        return 2000+(km*800);
    }

    @Override
    public double calcularTiempo(double km) {
        return km/15 * 60;
    }

    @Override
    public boolean puedeLlevar(double km, boolean esGrande) {
        return km <= 5 && !esGrande;
    }
    @Override
    public boolean operarlluvia(){
        return false;
    }
}
