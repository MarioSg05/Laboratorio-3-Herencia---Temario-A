import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static Enums.EstadoCita;

/**
 * Aplica el principio de Liskov y Polimorfismo en los reportes.
 */
public class SistemaGestionHospitalaria {
    private final List<TrabajadorMedico> personal;
    private final List<CitaMedica> citas;

    public SistemaGestionHospitalaria() {
        this.personal = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    // I. GESTIÓN DE PERSONAL MÉDICO
    /**
     * Contrata a un nuevo trabajador.
     * @param trabajador El objeto TrabajadorMedico (polimórfico).
     */
    public void contratarPersonal(TrabajadorMedico trabajador) {
        personal.add(trabajador);
        System.out.println("✅ Contratación Exitosa: " + trabajador.getNombreCompleto() + " (" + trabajador.getClass().getSimpleName() + ")");
    }

    // V. OPERACIONES DEL MANAGER DEL HOSPITAL - Asignación Inteligente
    /**
     * Encuentra personal disponible según especialización, horario y tipo de cita.
     * Simulación: Encuentra el médico con menos citas programadas que maneje el tipo de cita.
     */
    public TrabajadorMedico encontrarPersonalDisponible(CitaMedica nuevaCita) {
        return personal.stream()
            .filter(t -> esEspecialistaApropiado(t, nuevaCita.getTipoCita()))
            .min(Comparator.comparingInt(TrabajadorMedico::getCitasAsignadas))
            .orElse(null);
    }

    // Lógica auxiliar para filtrar por especialidad
    private boolean esEspecialistaApropiado(TrabajadorMedico t, Enums.TipoCita tipo) {
        String clase = t.getClass().getSimpleName();
        return switch (tipo) {
            case CONSULTA_GENERAL -> clase.equals("DoctorGeneral");
            case CIRUGIA -> clase.equals("Cirujano");
            case TERAPIA -> clase.equals("Enfermero"); // Simulación: Terapia manejada por Enfermero certificado
            case DIAGNOSTICO -> clase.equals("Radiologo");
            default -> true; // Para citas administrativas, cualquier trabajador sirve
        };
    }

    /**
     * Agendar una nueva cita.
     * @param cita La cita a agendar.
     */
    public void agendarCita(CitaMedica cita) {
        TrabajadorMedico medico = cita.getMedicoAsignado();
        if (verificarDisponibilidad(medico, cita.getFechaHora())) {
            citas.add(cita);
            medico.incrementarCitasAsignadas();
            System.out.println("✅ Cita agendada: " + cita.getIdCita() + " con " + medico.getNombreCompleto() + " el " + cita.getFechaHora().toString());
        } else {
            System.err.println("❌ ERROR: Conflicto de horario. Reagendamiento necesario para " + cita.getNombrePaciente());
            // Intenta una resolución simple (reagendar para 1 hora después)
            resolverConflicto(cita, LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS));
        }
    }

    // IV. SISTEMA DE REAGENDAMIENTO INTELIGENTE
    /**
     * Verifica la disponibilidad del personal en una fecha y hora específicas.
     */
    private boolean verificarDisponibilidad(TrabajadorMedico medico, LocalDateTime fechaHora) {
        // En un sistema real, se verificaría contra una agenda más compleja.
        // Aquí simulamos que si hay otra cita PROGRAMADA o CONFIRMADA en la misma hora, hay conflicto.
        return citas.stream()
            .filter(c -> c.getMedicoAsignado().getIdEmpleado().equals(medico.getIdEmpleado()))
            .filter(c -> c.getEstadoActual() == EstadoCita.PROGRAMADA || c.getEstadoActual() == EstadoCita.CONFIRMADA)
            .noneMatch(c -> c.getFechaHora().truncatedTo(ChronoUnit.HOURS).isEqual(fechaHora.truncatedTo(ChronoUnit.HOURS)));
    }
    
