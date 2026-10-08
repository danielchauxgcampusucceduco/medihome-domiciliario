package medihome;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfesionalSalud extends Usuario {
    private String numeroRegistroProfesional;
    private String especialidad;
    private EquipoAtencion equipoActual;
    private final List<ServicioDomiciliario> serviciosAsignados = new ArrayList<>();

    public ProfesionalSalud(String identificacion, String nombre, String correo,
                            String numeroRegistroProfesional, String especialidad) {
        super(identificacion, nombre, correo);
        setNumeroRegistroProfesional(numeroRegistroProfesional);
        setEspecialidad(especialidad);
    }

    public AtencionMedica registrarAtencion(ServicioDomiciliario servicio, LocalDateTime inicio) {
        if (servicio == null || servicio.getProfesional() != this) {
            throw new IllegalArgumentException("El profesional debe estar asignado al servicio.");
        }
        return servicio.iniciarAtencion(inicio);
    }

    public boolean estaDisponible(LocalDateTime fechaHora) {
        return serviciosAsignados.stream().noneMatch(servicio ->
                servicio.getEstado() != EstadoServicio.CANCELADO
                        && fechaHora.equals(servicio.getFechaProgramada()));
    }

    void agregarServicio(ServicioDomiciliario servicio) {
        if (!serviciosAsignados.contains(servicio)) serviciosAsignados.add(servicio);
    }

    void retirarServicio(ServicioDomiciliario servicio) { serviciosAsignados.remove(servicio); }

    void asignarEquipoActual(EquipoAtencion equipo) { this.equipoActual = equipo; }

    public List<ServicioDomiciliario> getServiciosAsignados() {
        return Collections.unmodifiableList(serviciosAsignados);
    }

    public String getNumeroRegistroProfesional() { return numeroRegistroProfesional; }
    public void setNumeroRegistroProfesional(String numeroRegistroProfesional) {
        this.numeroRegistroProfesional = textoObligatorio(numeroRegistroProfesional, "registro profesional");
    }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = textoObligatorio(especialidad, "especialidad"); }
    public EquipoAtencion getEquipoActual() { return equipoActual; }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Notificación para el profesional " + getNombre() + ": " + textoObligatorio(mensaje, "mensaje"));
    }
}
