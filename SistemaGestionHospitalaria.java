import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * Responsable de la gestión de personal, citas, nómina y reagendamiento.
 */
public class SistemaGestionHospitalaria {
    private List<TrabajadorMedico> personal;
    private List<CitaMedica> citas;
    
    public SistemaGestionHospitalaria() {
        this.personal = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    // --- MÉTODOS DE GESTIÓN DE PERSONAL ---
    
    /**
     * Contrata un nuevo trabajador médico.
     * @param trabajador El objeto TrabajadorMedico a añadir.
     */
    public void contratarPersonal(TrabajadorMedico trabajador) {
        personal.add(trabajador);
        System.out.println("✅ Contratado: " + trabajador.getNombreCompleto() + " (" + trabajador.getClass().getSimpleName() + ")");
    }

    // --- MÉTODOS DE GESTIÓN DE CITAS ---

    /**
     * Sistema de Reagendamiento Inteligente.
     * 1. Verifica disponibilidad
     * 2. Detecta conflictos
     * 3. Mantiene historial
     * 4. Actualiza estados
     * @param cita Cita a reagendar.
     * @param nuevaFechaHora Nueva fecha y hora propuesta.
     * @param motivo Motivo del reagendamiento.
     * @return true si se pudo reagendar.
     */
    public boolean reagendarCitaInteligente(CitaMedica cita, LocalDateTime nuevaFechaHora, String motivo) {
        TrabajadorMedico medico = cita.getMedicoAsignado();
        
        // 1. Verificar disponibilidad (simulación simple de conflicto)
        if (detectarConflicto(medico, nuevaFechaHora)) {
            System.out.println("❌ Conflicto: El médico " + medico.getNombreCompleto() + " ya tiene una cita agendada a esa hora.");
            return false;
        }
        
        // 4. Actualiza estado y 3. Mantiene historial
        if (cita.reagendar(nuevaFechaHora, motivo)) {
            System.out.println("🔔 Notificación: Cita " + cita.getIdCita() + " de " + cita.getNombrePaciente() + 
                               " con Dr(a). " + medico.getNombreCompleto() + 
                               " ha sido REAGENDADA a " + nuevaFechaHora + ". Motivo: " + motivo);
            return true;
        }
        return false;
    }

    /**
     * Gestión de Conflictos: Detecta si un médico tiene una cita en el mismo bloque de tiempo (simplificado a la misma hora).
     */
    private boolean detectarConflicto(TrabajadorMedico medico, LocalDateTime fechaHora) {
        for (CitaMedica c : citas) {
            // Verifica que no sea la misma cita y que el médico sea el mismo
            if (!c.getFechaHora().equals(fechaHora) && c.getMedicoAsignado().equals(medico)) {
                // Chequeo de solapamiento de 1 hora
                if (c.getFechaHora().isBefore(fechaHora.plusMinutes(59)) && 
                    c.getFechaHora().isAfter(fechaHora.minusMinutes(59)) &&
                    c.getEstadoActual() != Enums.EstadoCita.CANCELADA && c.getEstadoActual() != Enums.EstadoCita.COMPLETADA) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Asignación Inteligente: Encuentra personal disponible según especialización y horario.
     * @param tipo Tipo de cita requerida.
     * @param fechaHora Hora de la cita.
     * @return El TrabajadorMedico disponible con menos citas asignadas, o null si no hay.
     */
    public TrabajadorMedico asignacionInteligente(Enums.TipoCita tipo, LocalDateTime fechaHora) {
        List<TrabajadorMedico> candidatos = new ArrayList<>();
        Class<?> claseRequerida;

        // Determinar la clase de médico necesaria
        if (tipo == Enums.TipoCita.CONSULTA_GENERAL) {
            claseRequerida = DoctorGeneral.class;
        } else if (tipo == Enums.TipoCita.CIRUGIA) {
            claseRequerida = Cirujano.class;
        } else if (tipo == Enums.TipoCita.TERAPIA) {
            claseRequerida = Enfermero.class; 
        } else if (tipo == Enums.TipoCita.DIAGNOSTICO) {
            claseRequerida = Radiologo.class;
        } else {
            return null; 
        }

        // 1. Filtrar por especialización y disponibilidad
        for (TrabajadorMedico tm : personal) {
            if (claseRequerida.isInstance(tm) && !detectarConflicto(tm, fechaHora)) {
                candidatos.add(tm);
            }
        }
        
        if (candidatos.isEmpty()) {
            return null;
        }
        
        // 2. Ordenar por menos carga de trabajo (menos citas asignadas)
        candidatos.sort(Comparator.comparingInt(TrabajadorMedico::getCitasAsignadas));

        return candidatos.get(0); // Devuelve el más disponible
    }

    /**
     * Programa una cita usando asignación inteligente.
     */
    public CitaMedica programarCita(String paciente, Enums.TipoCita tipo, LocalDateTime fechaHora) {
        TrabajadorMedico medico = asignacionInteligente(tipo, fechaHora);
        
        if (medico != null) {
            CitaMedica nuevaCita = new CitaMedica(paciente, medico, fechaHora, tipo);
            citas.add(nuevaCita);
            System.out.println("✅ Cita Creada: " + nuevaCita.getIdCita() + " asignada a Dr(a). " + medico.getNombreCompleto());
            return nuevaCita;
        } else {
            System.out.println("❌ No se encontró personal disponible para una cita de " + tipo + " a las " + fechaHora);
            return null;
        }
    }
    
    // --- MÉTODOS DE REPORTE Y ANÁLISIS ---

    /**
     * [V.2] Reporte de Nómina: Calcula salarios por departamento usando polimorfismo.
     * [V.7] Análisis Financiero: Calcular nómina total por departamento.
     */
    public void reporteNominaMensual() {
        System.out.println("\n===== REPORTE DE NÓMINA MENSUAL =====");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        personal.stream()
            .collect(Collectors.groupingBy(TrabajadorMedico::getDepartamentoAsignado))
            .forEach((departamento, trabajadores) -> {
                System.out.println("\n--- Departamento: " + departamento + " ---");
                double nominaDepartamento = 0;
                for (TrabajadorMedico tm : trabajadores) {
                    double salario = tm.calcularSalario(); // 🌟 POLIMORFISMO en acción
                    nominaDepartamento += salario;
                    System.out.println("  " + tm.getNombreCompleto() + 
                                       " (" + tm.getClass().getSimpleName() + 
                                       "): $" + df.format(salario));
                }
                System.out.println("NOMINA TOTAL " + departamento + ": $" + df.format(nominaDepartamento));
            });
        System.out.println("=====================================");
    }

    /**
     * [V.5] Reporte de Personal: Muestra información de todos los trabajadores usando polimorfismo (toString).
     */
    public void reportePersonal() {
        System.out.println("\n===== REPORTE DE PERSONAL MÉDICO =====");
        personal.stream().forEach(tm -> System.out.println(tm)); // Polimorfismo con toString()
        System.out.println("======================================");
    }
    
    /**
     * Reporte de Citas: Listar citas por estado y trabajador asignado.
     */
    public void reporteCitasPorEstado() {
        System.out.println("\n===== REPORTE DE CITAS POR ESTADO =====");
        citas.stream()
            .collect(Collectors.groupingBy(CitaMedica::getEstadoActual))
            .forEach((estado, listaCitas) -> {
                System.out.println("\n--- Estado: " + estado + " (" + listaCitas.size() + " citas) ---");
                listaCitas.forEach(cita -> System.out.println("  " + cita.getIdCita() + " | Paciente: " + cita.getNombrePaciente() + " | Médico: " + cita.getMedicoAsignado().getNombreCompleto()));
            });
        System.out.println("=======================================");
    }
    
    /**
     * Muestra todas las modificaciones de citas.
     */
    public void historialReagendamientos() {
        System.out.println("\n===== HISTORIAL DE REAGENDAMIENTOS =====");
        citas.stream()
            .filter(c -> c.getEstadoActual() == Enums.EstadoCita.REAGENDADA)
            .forEach(c -> {
                System.out.println("\nCita " + c.getIdCita() + " (" + c.getNombrePaciente() + "):");
                System.out.println(c.getHistorialReagendamiento());
            });

        if (citas.stream().noneMatch(c -> c.getEstadoActual() == Enums.EstadoCita.REAGENDADA)) {
            System.out.println("No hay citas reagendadas.");
        }
        System.out.println("==========================================");
    }

    // Getters
    public List<TrabajadorMedico> getPersonal() { return personal; }
    public List<CitaMedica> getCitas() { return citas; }
    
    // Método auxiliar para obtener una cita por ID
    public CitaMedica getCitaPorId(String id) {
        return citas.stream()
            .filter(c -> c.getIdCita().equals(id))
            .findFirst()
            .orElse(null);
    }
}
