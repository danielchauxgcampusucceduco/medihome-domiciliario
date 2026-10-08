package medihome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipoAtencion {
    private final String codigo;
    private String nombre;
    private String zonaCobertura;
    private final List<ProfesionalSalud> profesionales = new ArrayList<>();

    public EquipoAtencion(String codigo, String nombre, String zonaCobertura) {
        this.codigo = Usuario.textoObligatorio(codigo, "código del equipo");
        setNombre(nombre);
        setZonaCobertura(zonaCobertura);
    }

    public void agregarProfesional(ProfesionalSalud profesional) {
        if (profesional == null) throw new IllegalArgumentException("El profesional es obligatorio.");
        if (profesional.getEquipoActual() == this) return;
        EquipoAtencion equipoAnterior = profesional.getEquipoActual();
        if (equipoAnterior != null) equipoAnterior.retirarProfesional(profesional);
        profesionales.add(profesional);
        profesional.asignarEquipoActual(this);
    }

    public void retirarProfesional(ProfesionalSalud profesional) {
        if (profesional != null && profesionales.remove(profesional)
                && profesional.getEquipoActual() == this) {
            profesional.asignarEquipoActual(null);
        }
    }

    public List<ProfesionalSalud> getProfesionales() {
        return Collections.unmodifiableList(profesionales);
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Usuario.textoObligatorio(nombre, "nombre del equipo"); }
    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = Usuario.textoObligatorio(zonaCobertura, "zona de cobertura");
    }
}
