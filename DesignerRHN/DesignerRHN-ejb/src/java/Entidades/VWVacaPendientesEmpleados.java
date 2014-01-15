package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VWVACAPENDIENTESEMPLEADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWVacaPendientesEmpleados.findAll", query = "SELECT v FROM VWVacaPendientesEmpleados v"),
    @NamedQuery(name = "VWVacaPendientesEmpleados.findByEmpleado", query = "SELECT v FROM VWVacaPendientesEmpleados v WHERE v.empleado = :empleado")})
public class VWVacaPendientesEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RFVACACION")
    private BigInteger rfvacacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLEADO")
    private BigInteger empleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ESTADO")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIASPENDIENTES")
    private BigInteger diaspendientes;
    @Column(name = "INICIALCAUSACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicialcausacion;
    @Column(name = "FINALCAUSACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalcausacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RFNOVEDAD")
    private BigInteger rfnovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONCEPTO")
    private BigInteger concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAREPORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechareporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORMULA")
    private BigInteger formula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TERMINAL")
    private String terminal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USUARIOREPORTA")
    private BigInteger usuarioreporta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORTOTAL")
    private BigDecimal valortotal;
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;

   
    public BigInteger getEmpleado() {
        return empleado;
    }

    public void setEmpleado(BigInteger empleado) {
        this.empleado = empleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getDiaspendientes() {
        return diaspendientes;
    }

    public void setDiaspendientes(BigInteger diaspendientes) {
        this.diaspendientes = diaspendientes;
    }

    public Date getInicialcausacion() {
        return inicialcausacion;
    }

    public void setInicialcausacion(Date inicialcausacion) {
        this.inicialcausacion = inicialcausacion;
    }

    public Date getFinalcausacion() {
        return finalcausacion;
    }

    public void setFinalcausacion(Date finalcausacion) {
        this.finalcausacion = finalcausacion;
    }

    public BigInteger getRfnovedad() {
        return rfnovedad;
    }

    public void setRfnovedad(BigInteger rfnovedad) {
        this.rfnovedad = rfnovedad;
    }

    public BigInteger getRfvacacion() {
        return rfvacacion;
    }

    public void setRfvacacion(BigInteger rfvacacion) {
        this.rfvacacion = rfvacacion;
    }

    public BigInteger getConcepto() {
        return concepto;
    }

    public void setConcepto(BigInteger concepto) {
        this.concepto = concepto;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechareporte() {
        return fechareporte;
    }

    public void setFechareporte(Date fechareporte) {
        this.fechareporte = fechareporte;
    }

    public BigInteger getFormula() {
        return formula;
    }

    public void setFormula(BigInteger formula) {
        this.formula = formula;
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

    public BigInteger getUsuarioreporta() {
        return usuarioreporta;
    }

    public void setUsuarioreporta(BigInteger usuarioreporta) {
        this.usuarioreporta = usuarioreporta;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }
    
    
}
