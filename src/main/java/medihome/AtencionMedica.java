package medihome;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AtencionMedica {
    private final ServicioDomiciliario servicio;
    private final ProfesionalSalud profesional;
    private final LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String observaciones;
    private String recomendaciones;
    private final List<MedicionSignosVitales> mediciones = new ArrayList<>();

    AtencionMedica(ServicioDomiciliario servicio, ProfesionalSalud profesional, LocalDateTime fechaHoraInicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio.");
        this.profesional = Objects.requireNonNull(profesional, "El profesional es obligatorio.");
        this.fechaHoraInicio = Objects.requireNonNull(fechaHoraInicio, "La fecha de inicio es obligatoria.");
    }

    public void registrarMedicion(MedicionSignosVitales medicion) {
        Objects.requireNonNull(medicion, "La medición es obligatoria.");
        if (fechaHoraFin != null) throw new IllegalStateException("La atención ya está cerrada.");
        mediciones.add(medicion);
    }

    void cerrar(LocalDateTime fechaHoraFin, String observaciones, String recomendaciones) {
        Objects.requireNonNull(fechaHoraFin, "La fecha de finalización es obligatoria.");
        if (!fechaHoraFin.isAfter(fechaHoraInicio)) {
            throw new IllegalArgumentException("La finalización debe ser posterior al inicio.");
        }
        String observacionesValidadas = Usuario.textoObligatorio(observaciones, "observaciones clínicas");
        String recomendacionesValidadas = Usuario.textoObligatorio(recomendaciones, "recomendaciones");
        this.fechaHoraFin = fechaHoraFin;
        this.observaciones = observacionesValidadas;
        this.recomendaciones = recomendacionesValidadas;
    }

    public ServicioDomiciliario getServicio() { return servicio; }
    public ProfesionalSalud getProfesional() { return profesional; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public String getObservaciones() { return observaciones; }
    public String getRecomendaciones() { return recomendaciones; }
    public List<MedicionSignosVitales> getMediciones() { return Collections.unmodifiableList(mediciones); }
}
