/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VIGENCIASCUENTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasCuentas.findAll", query = "SELECT v FROM VigenciasCuentas v"),
    @NamedQuery(name = "VigenciasCuentas.findBySecuencia", query = "SELECT v FROM VigenciasCuentas v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasCuentas.findByFechafinal", query = "SELECT v FROM VigenciasCuentas v WHERE v.fechafinal = :fechafinal"),
    @NamedQuery(name = "VigenciasCuentas.findByFechainicial", query = "SELECT v FROM VigenciasCuentas v WHERE v.fechainicial = :fechainicial")})
public class VigenciasCuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @JoinColumn(name = "TIPOCC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private TiposCentrosCostos tipocc;
    @JoinColumn(name = "CUENTAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private Cuentas cuentad;
    @JoinColumn(name = "CUENTAC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cuentas cuentac;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private Conceptos concepto;
    @JoinColumn(name = "CONSOLIDADORD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private CentrosCostos consolidadord;
    @JoinColumn(name = "CONSOLIDADORC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private CentrosCostos consolidadorc;
    @Transient
    private String strFechaInicial;
    @Transient
    private String strFechaFinal;

    public VigenciasCuentas() {
    }

    public VigenciasCuentas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasCuentas(BigInteger secuencia, Date fechafinal, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechafinal = fechafinal;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public TiposCentrosCostos getTipocc() {
        return tipocc;
    }

    public void setTipocc(TiposCentrosCostos tipocc) {
        this.tipocc = tipocc;
    }

    public Cuentas getCuentad() {
        return cuentad;
    }

    public void setCuentad(Cuentas cuentad) {
        this.cuentad = cuentad;
    }

    public Cuentas getCuentac() {
        return cuentac;
    }

    public void setCuentac(Cuentas cuentac) {
        this.cuentac = cuentac;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public CentrosCostos getConsolidadord() {
        return consolidadord;
    }

    public void setConsolidadord(CentrosCostos consolidadord) {
        this.consolidadord = consolidadord;
    }

    public CentrosCostos getConsolidadorc() {
        return consolidadorc;
    }

    public void setConsolidadorc(CentrosCostos consolidadorc) {
        this.consolidadorc = consolidadorc;
    }

    public String getStrFechaInicial() {
        if (fechainicial != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            strFechaInicial = formatoFecha.format(fechainicial);
        } else {
            strFechaInicial = " ";
        }
        return strFechaInicial;
    }

    public void setStrFechaInicial(String strFechaInicial) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechainicial = formatoFecha.parse(strFechaInicial);
        this.strFechaInicial = strFechaInicial;
    }

    public String getStrFechaFinal() {
        if (fechafinal != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            strFechaFinal = formatoFecha.format(fechafinal);
        } else {
            strFechaFinal = " ";
        }
        return strFechaFinal;
    }

    public void setStrFechaFinal(String strFechaFinal) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechafinal = formatoFecha.parse(strFechaFinal);
        this.strFechaFinal = strFechaFinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VigenciasCuentas)) {
            return false;
        }
        VigenciasCuentas other = (VigenciasCuentas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasCuentas[ secuencia=" + secuencia + " ]";
    }

}
