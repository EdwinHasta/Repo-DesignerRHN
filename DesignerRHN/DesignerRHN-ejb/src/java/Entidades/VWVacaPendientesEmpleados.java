package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author user
 */
@Entity
public class VWVacaPendientesEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @NotNull
    @Column(name = "FECHAREPORTE")
    @Temporal(TemporalType.DATE)
    private Date fechaReporte;
    @NotNull
    @Column(name = "INICIALCAUSACION")
    @Temporal(TemporalType.DATE)
    private Date inicialCausacion;
    @NotNull
    @Column(name = "FINALCAUSACION")
    @Temporal(TemporalType.DATE)
    private Date finalCausacion;
    @Column(name = "CONCEPTO")
    @NotNull
    private BigInteger concepto;
    @Column(name = "RFVACACION")
    @NotNull
    private BigInteger rfvacacion;
    @Column(name = "RFNOVEDAD")
    @NotNull
    private BigInteger rfnovedad;
    @Column(name = "USUARIOREPORTA")
    @NotNull
    private BigInteger usuarioreporta;
    @Column(name = "FORMULA")
    @NotNull
    private BigInteger formula;
    @Column(name = "DIASPENDIENTES")
    private BigInteger diaspendientes;
    @Column(name = "VALORTOTAL")
    @NotNull
    private BigDecimal valortotal;
    @Size(max = 20)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 20)
    @NotNull
    @Column(name = "TERMINAL")
    private String terminal;
    @Size(max = 20)
    @NotNull
    @Column(name = "TIPO")
    private String tipo;

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Date getInicialCausacion() {
        return inicialCausacion;
    }

    public void setInicialCausacion(Date inicialCausacion) {
        this.inicialCausacion = inicialCausacion;
    }

    public Date getFinalCausacion() {
        return finalCausacion;
    }

    public void setFinalCausacion(Date finalCausacion) {
        this.finalCausacion = finalCausacion;
    }

    public BigInteger getConcepto() {
        return concepto;
    }

    public void setConcepto(BigInteger concepto) {
        this.concepto = concepto;
    }

    public BigInteger getRfvacacion() {
        return rfvacacion;
    }

    public void setRfvacacion(BigInteger rfvacacion) {
        this.rfvacacion = rfvacacion;
    }

    public BigInteger getRfnovedad() {
        return rfnovedad;
    }

    public void setRfnovedad(BigInteger rfnovedad) {
        this.rfnovedad = rfnovedad;
    }

    public BigInteger getUsuarioreporta() {
        return usuarioreporta;
    }

    public void setUsuarioreporta(BigInteger usuarioreporta) {
        this.usuarioreporta = usuarioreporta;
    }

    public BigInteger getFormula() {
        return formula;
    }

    public void setFormula(BigInteger formula) {
        this.formula = formula;
    }

    public BigInteger getDiaspendientes() {
        return diaspendientes;
    }

    public void setDiaspendientes(BigInteger diaspendientes) {
        this.diaspendientes = diaspendientes;
    }

    
    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
    
    
}
