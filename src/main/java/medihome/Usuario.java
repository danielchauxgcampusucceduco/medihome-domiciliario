package medihome;

import java.util.Objects;

public abstract class Usuario {
    private final String identificacion;
    private String nombre;
    private String correo;

    protected Usuario(String identificacion, String nombre, String correo) {
        this.identificacion = textoObligatorio(identificacion, "identificación");
        this.nombre = textoObligatorio(nombre, "nombre");
        this.correo = textoObligatorio(correo, "correo");
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = textoObligatorio(nombre, "nombre"); }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = textoObligatorio(correo, "correo"); }

    public abstract void notificar(String mensaje);

    protected static String textoObligatorio(String valor, String campo) {
        Objects.requireNonNull(valor, "El campo " + campo + " es obligatorio.");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío.");
        }
        return valor.trim();
    }
}
