import java.text.DecimalFormat;

/**
 * Implementa atributos comunes y el método toString() general.
 */
public abstract class TrabajadorMedico {
    private String nombreCompleto;
    private String departamentoAsignado;
    private int anosExperiencia;
    private double salarioBase;
    private int citasAsignadas;

    public TrabajadorMedico(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase) {
        this.nombreCompleto = nombreCompleto;
        this.departamentoAsignado = departamentoAsignado;
        this.anosExperiencia = anosExperiencia;
        this.salarioBase = salarioBase;
        this.citasAsignadas = 0; // Inicializamos el contador
    }

    /**
     * Método abstracto que debe ser implementado por las clases hijas 
     * para definir su lógica específica de cálculo salarial (Polimorfismo).
     * @return El salario total calculado.
     */
    public abstract double calcularSalario();

    /**
     * Override del método toString para obtener una representación
     * estandarizada de todos los trabajadores.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "Trabajador: " + nombreCompleto + 
               " | Depto.: " + departamentoAsignado + 
               " | Exp.: " + anosExperiencia + " años" +
               " | Salario Base: $" + df.format(salarioBase);
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public String getDepartamentoAsignado() { return departamentoAsignado; }
    public int getAnosExperiencia() { return anosExperiencia; }
    public double getSalarioBase() { return salarioBase; }
    public int getCitasAsignadas() { return citasAsignadas; }
    
    public void setCitasAsignadas(int citasAsignadas) { this.citasAsignadas = citasAsignadas; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setDepartamentoAsignado(String departamentoAsignado) { this.departamentoAsignado = departamentoAsignado; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
}
