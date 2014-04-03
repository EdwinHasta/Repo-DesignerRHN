/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "VIGENCIASRETENCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasRetenciones.findAll", query = "SELECT v FROM VigenciasRetenciones v ORDER BY v.fechavigencia ASC"),
    @NamedQuery(name = "VigenciasRetenciones.findBySecuencia", query = "SELECT v FROM VigenciasRetenciones v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasRetenciones.findByCodigo", query = "SELECT v FROM VigenciasRetenciones v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VigenciasRetenciones.findByFechavigencia", query = "SELECT v FROM VigenciasRetenciones v WHERE v.fechavigencia = :fechavigencia"),
    @NamedQuery(name = "VigenciasRetenciones.findByUvt", query = "SELECT v FROM VigenciasRetenciones v WHERE v.uvt = :uvt")})
public class VigenciasRetenciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @Column(name = "UVT")
    private BigInteger uvt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vigencia")
    private Collection<Retenciones> retencionesCollection;

    public VigenciasRetenciones() {
    }

    public VigenciasRetenciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasRetenciones(BigInteger secuencia, Short codigo, Date fechavigencia) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.fechavigencia = fechavigencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public BigInteger getUvt() {
        return uvt;
    }

    public void setUvt(BigInteger uvt) {
        this.uvt = uvt;
    }

    @XmlTransient
    public Collection<Retenciones> getRetencionesCollection() {
        return retencionesCollection;
    }

    public void setRetencionesCollection(Collection<Retenciones> retencionesCollection) {
        this.retencionesCollection = retencionesCollection;
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
        if (!(object instanceof VigenciasRetenciones)) {
            return false;
        }
        VigenciasRetenciones other = (VigenciasRetenciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasRetenciones[ secuencia=" + secuencia + " ]";
    }
    
}
