/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "TEFORMULASCONCEPTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TEFormulasConceptos.findAll", query = "SELECT t FROM TEFormulasConceptos t"),
    @NamedQuery(name = "TEFormulasConceptos.findBySecuencia", query = "SELECT t FROM TEFormulasConceptos t WHERE t.secuencia = :secuencia")})
public class TEFormulasConceptos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "TSGRUPOTIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TSGruposTiposEntidades tsgrupotipoentidad;
    @JoinColumn(name = "TIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposEntidades tipoentidad;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public TEFormulasConceptos() {
    }

    public TEFormulasConceptos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TSGruposTiposEntidades getTsgrupotipoentidad() {
        return tsgrupotipoentidad;
    }

    public void setTsgrupotipoentidad(TSGruposTiposEntidades tsgrupotipoentidad) {
        this.tsgrupotipoentidad = tsgrupotipoentidad;
    }

    public TiposEntidades getTipoentidad() {
        return tipoentidad;
    }

    public void setTipoentidad(TiposEntidades tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Empresas getEmpresa() {
        getConcepto();
        if (concepto != null) {
            empresa = concepto.getEmpresa();
        }
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
        if (!(object instanceof TEFormulasConceptos)) {
            return false;
        }
        TEFormulasConceptos other = (TEFormulasConceptos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TEFormulasConceptos[ secuencia=" + secuencia + " ]";
    }

}
