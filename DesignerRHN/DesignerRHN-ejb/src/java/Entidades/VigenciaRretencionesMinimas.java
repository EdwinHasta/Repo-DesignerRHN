/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "VIGENCIASRETENCIONESMINIMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciaRretencionesMinimas.findAll", query = "SELECT v FROM VigenciaRretencionesMinimas v")})
public class VigenciaRretencionesMinimas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vigenciaretencionminima")
    private Collection<RetencionesMinimas> retencionesMinimasCollection;

    public VigenciaRretencionesMinimas() {
    }

    public VigenciaRretencionesMinimas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciaRretencionesMinimas(BigDecimal secuencia, BigInteger codigo, Date fechavigencia) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.fechavigencia = fechavigencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    @XmlTransient
    public Collection<RetencionesMinimas> getRetencionesMinimasCollection() {
        return retencionesMinimasCollection;
    }

    public void setRetencionesMinimasCollection(Collection<RetencionesMinimas> retencionesMinimasCollection) {
        this.retencionesMinimasCollection = retencionesMinimasCollection;
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
        if (!(object instanceof VigenciaRretencionesMinimas)) {
            return false;
        }
        VigenciaRretencionesMinimas other = (VigenciaRretencionesMinimas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciaRretencionesMinimas[ secuencia=" + secuencia + " ]";
    }
    
}
