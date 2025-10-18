import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class DoctorGeneral extends TrabajadorMedico {
    private String especializacion;
    private int capacidadPacientesPorDia;
    private double tarifaConsulta;
    private int consultasRealizadas;

    public DoctorGeneral(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                         String especializacion, int capacidadPacientesPorDia, double tarifaConsulta) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.especializacion = especializacion;
        this.capacidadPacientesPorDia = capacidadPacientesPorDia;
        this.tarifaConsulta = tarifaConsulta;
        this.consultasRealizadas = 0;
    }

    @Override
    public double calcularSalario() {
        // Doctores: Salario base + (número de consultas × tarifa)
        return getSalarioBase() + (this.consultasRealizadas * this.tarifaConsulta);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Doctor General" +
               " | Especialización: " + especializacion +
               " | Tarifa Consulta: $" + df.format(tarifaConsulta) +
               " | Consultas/Mes: " + consultasRealizadas;
    }
    
    public String getEspecializacion() { return especializacion; }
    public int getCapacidadPacientesPorDia() { return capacidadPacientesPorDia; }
    public double getTarifaConsulta() { return tarifaConsulta; }
    public int getConsultasRealizadas() { return consultasRealizadas; }

    public void incrementarConsultasRealizadas(int consultas) {
        this.consultasRealizadas += consultas;
    }
    
    public void setEspecializacion(String especializacion) { this.especializacion = especializacion; }
    public void setTarifaConsulta(double tarifaConsulta) { this.tarifaConsulta = tarifaConsulta; }
}
