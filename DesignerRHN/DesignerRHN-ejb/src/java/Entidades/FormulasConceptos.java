/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FORMULASCONCEPTOS")
@NamedQueries({
    @NamedQuery(name = "FormulasConceptos.findAll", query = "SELECT f FROM FormulasConceptos f")})
public class FormulasConceptos implements Serializable {
    @OneToMany(mappedBy = "formulaconcepto")
    private Collection<SolucionesFormulas> solucionesFormulasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private Formulas formula;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public FormulasConceptos() {
    }

    public FormulasConceptos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public FormulasConceptos(BigInteger secuencia, Date fechainicial, Date fechafinal, BigInteger orden, String tipo) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
        this.orden = orden;
        this.tipo = tipo;
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

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
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
        if (!(object instanceof FormulasConceptos)) {
            return false;
        }
        FormulasConceptos other = (FormulasConceptos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formulasconceptos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<SolucionesFormulas> getSolucionesFormulasCollection() {
        return solucionesFormulasCollection;
    }

    public void setSolucionesFormulasCollection(Collection<SolucionesFormulas> solucionesFormulasCollection) {
        this.solucionesFormulasCollection = solucionesFormulasCollection;
    }
    
}
