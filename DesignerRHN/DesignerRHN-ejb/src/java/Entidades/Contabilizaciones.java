/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "CONTABILIZACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contabilizaciones.findAll", query = "SELECT c FROM Contabilizaciones c"),
    @NamedQuery(name = "Contabilizaciones.findBySecuencia", query = "SELECT c FROM Contabilizaciones c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "Contabilizaciones.findByFechageneracion", query = "SELECT c FROM Contabilizaciones c WHERE c.fechageneracion = :fechageneracion"),
    @NamedQuery(name = "Contabilizaciones.findByFlag", query = "SELECT c FROM Contabilizaciones c WHERE c.flag = :flag"),
    @NamedQuery(name = "Contabilizaciones.findByFechacontabilizacion", query = "SELECT c FROM Contabilizaciones c WHERE c.fechacontabilizacion = :fechacontabilizacion")})
public class Contabilizaciones implements Serializable {
    @OneToMany(mappedBy = "contabilizacion")
    private Collection<InterconDynamics> interconDynamicsCollection;
    @OneToMany(mappedBy = "contabilizacion")
    private Collection<InterconSapBO> interconSapBOCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "FECHAGENERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechageneracion;
    @Size(max = 30)
    @Column(name = "FLAG")
    private String flag;
    @Column(name = "FECHACONTABILIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacontabilizacion;
    @OneToMany(mappedBy = "contabilizacion")
    private Collection<InterconTotal> interconTotalCollection;
    @JoinColumn(name = "SOLUCIONNODO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private SolucionesNodos solucionnodo;

    public Contabilizaciones() {
    }

    public Contabilizaciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechageneracion() {
        return fechageneracion;
    }

    public void setFechageneracion(Date fechageneracion) {
        this.fechageneracion = fechageneracion;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getFechacontabilizacion() {
        return fechacontabilizacion;
    }

    public void setFechacontabilizacion(Date fechacontabilizacion) {
        this.fechacontabilizacion = fechacontabilizacion;
    }

    @XmlTransient
    public Collection<InterconTotal> getInterconTotalCollection() {
        return interconTotalCollection;
    }

    public void setInterconTotalCollection(Collection<InterconTotal> interconTotalCollection) {
        this.interconTotalCollection = interconTotalCollection;
    }

    public SolucionesNodos getSolucionnodo() {
        return solucionnodo;
    }

    public void setSolucionnodo(SolucionesNodos solucionnodo) {
        this.solucionnodo = solucionnodo;
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
        if (!(object instanceof Contabilizaciones)) {
            return false;
        }
        Contabilizaciones other = (Contabilizaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Contabilizaciones[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<InterconSapBO> getInterconSapBOCollection() {
        return interconSapBOCollection;
    }

    public void setInterconSapBOCollection(Collection<InterconSapBO> interconSapBOCollection) {
        this.interconSapBOCollection = interconSapBOCollection;
    }

    @XmlTransient
    public Collection<InterconDynamics> getInterconDynamicsCollection() {
        return interconDynamicsCollection;
    }

    public void setInterconDynamicsCollection(Collection<InterconDynamics> interconDynamicsCollection) {
        this.interconDynamicsCollection = interconDynamicsCollection;
    }

}
