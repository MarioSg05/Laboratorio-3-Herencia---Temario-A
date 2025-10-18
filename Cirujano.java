import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial basada en horas de cirugía.
 */
public class Cirujano extends TrabajadorMedico {
    private String tiposCirugia;
    private double bonoPorHoraCirugia;
    private double horasCirugiaAcumuladas; // Campo para registrar actividad

    public Cirujano(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                    String tiposCirugia, double horasCirugiaAcumuladas, double bonoPorHoraCirugia) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.tiposCirugia = tiposCirugia;
        this.horasCirugiaAcumuladas = horasCirugiaAcumuladas;
        this.bonoPorHoraCirugia = bonoPorHoraCirugia;
    }

    // Polimorfismo: Implementación específica de cálculo salarial
    @Override
    public double calcularSalario() {
        // Cirujano: Salario base + bono por horas de cirugía acumuladas.
        double salarioTotal = getSalarioBase();
        salarioTotal += horasCirugiaAcumuladas * bonoPorHoraCirugia;
        return salarioTotal;
    }
    
    /**
     * Método para incrementar el contador de horas de cirugía.
     */
    public void incrementarHorasCirugia(double horas) {
        this.horasCirugiaAcumuladas += horas;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return super.toString() + 
               " | Tipo: Cirujano" +
               " | Cirugías: " + tiposCirugia +
               " | Bono/Hora: $" + df.format(bonoPorHoraCirugia) +
               " | Horas Cirugía: " + new DecimalFormat("#0.0").format(horasCirugiaAcumuladas);
    }

    public String getTiposCirugia() { return tiposCirugia; }
    public double getBonoPorHoraCirugia() { return bonoPorHoraCirugia; }
    public double getHorasCirugiaAcumuladas() { return horasCirugiaAcumuladas; }

    public void setTiposCirugia(String tiposCirugia) { this.tiposCirugia = tiposCirugia; }
    public void setBonoPorHoraCirugia(double bonoPorHoraCirugia) { this.bonoPorHoraCirugia = bonoPorHoraCirugia; }
}
