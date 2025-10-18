import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Clase abstracta para todos los trabajadores médicos
 */
public abstract class TrabajadorMedico {
    private final String idEmpleado;
    private String nombreCompleto;
    private String departamentoAsignado;
    private int anosExperiencia;
    private double salarioBase;
    private int citasAsignadas = 0; // Contador auxiliar para cálculos de salario o reportes

    // Constructor Base
    public TrabajadorMedico(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase) {
        // Generación de ID de empleado único
        this.idEmpleado = "EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.nombreCompleto = nombreCompleto;
        this.departamentoAsignado = departamentoAsignado;
        this.anosExperiencia = anosExperiencia;
        this.salarioBase = salarioBase;
    }

    // Método abstracto para cálculo de salario
    /**
     * Calcula el salario mensual del trabajador médico según su especialización.
     * @return Salario total calculado.
     */
    public abstract double calcularSalario();

    /**
     * Proporciona la información base del trabajador.
     * @return String con la información formateada.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "ID: " + idEmpleado +
               " | Nombre: " + nombreCompleto +
               " | Depto: " + departamentoAsignado +
               " | Exp: " + anosExperiencia + " años" +
               " | Salario Base: $" + df.format(salarioBase);
    }

    public String getIdEmpleado() { return idEmpleado; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getDepartamentoAsignado() { return departamentoAsignado; }
    public int getAnosExperiencia() { return anosExperiencia; }
    public double getSalarioBase() { return salarioBase; }
    public int getCitasAsignadas() { return citasAsignadas; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setDepartamentoAsignado(String departamentoAsignado) { this.departamentoAsignado = departamentoAsignado; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public void incrementarCitasAsignadas() { this.citasAsignadas++; }
    public void decrementarCitasAsignadas() { if (this.citasAsignadas > 0) this.citasAsignadas--; }
    public void establecerCitasAsignadas(int cantidad) { this.citasAsignadas = cantidad; }
}
