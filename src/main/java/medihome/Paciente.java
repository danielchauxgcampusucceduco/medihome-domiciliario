package medihome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paciente extends Usuario {
    private String telefono;
    private String direccion;
    private final List<ServicioDomiciliario> servicios = new ArrayList<>();

    public Paciente(String identificacion, String nombre, String correo, String telefono, String direccion) {
        super(identificacion, nombre, correo);
        setTelefono(telefono);
        setDireccion(direccion);
    }

    public void solicitarServicio(ServicioDomiciliario servicio) {
        if (servicio == null || servicio.getPaciente() != this) {
            throw new IllegalArgumentException("El servicio debe estar asociado a este paciente.");
        }
        if (!servicios.contains(servicio)) servicios.add(servicio);
    }

    public List<ServicioDomiciliario> getServicios() {
        return Collections.unmodifiableList(servicios);
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = textoObligatorio(telefono, "teléfono"); }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = textoObligatorio(direccion, "dirección"); }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Notificación para el paciente " + getNombre() + ": " + textoObligatorio(mensaje, "mensaje"));
    }
}
