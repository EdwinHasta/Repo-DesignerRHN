/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FORMULASCONTRATOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulascontratos.findAll", query = "SELECT f FROM Formulascontratos f"),
    @NamedQuery(name = "Formulascontratos.findBySecuencia", query = "SELECT f FROM Formulascontratos f WHERE f.secuencia = :secuencia"),
    @NamedQuery(name = "Formulascontratos.findByFechainicial", query = "SELECT f FROM Formulascontratos f WHERE f.fechainicial = :fechainicial"),
    @NamedQuery(name = "Formulascontratos.findByFechafinal", query = "SELECT f FROM Formulascontratos f WHERE f.fechafinal = :fechafinal")})
public class Formulascontratos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @OneToMany(mappedBy = "formulacontrato")
    private Collection<VigenciasAfiliaciones> vigenciasafiliacionesCollection;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros tercero;
    @JoinColumn(name = "PERIODICIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Periodicidades periodicidad;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @JoinColumn(name = "CONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Contratos contrato;

    public Formulascontratos() {
    }

    public Formulascontratos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Formulascontratos(BigDecimal secuencia, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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

    @XmlTransient
    public Collection<VigenciasAfiliaciones> getVigenciasafiliacionesCollection() {
        return vigenciasafiliacionesCollection;
    }

    public void setVigenciasafiliacionesCollection(Collection<VigenciasAfiliaciones> vigenciasafiliacionesCollection) {
        this.vigenciasafiliacionesCollection = vigenciasafiliacionesCollection;
    }

    public Terceros getTercero() {
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    public Periodicidades getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidades periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Contratos getContrato() {
        return contrato;
    }

    public void setContrato(Contratos contrato) {
        this.contrato = contrato;
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
        if (!(object instanceof Formulascontratos)) {
            return false;
        }
        Formulascontratos other = (Formulascontratos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formulascontratos[ secuencia=" + secuencia + " ]";
    }
    
}
