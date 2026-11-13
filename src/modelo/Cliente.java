package modelo;


import servicio.DomiService;

public class Cliente {
    private String cedula;
    private String nombre;
    private String telefono;
    private String barrio;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String telefono, String barrio, String direccion) {
        setCedula(cedula); //Se hace de esta manera para un mejor uso de las validaciones
        setNombre(nombre);
        setTelefono(telefono);
        setBarrio(barrio);
        setDireccion(direccion);
    }


    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula no puede estar vacía");
        }
        if (cedula.length() < 3 || cedula.length() > 10) {
            throw new IllegalArgumentException("El cédula debe tener entre 7 y 10 dígitos");
        }
        for (int i = 0; i < cedula.length(); i++) {
            if (!Character.isDigit(cedula.charAt(i))) {
                throw new IllegalArgumentException("El cédula solo debe contener números");
            }
        }
        this.cedula = cedula.trim();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        String nombreLimpio = nombre.trim();
        if (!nombreLimpio.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("El nombre debe solo contener letras");
        }

        this.nombre = nombre.trim();
    }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo");
        }
        telefono = telefono.trim();
        if (telefono.length() < 7 || telefono.length() > 10) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos");
        }
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                throw new IllegalArgumentException("El teléfono solo debe contener números");
            }
        }
        this.telefono = telefono;
    }

    public void setBarrio(String barrio) {
        if (barrio == null || barrio.trim().isEmpty()) {
            throw new IllegalArgumentException("El barrio no puede estar vacío");
        }
        if (!DomiService.esBarrioValido(barrio)){
            throw new IllegalArgumentException("Barrio invalido o no cubierto");
        }
        this.barrio = barrio.trim();
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }

        String dirLimpia = direccion.trim();

        String regexDireccion = "^(?i)(calle|cl|cll|carrera|cra|kr|avenida|av|diagonal|dg|transversal|tv)\\.?\\s+[0-9a-zA-Z]+\\s*(#|no\\.?|n°)?\\s*[0-9a-zA-Z]+\\s*-\\s*[0-9]+(\\s+(sur|norte|este|oeste))?$";


        if (!dirLimpia.matches(regexDireccion)){
            throw new IllegalArgumentException("El formato de la dirección no es válido");
        }
        this.direccion = direccion.trim();

    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDireccion() {
        return direccion;
    }


}

//============================//
