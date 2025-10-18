import java.text.DecimalFormat;

/**
 * Hereda de TrabajadorMedico e implementa su propia lógica salarial.
 */
public class Enfermero extends TrabajadorMedico {
    private Enums.TipoTurno tipoTurno;
    private String nivelCertificacion;
    private final double BONIFICACION_NOCTURNA = 350.00;

    public Enfermero(String nombreCompleto, String departamentoAsignado, int anosExperiencia, double salarioBase,
                     Enums.TipoTurno tipoTurno, String nivelCertificacion) {
        super(nombreCompleto, departamentoAsignado, anosExperiencia, salarioBase);
        this.tipoTurno = tipoTurno;
        this.nivelCertificacion = nivelCertificacion;
    }

    @Override
    public double calcularSalario() {
        double salarioTotal = getSalarioBase();
        if (this.tipoTurno == Enums.TipoTurno.NOCTURNO) {
            salarioTotal += BONIFICACION_NOCTURNA;
        }
        return salarioTotal;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String turnoStr = tipoTurno == Enums.TipoTurno.NOCTURNO ? "Nocturno (Bono: $"+ df.format(BONIFICACION_NOCTURNA) +")" : "Matutino";
        return super.toString() + 
               " | Tipo: Enfermero" +
               " | Turno: " + turnoStr +
               " | Cert.: " + nivelCertificacion;
    }

    public Enums.TipoTurno getTipoTurno() { return tipoTurno; }
    public String getNivelCertificacion() { return nivelCertificacion; }
    public double getBONIFICACION_NOCTURNA() { return BONIFICACION_NOCTURNA; }

    public void setTipoTurno(Enums.TipoTurno tipoTurno) { this.tipoTurno = tipoTurno; }
    public void setNivelCertificacion(String nivelCertificacion) { this.nivelCertificacion = nivelCertificacion; }
}
