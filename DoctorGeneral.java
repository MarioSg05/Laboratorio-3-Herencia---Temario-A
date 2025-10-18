import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class DoctorGeneral extends TrabajadorMedico {
    private String especializacionSecundaria;
    private int limiteConsultasMensual;
    private double bonoPorConsulta;
    private int consultasRealizadas; // Campo para registrar actividad

    public DoctorGeneral(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                         String especializacionSecundaria, int limiteConsultasMensual, double bonoPorConsulta) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.especializacionSecundaria = especializacionSecundaria;
        this.limiteConsultasMensual = limiteConsultasMensual;
        this.bonoPorConsulta = bonoPorConsulta;
        this.consultasRealizadas = 0; 
    }

    // Polimorfismo: Implementación específica de cálculo salarial
    @Override
    public double calcularSalario() {
        double salarioTotal = getSalarioBase();
        
        // Bonificación por consultas realizadas hasta el límite
        if (consultasRealizadas > 0) {
            int consultasConBono = Math.min(consultasRealizadas, limiteConsultasMensual);
            salarioTotal += consultasConBono * bonoPorConsulta;
        }
        
        return salarioTotal;
    }

    /**
     * Método para incrementar el contador de consultas realizadas.
     */
    public void incrementarConsultas(int cantidad) {
        this.consultasRealizadas += cantidad;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Doctor General" +
               " | Espec. Sec.: " + especializacionSecundaria +
               " | Bono por Consulta: $" + df.format(bonoPorConsulta) +
               " | Cons. Realizadas: " + consultasRealizadas + " (Límite: " + limiteConsultasMensual + ")";
    }

    public String getEspecializacionSecundaria() { return especializacionSecundaria; }
    public int getLimiteConsultasMensual() { return limiteConsultasMensual; }
    public double getBonoPorConsulta() { return bonoPorConsulta; }
    public int getConsultasRealizadas() { return consultasRealizadas; }

    public void setEspecializacionSecundaria(String especializacionSecundaria) { this.especializacionSecundaria = especializacionSecundaria; }
    public void setLimiteConsultasMensual(int limiteConsultasMensual) { this.limiteConsultasMensual = limiteConsultasMensual; }
    public void setBonoPorConsulta(double bonoPorConsulta) { this.bonoPorConsulta = bonoPorConsulta; }
}
