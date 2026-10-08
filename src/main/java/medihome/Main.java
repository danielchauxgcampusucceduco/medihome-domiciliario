package medihome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        Empresa medihome = new Empresa("NIT-900123456", "MediHome", "contacto@medihome.test",
                "6015550101", "Carrera 10 #20-30, Bogotá");
        Paciente paciente = new Paciente("CC-100200", "Laura Gómez", "laura@example.test",
                "3005550101", "Calle 12 #34-56, Bogotá");
        ProfesionalSalud profesional = new ProfesionalSalud("CC-200300", "Andrea Ruiz",
                "andrea@example.test", "TP-12345", "Medicina general");
        EquipoAtencion equipo = new EquipoAtencion("EQ-01", "Equipo Norte", "Zona norte");
        equipo.agregarProfesional(profesional);
        medihome.registrarProfesional(profesional);
        medihome.registrarEquipo(equipo);

        LocalDateTime horaProgramada = LocalDateTime.now().withSecond(0).withNano(0);
        ServicioDomiciliario servicio = new ServicioDomiciliario("SRV-0001", horaProgramada,
                paciente.getDireccion(), "Consulta médica general", paciente);
        medihome.registrarServicio(servicio);
        servicio.programar(profesional, horaProgramada);

        LocalDateTime inicio = horaProgramada.plusMinutes(5);
        AtencionMedica atencion = profesional.registrarAtencion(servicio, inicio);
        MedicionSignosVitales medicion = new MedicionSignosVitales(inicio.plusMinutes(10),
                36.7, 78, 120, 80, 98.0);
        atencion.registrarMedicion(medicion);
        servicio.finalizar(inicio.plusMinutes(40),
                "Atención domiciliaria realizada. Paciente consciente y colaboradora.",
                "Mantener hidratación y seguir las indicaciones entregadas por el profesional.");

        imprimirReporte(medihome, paciente, profesional, servicio, atencion);
        paciente.notificar("El reporte del servicio " + servicio.getCodigo() + " está disponible.");
    }

    private static void imprimirReporte(Empresa empresa, Paciente paciente,
                                        ProfesionalSalud profesional, ServicioDomiciliario servicio,
                                        AtencionMedica atencion) {
        System.out.println("============================================================");
        System.out.println("       REPORTE DE ATENCIÓN DOMICILIARIA - " + empresa.getNombre());
        System.out.println("============================================================");
        System.out.println("Servicio:       " + servicio.getCodigo());
        System.out.println("Estado:         " + servicio.getEstado());
        System.out.println("Programado:     " + FORMATO.format(servicio.getFechaProgramada()));
        System.out.println("Dirección:      " + servicio.getDireccionAtencion());
        System.out.println("Motivo:         " + servicio.getMotivo());
        System.out.println("Paciente:       " + paciente.getNombre() + " (" + paciente.getIdentificacion() + ")");
        System.out.println("Profesional:    " + profesional.getNombre() + " — " + profesional.getEspecialidad());
        System.out.println("Registro prof.: " + profesional.getNumeroRegistroProfesional());
        System.out.println("Inicio:         " + FORMATO.format(atencion.getFechaHoraInicio()));
        System.out.println("Finalización:   " + FORMATO.format(atencion.getFechaHoraFin()));
        System.out.println("Observaciones:  " + atencion.getObservaciones());
        System.out.println("Recomendaciones:" + System.lineSeparator() + "  " + atencion.getRecomendaciones());
        System.out.println("Mediciones:");
        atencion.getMediciones().forEach(item -> System.out.println("  - "
                + FORMATO.format(item.getFechaHora()) + " | " + item.resumen()));
        System.out.println("============================================================");
    }
}
