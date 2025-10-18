import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class Radiologo extends TrabajadorMedico {
    private String equiposCertificados;
    private double tarifaPorEstudio;
    private int estudiosRealizados;

    public Radiologo(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                     String equiposCertificados, double tarifaPorEstudio) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.equiposCertificados = equiposCertificados;
        this.tarifaPorEstudio = tarifaPorEstudio;
        this.estudiosRealizados = 0;
    }

    // Polimorfismo: Implementación específica de cálculo salarial
    @Override
    public double calcularSalario() {
        // Especialistas (Radiólogos): Salario base + comisiones por procedimientos
        return getSalarioBase() + (this.estudiosRealizados * this.tarifaPorEstudio);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Radiólogo" +
               " | Equipos: " + equiposCertificados.substring(0, Math.min(equiposCertificados.length(), 15)) + "..." +
               " | Tarifa Estudio: $" + df.format(tarifaPorEstudio) +
               " | Estudios/Mes: " + estudiosRealizados;
    }

    public String getEquiposCertificados() { return equiposCertificados; }
    public double getTarifaPorEstudio() { return tarifaPorEstudio; }
    public int getEstudiosRealizados() { return estudiosRealizados; }

    public void incrementarEstudiosRealizados(int estudios) {
        this.estudiosRealizados += estudios;
    }

    public void setEquiposCertificados(String equiposCertificados) { this.equiposCertificados = equiposCertificados; }
    public void setTarifaPorEstudio(double tarifaPorEstudio) { this.tarifaPorEstudio = tarifaPorEstudio; }
}
