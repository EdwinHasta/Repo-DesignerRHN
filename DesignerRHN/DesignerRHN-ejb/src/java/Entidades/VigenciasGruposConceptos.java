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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASGRUPOSCONCEPTOS")
@NamedQueries({
    @NamedQuery(name = "VigenciasGruposConceptos.findAll", query = "SELECT v FROM VigenciasGruposConceptos v")})
public class VigenciasGruposConceptos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.DATE)
    private Date fechainicial;
    @Basic(optional = false)
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.DATE)
    private Date fechafinal;
    @JoinColumn(name = "GRUPOCONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private GruposConceptos gruposConceptos;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;
    @Transient
    private String strFechaInicial;
    @Transient
    private String strFechaFinal;

    public VigenciasGruposConceptos() {
    }

    public VigenciasGruposConceptos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasGruposConceptos(BigInteger secuencia, Date fechainicial, Date fechafinal) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
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

    public GruposConceptos getGrupoconcepto() {
        return gruposConceptos;
    }

    public void setGrupoconcepto(GruposConceptos grupoconcepto) {
        this.gruposConceptos = grupoconcepto;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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
        if (!(object instanceof VigenciasGruposConceptos)) {
            return false;
        }
        VigenciasGruposConceptos other = (VigenciasGruposConceptos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciasgruposconceptos[ secuencia=" + secuencia + " ]";
    }

}
