import java.text.DecimalFormat;
import static Enums.TipoTurno;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class Enfermero extends TrabajadorMedico {
    private TipoTurno tipoTurno;
    private String nivelCertificacion;
    private final double BONIFICACION_NOCTURNA = 350.00; // Bonificación fija por turno nocturno

    public Enfermero(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                     TipoTurno tipoTurno, String nivelCertificacion) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.tipoTurno = tipoTurno;
        this.nivelCertificacion = nivelCertificacion;
    }

    // Polimorfismo: Implementación específica de cálculo salarial
    @Override
    public double calcularSalario() {
        // Enfermeros: Salario base + bonificación nocturna (si aplica)
        double salarioTotal = getSalarioBase();
        if (this.tipoTurno == TipoTurno.NOCTURNO) {
            salarioTotal += BONIFICACION_NOCTURNA;
        }
        return salarioTotal;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String turnoStr = tipoTurno == TipoTurno.NOCTURNO ? "Nocturno (Bono: $"+ df.format(BONIFICACION_NOCTURNA) +")" : "Diurno";
        return super.toString() + 
               " | Tipo: Enfermero" +
               " | Turno: " + turnoStr +
               " | Cert.: " + nivelCertificacion;
    }

    public TipoTurno getTipoTurno() { return tipoTurno; }
    public String getNivelCertificacion() { return nivelCertificacion; }
    public double getBONIFICACION_NOCTURNA() { return BONIFICACION_NOCTURNA; }

    public void setTipoTurno(TipoTurno tipoTurno) { this.tipoTurno = tipoTurno; }
    public void setNivelCertificacion(String nivelCertificacion) { this.nivelCertificacion = nivelCertificacion; }
}
