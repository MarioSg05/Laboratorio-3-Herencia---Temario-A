/**
 * Clase que contiene las enumeraciones
 */
public class Enums {
    
    /**
     * Enumeración para el tipo de citas médicas.
     */
    public enum TipoCita {
        CONSULTA_GENERAL,
        CIRUGIA,
        TERAPIA,
        DIAGNOSTICO,
        ADMINISTRATIVA
    }

    /**
     * Enumeración para el estado actual de las citas.
     */
    public enum EstadoCita {
        PROGRAMADA,
        CONFIRMADA,
        EN_PROGRESO,
        COMPLETADA,
        CANCELADA,
        REAGENDADA
    }

    /**
     * Enumeración para el tipo de turno del personal de enfermería
     */
    public enum TipoTurno {
        MATUTINO,
        NOCTURNO
    }
}
