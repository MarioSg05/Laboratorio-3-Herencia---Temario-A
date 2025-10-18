import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import static Enums.TipoCita;
import static Enums.EstadoCita;

/**
 * Aplica composición al tener una referencia a TrabajadorMedico.
 */
public class CitaMedica {
    private final String idCita;
    private String nombrePaciente;
    private TrabajadorMedico medicoAsignado; 
    private LocalDateTime fechaHora;
    private TipoCita tipoCita;
    private EstadoCita estadoActual;
    private String historialReagendamiento;

    public CitaMedica(String nombrePaciente, TrabajadorMedico medicoAsignado, LocalDateTime fechaHora, TipoCita tipoCita) {
        this.idCita = "CIT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.nombrePaciente = nombrePaciente;
        this.medicoAsignado = medicoAsignado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.estadoActual = EstadoCita.PROGRAMADA;
        this.historialReagendamiento = "Creada en: " + this.fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n";
    }

    /**
     * Actualiza la fecha y hora de la cita y añade un registro al historial.
     * @param nuevaFechaHora La nueva fecha y hora.
     * @param motivo Motivo del cambio.
     */
    public void reagendar(LocalDateTime nuevaFechaHora, String motivo) {
        LocalDateTime anterior = this.fechaHora;
        this.fechaHora = nuevaFechaHora;
        this.estadoActual = EstadoCita.REAGENDADA;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.historialReagendamiento += String.format(
            "   -> Reagendada desde [%s] a [%s]. Motivo: %s\n",
            anterior.format(formatter),
            nuevaFechaHora.format(formatter),
            motivo
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
            "Cita ID: %s | Paciente: %s | Médico: %s (%s) | Fecha/Hora: %s | Tipo: %s | Estado: %s",
            idCita,
            nombrePaciente,
            medicoAsignado.getNombreCompleto(),
            medicoAsignado.getDepartamentoAsignado(),
            fechaHora.format(formatter),
            tipoCita.name(),
            estadoActual.name()
        );
    }

    public String getIdCita() { return idCita; }
    public String getNombrePaciente() { return nombrePaciente; }
    public TrabajadorMedico getMedicoAsignado() { return medicoAsignado; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public TipoCita getTipoCita() { return tipoCita; }
    public EstadoCita getEstadoActual() { return estadoActual; }
    public String getHistorialReagendamiento() { return historialReagendamiento; }
    
    public void setEstadoActual(EstadoCita estadoActual) { this.estadoActual = estadoActual; }
    public void setMedicoAsignado(TrabajadorMedico medicoAsignado) { this.medicoAsignado = medicoAsignado; }
}
