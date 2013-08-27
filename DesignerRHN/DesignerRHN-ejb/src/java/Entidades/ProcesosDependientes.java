/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PROCESOSDEPENDIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcesosDependientes.findAll", query = "SELECT p FROM ProcesosDependientes p")})
public class ProcesosDependientes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @OneToMany(mappedBy = "procesodependiente")
    private Collection<SolucionesFormulas> solucionesFormulasCollection;
    @JoinColumn(name = "DEPENDIENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Procesos dependiente;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;

    public ProcesosDependientes() {
    }

    public ProcesosDependientes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    @XmlTransient
    public Collection<SolucionesFormulas> getSolucionesFormulasCollection() {
        return solucionesFormulasCollection;
    }

    public void setSolucionesFormulasCollection(Collection<SolucionesFormulas> solucionesFormulasCollection) {
        this.solucionesFormulasCollection = solucionesFormulasCollection;
    }

    public Procesos getDependiente() {
        return dependiente;
    }

    public void setDependiente(Procesos dependiente) {
        this.dependiente = dependiente;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
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
        if (!(object instanceof ProcesosDependientes)) {
            return false;
        }
        ProcesosDependientes other = (ProcesosDependientes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ProcesosDependientes[ secuencia=" + secuencia + " ]";
    }
    
}
