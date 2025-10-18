/**
 * NOTA IMPORTANTE SOBRE EL DISEÑO DEL MAIN: SIMULACIÓN NO INTERACTIVA
 * * Este archivo (MainHospital.java) se ha diseñado como un "Demostrador de Simulación"
 * y no como una aplicación interactiva con entrada de usuario (Input).
 * * OBJETIVO:
 * El principal objetivo de este diseño es **probar y demostrar** la correcta 
 * implementación de la jerarquía de clases y los conceptos de POO solicitados (Polimorfismo,
 * Encapsulamiento, Herencia, etc.) de manera automatizada. */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Implementa los escenarios de prueba sugeridos.
 */
public class MainHospital {
    
    // --- ESCENARIOS DE PRUEBA ---
    
    /**
     * 1. Contratación Masiva: Crea 15+ trabajadores médicos de diferentes tipos.
     */
    public static List<TrabajadorMedico> escenarioContratacion(SistemaGestionHospitalaria sistema) {
        System.out.println("===================================================================");
        System.out.println("ESCENARIO 1: CONTRATACIÓN MASIVA");
        System.out.println("===================================================================");
        
        List<TrabajadorMedico> nuevoPersonal = new ArrayList<>();
        
        // 5 Doctores Generales
        nuevoPersonal.add(new DoctorGeneral("Dr. Pedro Alva", "Medicina General", 5, 5000.00, "Medicina Familiar", 15, 150.00));
        nuevoPersonal.add(new DoctorGeneral("Dra. Ana Cruz", "Medicina General", 10, 6000.00, "Pediatría", 12, 180.00));
        nuevoPersonal.add(new DoctorGeneral("Dr. Luis Rojas", "Cardiología", 8, 7000.00, "Cardiología", 10, 200.00));
        
        // 3 Cirujanos
        nuevoPersonal.add(new Cirujano("Dr. Manuel Lopez", "Cirugía", 15, 8500.00, "General, Laparoscópica", 8.0, 500.00));
        nuevoPersonal.add(new Cirujano("Dra. Carla Soto", "Cirugía", 12, 9000.00, "Neurocirugía", 6.0, 800.00));
        
        // 5 Enfermeros
        nuevoPersonal.add(new Enfermero("E. María Pérez", "Enfermería", 3, 3000.00, Enums.TipoTurno.MATUTINO, "Nivel III"));
        nuevoPersonal.add(new Enfermero("E. Juan Gomez", "Enfermería", 7, 3500.00, Enums.TipoTurno.NOCTURNO, "Nivel IV (UCI)"));
        nuevoPersonal.add(new Enfermero("E. Sofia Mora", "Enfermería", 2, 3000.00, Enums.TipoTurno.NOCTURNO, "Nivel II"));
        
        // 3 Radiólogos 
        nuevoPersonal.add(new Radiologo("R. Elena Díaz", "Diagnóstico", 6, 4500.00, "MRI, CT-Scan", 100.00));
        nuevoPersonal.add(new Radiologo("R. Carlos Vega", "Diagnóstico", 4, 4000.00, "Rayos X, Ultrasonido", 80.00));
        
        // Contratación
        for (TrabajadorMedico tm : nuevoPersonal) {
            sistema.contratarPersonal(tm);
        }
        
        return nuevoPersonal;
    }
    
    /**
     * 2. Agenda Saturada: Programa 20 citas en un día.
     */
    public static List<CitaMedica> escenarioAgendaSaturada(SistemaGestionHospitalaria sistema) {
        System.out.println("\n===================================================================");
        System.out.println("ESCENARIO 2: AGENDA SATURADA (Programando 20 citas)");
        System.out.println("===================================================================");
        
        LocalDateTime horaInicio = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
        List<CitaMedica> citasAgendadas = new ArrayList<>();
        Random rand = new Random();
        
        Enums.TipoCita[] tiposCita = Enums.TipoCita.values();
        
        for (int i = 0; i < 20; i++) {
            String paciente = "Paciente_" + (i + 1);
            Enums.TipoCita tipo = tiposCita[rand.nextInt(tiposCita.length - 1)]; // Evitar ADMINISTRATIVA
            LocalDateTime horaCita = horaInicio.plusMinutes(i * 30); // Citas cada 30 minutos

            CitaMedica cita = sistema.programarCita(paciente, tipo, horaCita);
            if (cita != null) {
                citasAgendadas.add(cita);
            }
            
            // Simular un procedimiento para el cálculo de nómina (solo Doctores y Radiólogos)
            if (cita != null && cita.getMedicoAsignado() instanceof DoctorGeneral) {
                ((DoctorGeneral)cita.getMedicoAsignado()).incrementarConsultas(1);
            } else if (cita != null && cita.getMedicoAsignado() instanceof Radiologo) {
                ((Radiologo)cita.getMedicoAsignado()).incrementarEstudios(1);
            }
        }
        
        return citasAgendadas;
    }

