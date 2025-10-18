import java.time.LocalDateTime;

/**
 * Contiene información sobre el paciente, el médico asignado y el estado.
 */
public class CitaMedica {
    private String idCita;
    private String nombrePaciente;
    private TrabajadorMedico medicoAsignado; 
    private LocalDateTime fechaHora;
    private Enums.TipoCita tipoCita;
    private Enums.EstadoCita estadoActual;
    private String historialReagendamiento;
    
    private static int contadorCitas = 0;

    public CitaMedica(String nombrePaciente, TrabajadorMedico medicoAsignado, 
                      LocalDateTime fechaHora, Enums.TipoCita tipoCita) {
        this.idCita = "CITA-" + String.format("%04d", ++contadorCitas);
        this.nombrePaciente = nombrePaciente;
        this.medicoAsignado = medicoAsignado;
        this.fechaHora = fechaHora;
        this.tipoCita = tipoCita;
        this.estadoActual = Enums.EstadoCita.PROGRAMADA;
        this.historialReagendamiento = "Cita creada en: " + fechaHora.toString();
        
        medicoAsignado.setCitasAsignadas(medicoAsignado.getCitasAsignadas() + 1);
    }
    
    /**
     * Reagendar una cita (mueve el estado y actualiza el historial).
     */
    public boolean reagendar(LocalDateTime nuevaFechaHora, String motivo) {
        if (this.estadoActual != Enums.EstadoCita.CANCELADA && this.estadoActual != Enums.EstadoCita.COMPLETADA) {
            this.fechaHora = nuevaFechaHora;
            this.estadoActual = Enums.EstadoCita.REAGENDADA;
            this.historialReagendamiento += "\nReagendada a " + nuevaFechaHora.toString() + " por: " + motivo;
            return true;
        }
        return false;
    }

    /**
     * Marca la cita como completada.
     */
    public void completarCita() {
        this.estadoActual = Enums.EstadoCita.COMPLETADA;
    }
    
    @Override
    public String toString() {
        return "Cita #" + idCita + 
               " | Paciente: " + nombrePaciente + 
               " | Tipo: " + tipoCita + 
               " | Estado: " + estadoActual +
               " | Fecha: " + fechaHora.toString() +
               " | Médico: " + medicoAsignado.getNombreCompleto() + " (" + medicoAsignado.getDepartamentoAsignado() + ")";
    }

    // Getters
    public String getIdCita() { return idCita; }
    public String getNombrePaciente() { return nombrePaciente; }
    public TrabajadorMedico getMedicoAsignado() { return medicoAsignado; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Enums.TipoCita getTipoCita() { return tipoCita; }
    public Enums.EstadoCita getEstadoActual() { return estadoActual; }
    public String getHistorialReagendamiento() { return historialReagendamiento; }

    // Setters
    public void setEstadoActual(Enums.EstadoCita estadoActual) { this.estadoActual = estadoActual; }
}
