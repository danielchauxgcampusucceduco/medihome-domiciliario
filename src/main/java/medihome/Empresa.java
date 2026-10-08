package medihome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Empresa {
    private final String identificacion;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccionPrincipal;
    private final List<ProfesionalSalud> profesionales = new ArrayList<>();
    private final List<EquipoAtencion> equipos = new ArrayList<>();
    private final List<ServicioDomiciliario> servicios = new ArrayList<>();

    public Empresa(String identificacion, String nombre, String correo, String telefono, String direccionPrincipal) {
        this.identificacion = Usuario.textoObligatorio(identificacion, "identificación de la empresa");
        setNombre(nombre);
        setCorreo(correo);
        setTelefono(telefono);
        setDireccionPrincipal(direccionPrincipal);
    }

    public void registrarProfesional(ProfesionalSalud profesional) {
        if (profesional == null) throw new IllegalArgumentException("El profesional es obligatorio.");
        if (!profesionales.contains(profesional)) profesionales.add(profesional);
    }

    public void registrarEquipo(EquipoAtencion equipo) {
        if (equipo == null) throw new IllegalArgumentException("El equipo es obligatorio.");
        if (equipos.stream().anyMatch(actual -> actual.getCodigo().equals(equipo.getCodigo()))) {
            throw new IllegalArgumentException("Ya existe un equipo con ese código.");
        }
        equipos.add(equipo);
    }

    public void registrarServicio(ServicioDomiciliario servicio) {
        if (servicio == null) throw new IllegalArgumentException("El servicio es obligatorio.");
        if (servicios.stream().anyMatch(actual -> actual.getCodigo().equals(servicio.getCodigo()))) {
            throw new IllegalArgumentException("Ya existe un servicio con ese código.");
        }
        servicios.add(servicio);
    }

    public List<ProfesionalSalud> getProfesionales() { return Collections.unmodifiableList(profesionales); }
    public List<EquipoAtencion> getEquipos() { return Collections.unmodifiableList(equipos); }
    public List<ServicioDomiciliario> getServicios() { return Collections.unmodifiableList(servicios); }
    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Usuario.textoObligatorio(nombre, "nombre de la empresa"); }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = Usuario.textoObligatorio(correo, "correo de la empresa"); }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = Usuario.textoObligatorio(telefono, "teléfono de la empresa"); }
    public String getDireccionPrincipal() { return direccionPrincipal; }
    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = Usuario.textoObligatorio(direccionPrincipal, "dirección principal");
    }
}
