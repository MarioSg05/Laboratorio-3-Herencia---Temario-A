import java.text.DecimalFormat;

/**
 * Clase concreta para Radiólogos.
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial basada en estudios.
 */
public class Radiologo extends TrabajadorMedico {
    private String tiposExamenes;
    private double bonoPorEstudio;
    private int estudiosRealizados; // Campo para registrar actividad

    public Radiologo(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                     String tiposExamenes, double bonoPorEstudio) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.tiposExamenes = tiposExamenes;
        this.bonoPorEstudio = bonoPorEstudio;
        this.estudiosRealizados = 0; // Inicialización
    }

    // Polimorfismo: Implementación específica de cálculo salarial
    @Override
    public double calcularSalario() {
        // Radiólogo: Salario base + bono por cada estudio realizado.
        double salarioTotal = getSalarioBase();
        salarioTotal += estudiosRealizados * bonoPorEstudio;
        return salarioTotal;
    }
    
    /**
     * Método para incrementar el contador de estudios de diagnóstico realizados.
     */
    public void incrementarEstudios(int cantidad) {
        this.estudiosRealizados += cantidad;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Radiólogo" +
               " | Exámenes: " + tiposExamenes +
               " | Bono/Estudio: $" + df.format(bonoPorEstudio) +
               " | Estudios Realizados: " + estudiosRealizados;
    }

    // Getters y Setters
    public String getTiposExamenes() { return tiposExamenes; }
    public double getBonoPorEstudio() { return bonoPorEstudio; }
    public int getEstudiosRealizados() { return estudiosRealizados; }

    public void setTiposExamenes(String tiposExamenes) { this.tiposExamenes = tiposExamenes; }
    public void setBonoPorEstudio(double bonoPorEstudio) { this.bonoPorEstudio = bonoPorEstudio; }
}
