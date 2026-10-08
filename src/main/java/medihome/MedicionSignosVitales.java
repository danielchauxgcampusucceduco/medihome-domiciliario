package medihome;

import java.time.LocalDateTime;
import java.util.Objects;

public class MedicionSignosVitales {
    private final LocalDateTime fechaHora;
    private final double temperatura;
    private final int frecuenciaCardiaca;
    private final int presionSistolica;
    private final int presionDiastolica;
    private final double saturacionOxigeno;

    public MedicionSignosVitales(LocalDateTime fechaHora, double temperatura, int frecuenciaCardiaca,
                                 int presionSistolica, int presionDiastolica, double saturacionOxigeno) {
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha y hora son obligatorias.");
        if (temperatura < 0 || frecuenciaCardiaca <= 0 || presionSistolica <= 0
                || presionDiastolica <= 0 || saturacionOxigeno < 0 || saturacionOxigeno > 100) {
            throw new IllegalArgumentException("La medición contiene valores fuera de formato.");
        }
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionSistolica = presionSistolica;
        this.presionDiastolica = presionDiastolica;
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getTemperatura() { return temperatura; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public int getPresionSistolica() { return presionSistolica; }
    public int getPresionDiastolica() { return presionDiastolica; }
    public double getSaturacionOxigeno() { return saturacionOxigeno; }

    public String resumen() {
        return String.format("Temperatura: %.1f °C | Frecuencia cardíaca: %d lpm | Presión: %d/%d mmHg | Saturación: %.1f%%",
                temperatura, frecuenciaCardiaca, presionSistolica, presionDiastolica, saturacionOxigeno);
    }
}