    // V. GESTIÓN DE CONFLICTOS / IV. SISTEMA DE REAGENDAMIENTO
    /**
     * Resuelve choques de horarios automáticamente buscando el próximo espacio libre.
     * @param citaEnConflicto Cita que necesita ser movida.
     * @param fechaSugerida Punto de partida para buscar un nuevo horario.
     */
    public void resolverConflicto(CitaMedica citaEnConflicto, LocalDateTime fechaSugerida) {
        LocalDateTime nuevoHorario = fechaSugerida.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        TrabajadorMedico medico = citaEnConflicto.getMedicoAsignado();
        
        // Simulación de búsqueda del próximo espacio libre (máximo 24 intentos)
        int intentos = 0;
        while (!verificarDisponibilidad(medico, nuevoHorario) && intentos < 24) {
            nuevoHorario = nuevoHorario.plusHours(1);
            intentos++;
        }

        if (intentos < 24) {
            // Reagenda la cita y notifica (simulado por consola)
            citaEnConflicto.reagendar(nuevoHorario, "Conflicto con cita existente o emergencia.");
            System.out.println("🤖 NOTIFICACIÓN (Simulación): Cita " + citaEnConflicto.getIdCita() + 
                               " de " + citaEnConflicto.getNombrePaciente() + " REAGENDADA.");
            System.out.println("   -> Nuevo Horario Asignado: " + nuevoHorario.toString());
        } else {
            // Si no encuentra espacio en 24 horas, la cancela
            citaEnConflicto.setEstadoActual(EstadoCita.CANCELADA);
            System.err.println("❌ Alerta de Crisis: No se pudo reagendar " + citaEnConflicto.getIdCita() + 
                               ". Cita Cancelada por falta de disponibilidad.");
        }
    }

    // V. OPERACIONES DEL MANAGER - Reportes de Nómina (Polimorfismo)
    /**
     * Calcula la nómina total por departamento, usando polimorfismo.
     */
    public void reporteNominaPorDepartamento() {
        System.out.println("\n--- REPORTE DE NÓMINA POR DEPARTAMENTO (Polimorfismo) ---");
        personal.stream()
            .collect(Collectors.groupingBy(TrabajadorMedico::getDepartamentoAsignado,
                     Collectors.summingDouble(TrabajadorMedico::calcularSalario)))
            .forEach((depto, total) -> System.out.printf("  - %s: $%,.2f\n", depto, total));
    }

    // V. OPERACIONES DEL MANAGER - Reporte de Personal (Polimorfismo)
    /**
     * Muestra la información detallada de todo el personal (usa toString polimórfico).
     */
    public void reportePersonalCompleto() {
        System.out.println("\n--- REPORTE COMPLETO DE PERSONAL ---");
        personal.forEach(t -> System.out.println("  " + t)); // Uso del toString() polimórfico
    }

    // V. OPERACIONES DEL MANAGER - Reporte de Citas
    /**
     * Lista citas por estado o trabajador asignado.
     */
    public void reporteCitas(EstadoCita estadoFiltro) {
        System.out.println("\n--- REPORTE DE CITAS: " + estadoFiltro.name() + " ---");
        List<CitaMedica> citasFiltradas = citas.stream()
            .filter(c -> c.getEstadoActual() == estadoFiltro)
            .sorted(Comparator.comparing(CitaMedica::getFechaHora))
            .collect(Collectors.toList());

        if (citasFiltradas.isEmpty()) {
            System.out.println("  * No hay citas en estado " + estadoFiltro.name());
        } else {
            citasFiltradas.forEach(c -> System.out.println("  " + c));
        }
    }

    // V. OPERACIONES DEL MANAGER - Historial de Reagendamientos
    /**
     * Muestra el historial de todas las citas que han sido reagendadas.
     */
    public void historialReagendamientos() {
        System.out.println("\n--- HISTORIAL DE REAGENDAMIENTOS ---");
        citas.stream()
            .filter(c -> c.getEstadoActual() == EstadoCita.REAGENDADA)
            .forEach(c -> {
                System.out.println("Cita ID: " + c.getIdCita() + " | Paciente: " + c.getNombrePaciente());
                System.out.println("  Historial:");
                System.out.println(c.getHistorialReagendamiento().trim());
            });
    }

    // Getters
    public List<TrabajadorMedico> getPersonal() { return personal; }
    public List<CitaMedica> getCitas() { return citas; }
}
