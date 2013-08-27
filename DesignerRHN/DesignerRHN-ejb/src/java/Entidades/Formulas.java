/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FORMULAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulas.findAll", query = "SELECT f FROM Formulas f")})
public class Formulas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<FormulasDependientes> formulasDependientesCollection;
    @OneToMany(mappedBy = "dependiente")
    private Collection<FormulasDependientes> formulasDependientesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<ProcesosDependientes> procesosDependientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<FormulasConceptos> formulasconceptosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<FormulasNovedades> formulasnovedadesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "NOMBRECORTO")
    private String nombrecorto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRELARGO")
    private String nombrelargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 2000)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 1)
    @Column(name = "FUNDAMENTAL")
    private String fundamental;
    @Size(max = 1)
    @Column(name = "PERIODICIDADINDEPENDIENTE")
    private String periodicidadindependiente;
    @OneToMany(mappedBy = "formula")
    private Collection<Solucionesnodos> solucionesnodosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private Collection<Historiasformulas> historiasformulasCollection;

    public Formulas() {
    }

    public Formulas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Formulas(BigInteger secuencia, String nombrecorto, String nombrelargo, String tipo, String estado) {
        this.secuencia = secuencia;
        this.nombrecorto = nombrecorto;
        this.nombrelargo = nombrelargo;
        this.tipo = tipo;
        this.estado = estado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombrecorto() {
        return nombrecorto;
    }

    public void setNombrecorto(String nombrecorto) {
        this.nombrecorto = nombrecorto;
    }

    public String getNombrelargo() {
        return nombrelargo;
    }

    public void setNombrelargo(String nombrelargo) {
        this.nombrelargo = nombrelargo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFundamental() {
        return fundamental;
    }

    public void setFundamental(String fundamental) {
        this.fundamental = fundamental;
    }

    public String getPeriodicidadindependiente() {
        return periodicidadindependiente;
    }

    public void setPeriodicidadindependiente(String periodicidadindependiente) {
        this.periodicidadindependiente = periodicidadindependiente;
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<Solucionesnodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }

    @XmlTransient
    public Collection<Historiasformulas> getHistoriasformulasCollection() {
        return historiasformulasCollection;
    }

    public void setHistoriasformulasCollection(Collection<Historiasformulas> historiasformulasCollection) {
        this.historiasformulasCollection = historiasformulasCollection;
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
        if (!(object instanceof Formulas)) {
            return false;
        }
        Formulas other = (Formulas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formulas[ secuencia=" + secuencia + " ]";
    }

    public Collection<FormulasConceptos> getFormulasconceptosCollection() {
        return formulasconceptosCollection;
    }

    public void setFormulasconceptosCollection(Collection<FormulasConceptos> formulasconceptosCollection) {
        this.formulasconceptosCollection = formulasconceptosCollection;
    }

    public Collection<FormulasNovedades> getFormulasnovedadesCollection() {
        return formulasnovedadesCollection;
    }

    public void setFormulasnovedadesCollection(Collection<FormulasNovedades> formulasnovedadesCollection) {
        this.formulasnovedadesCollection = formulasnovedadesCollection;
    }

    @XmlTransient
    public Collection<FormulasDependientes> getFormulasDependientesCollection() {
        return formulasDependientesCollection;
    }

    public void setFormulasDependientesCollection(Collection<FormulasDependientes> formulasDependientesCollection) {
        this.formulasDependientesCollection = formulasDependientesCollection;
    }

    @XmlTransient
    public Collection<FormulasDependientes> getFormulasDependientesCollection1() {
        return formulasDependientesCollection1;
    }

    public void setFormulasDependientesCollection1(Collection<FormulasDependientes> formulasDependientesCollection1) {
        this.formulasDependientesCollection1 = formulasDependientesCollection1;
    }

    @XmlTransient
    public Collection<ProcesosDependientes> getProcesosDependientesCollection() {
        return procesosDependientesCollection;
    }

    public void setProcesosDependientesCollection(Collection<ProcesosDependientes> procesosDependientesCollection) {
        this.procesosDependientesCollection = procesosDependientesCollection;
    }
    
}
