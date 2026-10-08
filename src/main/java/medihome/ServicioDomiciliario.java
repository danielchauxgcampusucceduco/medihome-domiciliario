package medihome;

import java.time.LocalDateTime;
import java.util.Objects;

public class ServicioDomiciliario {
    private final String codigo;
    private LocalDateTime fechaProgramada;
    private String direccionAtencion;
    private String motivo;
    private EstadoServicio estado = EstadoServicio.SOLICITADO;
    private final Paciente paciente;
    private ProfesionalSalud profesional;
    private AtencionMedica atencion;

    public ServicioDomiciliario(String codigo, LocalDateTime fechaProgramada, String direccionAtencion,
                                String motivo, Paciente paciente) {
        this.codigo = Usuario.textoObligatorio(codigo, "código del servicio");
        this.fechaProgramada = Objects.requireNonNull(fechaProgramada, "La fecha programada es obligatoria.");
        this.direccionAtencion = Usuario.textoObligatorio(direccionAtencion, "dirección de atención");
        this.motivo = Usuario.textoObligatorio(motivo, "motivo");
        this.paciente = Objects.requireNonNull(paciente, "El paciente es obligatorio.");
        paciente.solicitarServicio(this);
    }

    public void programar(ProfesionalSalud profesional, LocalDateTime fechaHora) {
        if (estado != EstadoServicio.SOLICITADO) {
            throw new IllegalStateException("Solo se puede programar un servicio solicitado.");
        }
        Objects.requireNonNull(profesional, "El profesional asignado es obligatorio.");
        Objects.requireNonNull(fechaHora, "La fecha programada es obligatoria.");
        if (!profesional.estaDisponible(fechaHora)) {
            throw new IllegalStateException("El profesional ya tiene un servicio en esa fecha y hora.");
        }
        this.profesional = profesional;
        this.fechaProgramada = fechaHora;
        profesional.agregarServicio(this);
        this.estado = EstadoServicio.PROGRAMADO;
    }

    public AtencionMedica iniciarAtencion(LocalDateTime fechaHoraInicio) {
        if (estado != EstadoServicio.PROGRAMADO || profesional == null) {
            throw new IllegalStateException("El servicio debe estar programado y tener un profesional asignado.");
        }
        atencion = new AtencionMedica(this, profesional, fechaHoraInicio);
        estado = EstadoServicio.EN_ATENCION;
        return atencion;
    }

    public void finalizar(LocalDateTime fechaHoraFin, String observaciones, String recomendaciones) {
        if (estado != EstadoServicio.EN_ATENCION || atencion == null) {
            throw new IllegalStateException("Solo se puede finalizar un servicio que está en atención.");
        }
        atencion.cerrar(fechaHoraFin, observaciones, recomendaciones);
        estado = EstadoServicio.FINALIZADO;
    }

    public void cancelar() {
        if (estado != EstadoServicio.SOLICITADO && estado != EstadoServicio.PROGRAMADO) {
            throw new IllegalStateException("Solo se puede cancelar un servicio solicitado o programado.");
        }
        if (profesional != null) profesional.retirarServicio(this);
        estado = EstadoServicio.CANCELADO;
    }

    public String getCodigo() { return codigo; }
    public LocalDateTime getFechaProgramada() { return fechaProgramada; }
    public String getDireccionAtencion() { return direccionAtencion; }
    public void setDireccionAtencion(String direccionAtencion) {
        this.direccionAtencion = Usuario.textoObligatorio(direccionAtencion, "dirección de atención");
    }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = Usuario.textoObligatorio(motivo, "motivo"); }
    public EstadoServicio getEstado() { return estado; }
    public Paciente getPaciente() { return paciente; }
    public ProfesionalSalud getProfesional() { return profesional; }
    public AtencionMedica getAtencion() { return atencion; }
}
