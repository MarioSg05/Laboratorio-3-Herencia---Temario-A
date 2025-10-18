import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class Cirujano extends TrabajadorMedico {
    private String tiposOperaciones;
    private double horasCirugiaDisponibles;
    private double bonoPorRiesgo;
    private double horasCirugiaRealizadas;

    public Cirujano(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                    String tiposOperaciones, double horasCirugiaDisponibles, double bonoPorRiesgo) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.tiposOperaciones = tiposOperaciones;
        this.horasCirugiaDisponibles = horasCirugiaDisponibles;
        this.bonoPorRiesgo = bonoPorRiesgo;
        this.horasCirugiaRealizadas = 0;
    }

    // Polimorfismo  de cálculo salarial
    @Override
    public double calcularSalario() {
        // Cirujanos: Salario base + (horas de cirugía × Tarifa por hora fija 150.0) + bonos por riesgo
        double tarifaPorHora = 150.00;
        return getSalarioBase() + (this.horasCirugiaRealizadas * tarifaPorHora) + this.bonoPorRiesgo;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Cirujano" +
               " | Ops: " + tiposOperaciones.substring(0, Math.min(tiposOperaciones.length(), 20)) + "..." +
               " | Bono Riesgo: $" + df.format(bonoPorRiesgo) +
               " | Horas Realizadas/Mes: " + horasCirugiaRealizadas;
    }
    
    public String getTiposOperaciones() { return tiposOperaciones; }
    public double getBonoPorRiesgo() { return bonoPorRiesgo; }
    public double getHorasCirugiaRealizadas() { return horasCirugiaRealizadas; }

    public void incrementarHorasCirugiaRealizadas(double horas) {
        this.horasCirugiaRealizadas += horas;
    }
    
    public void setTiposOperaciones(String tiposOperaciones) { this.tiposOperaciones = tiposOperaciones; }
    public void setBonoPorRiesgo(double bonoPorRiesgo) { this.bonoPorRiesgo = bonoPorRiesgo; }
}