    /**
     * 3 & 5. Crisis de Personal y Emergencia Médica: Simular ausencia y reagendar citas.
     */
    public static void escenarioCrisisYEmergencia(SistemaGestionHospitalaria sistema, List<TrabajadorMedico> personal, List<CitaMedica> citas) {
        System.out.println("\n===================================================================");
        System.out.println("ESCENARIO 3 & 5: CRISIS DE PERSONAL Y EMERGENCIA MÉDICA");
        System.out.println("===================================================================");

        // --- 3. Crisis de Personal: Simular ausencia de un Doctor ---
        TrabajadorMedico doctorAusente = personal.stream()
            .filter(t -> t instanceof DoctorGeneral)
            .findFirst().orElse(null);
            
        if (doctorAusente != null) {
            System.out.println("🚨 Dr(a). " + doctorAusente.getNombreCompleto() + " reporta AUSENCIA INESPERADA.");
            
            // Encontrar 5 citas asignadas a este doctor
            List<CitaMedica> citasAfectadas = citas.stream()
                .filter(c -> c.getMedicoAsignado().equals(doctorAusente) && c.getEstadoActual() == Enums.EstadoCita.PROGRAMADA)
                .limit(5)
                .collect(Collectors.toList());
                
            System.out.println("Se deben REAGENDAR " + citasAfectadas.size() + " citas afectadas.");
            
            LocalDateTime nuevaHoraBase = LocalDateTime.now().plusDays(2).withHour(16).withMinute(0);
            int i = 0;
            for (CitaMedica cita : citasAfectadas) {
                LocalDateTime nuevaHora = nuevaHoraBase.plusMinutes(i++ * 30);
                // Intenta reagendar la cita
                sistema.reagendarCitaInteligente(cita, nuevaHora, "Ausencia del médico asignado por emergencia personal.");
            }
        } else {
            System.out.println("No hay suficientes doctores para simular la ausencia.");
        }
        
        // --- 5. Emergencia Médica: Acomodar Cirugía Urgente ---
        System.out.println("\n--- EMERGENCIA: CIRUGÍA URGENTE ---");
        
        // Simular una cirugía de emergencia que toma 3 horas
        LocalDateTime horaEmergencia = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
        
        // Necesitamos un cirujano para la emergencia
        TrabajadorMedico cirujano = personal.stream()
            .filter(t -> t instanceof Cirujano)
            .findFirst().orElse(null);

        if (cirujano != null) {
            // Se asume que la emergencia ya está "programada" con el cirujano, 
            // pero podría chocar con citas existentes.
            System.out.println("Cirugía Urgente asignada al Dr(a). " + cirujano.getNombreCompleto() + " a las " + horaEmergencia);
            ((Cirujano)cirujano).incrementarHorasCirugia(3.0);
            
            // Buscar citas del cirujano que choquen con el bloque de 3 horas (1hr, 2hr, 3hr después)
            for (int i = 0; i < 3; i++) {
                LocalDateTime horaChoque = horaEmergencia.plusHours(i);
                
                citas.stream()
                    .filter(c -> c.getMedicoAsignado().equals(cirujano) && 
                                 c.getFechaHora().withMinute(0).equals(horaChoque.withMinute(0)) && 
                                 c.getEstadoActual() == Enums.EstadoCita.PROGRAMADA)
                    .forEach(citaChocada -> {
                        System.out.println("  Conflicto detectado en " + horaChoque + ": Cita " + citaChocada.getIdCita());
                        LocalDateTime nuevaHora = citaChocada.getFechaHora().plusDays(3);
                        sistema.reagendarCitaInteligente(citaChocada, nuevaHora, "Emergencia quirúrgica de alta prioridad.");
                    });
            }
        }
    }
    
    /**
     * 4. Nómina Mensual: Calcular salarios para todo el hospital.
     */
    public static void escenarioNominaMensual(SistemaGestionHospitalaria sistema) {
        System.out.println("\n===================================================================");
        System.out.println("ESCENARIO 4: CÁLCULO DE NÓMINA Y REPORTES");
        System.out.println("===================================================================");

        // Aumentar procedimientos para Cirujano y Radiólogo para mejor prueba
        sistema.getPersonal().stream()
            .filter(t -> t instanceof Cirujano)
            .limit(1)
            .forEach(t -> ((Cirujano)t).incrementarHorasCirugia(5.0));
            
        sistema.getPersonal().stream()
            .filter(t -> t instanceof Radiologo)
            .limit(1)
            .forEach(t -> ((Radiologo)t).incrementarEstudios(10));
            
        // Ejecutar Reporte de Nómina (Polimorfismo)
        sistema.reporteNominaMensual();
        
        // Reportes Adicionales
        sistema.reportePersonal();
        sistema.reporteCitasPorEstado();
        sistema.historialReagendamientos();
    }


    public static void main(String[] args) {
        SistemaGestionHospitalaria sistema = new SistemaGestionHospitalaria();
        
        // 1. Contratación
        List<TrabajadorMedico> personal = escenarioContratacion(sistema);
        
        // 2. Agenda
        List<CitaMedica> citas = escenarioAgendaSaturada(sistema);
        
        // 3 & 5. Crisis y Emergencia
        escenarioCrisisYEmergencia(sistema, personal, citas);
        
        // 4. Nómina y Reportes
        escenarioNominaMensual(sistema);
    }
}
