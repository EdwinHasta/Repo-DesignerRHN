/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "TSFORMULASCONCEPTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TSFormulasConceptos.findAll", query = "SELECT t FROM TSFormulasConceptos t"),
    @NamedQuery(name = "TSFormulasConceptos.findBySecuencia", query = "SELECT t FROM TSFormulasConceptos t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TSFormulasConceptos.findByOrigen", query = "SELECT t FROM TSFormulasConceptos t WHERE t.origen = :origen")})
public class TSFormulasConceptos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "ORIGEN")
    private String origen;
    @JoinColumn(name = "TIPOSUELDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposSueldos tiposueldo;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public TSFormulasConceptos() {
    }

    public TSFormulasConceptos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TSFormulasConceptos(BigInteger secuencia, String origen) {
        this.secuencia = secuencia;
        this.origen = origen;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getOrigen() {
        if (origen == null) {
            origen = "SUELDO";
        }
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public TiposSueldos getTiposueldo() {
        return tiposueldo;
    }

    public void setTiposueldo(TiposSueldos tiposueldo) {
        this.tiposueldo = tiposueldo;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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
        if (!(object instanceof TSFormulasConceptos)) {
            return false;
        }
        TSFormulasConceptos other = (TSFormulasConceptos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TSFormulasConceptos[ secuencia=" + secuencia + " ]";
    }

}
