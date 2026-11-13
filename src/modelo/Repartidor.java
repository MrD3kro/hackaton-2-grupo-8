package modelo;

import java.util.ArrayList;

public abstract class Repartidor implements Calificable{

    private String codigo;
    private String nombre;
    private boolean disponible;

    private ArrayList<Integer> calificaciones = new ArrayList<>(); //el integer es porq no deja trabajar con int


    public Repartidor (String codigo, String nombre, boolean disponible){
        if(codigo.isEmpty()){
        throw new IllegalArgumentException("El codigo debe ser valido");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre debe ser valido");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.disponible = disponible;
    }

    public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public abstract String getTipo();

    public abstract double calcularCosto(double km);

    public abstract double calcularTiempo(double km);

    public abstract boolean puedeLlevar(double km, boolean esGrande);


    @Override
    public void recibirCalificacion(int calificacion){
        if(calificacion < 1 || calificacion > 5){
            throw new IllegalArgumentException("El calificacion debe ser entre 1 y 5");
        }
        calificaciones.add(calificacion);
    }

    @Override
    public double promedio() {
        double sumarAcumulador=0;
        if(calificaciones.isEmpty()){
            return 0;
        }
        for(int i = 0; i < calificaciones.size(); i++){
            sumarAcumulador += calificaciones.get(i);
        }
        return sumarAcumulador/calificaciones.size();

    }

}
