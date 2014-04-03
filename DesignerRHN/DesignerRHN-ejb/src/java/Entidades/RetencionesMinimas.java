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
 * @author user
 */
@Entity
@Table(name = "RETENCIONESMINIMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RetencionesMinimas.findAll", query = "SELECT r FROM RetencionesMinimas r")})
public class RetencionesMinimas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MENSUALIZADO")
    private BigInteger mensualizado;
    @Column(name = "RETENCION")
    private BigInteger retencion;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "RESTAUVT")
    private BigInteger restauvt;
    @OneToMany(mappedBy = "retencionminima")
    private Collection<Declarantes> declarantesCollection;
    @JoinColumn(name = "VIGENCIARETENCIONMINIMA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private VigenciasRetencionesMinimas vigenciaretencionminima;

    public RetencionesMinimas() {
    }

    public RetencionesMinimas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public RetencionesMinimas(BigInteger secuencia, BigInteger mensualizado) {
        this.secuencia = secuencia;
        this.mensualizado = mensualizado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getMensualizado() {
        return mensualizado;
    }

    public void setMensualizado(BigInteger mensualizado) {
        this.mensualizado = mensualizado;
    }

    public BigInteger getRetencion() {
        return retencion;
    }

    public void setRetencion(BigInteger retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigInteger getRestauvt() {
        return restauvt;
    }

    public void setRestauvt(BigInteger restauvt) {
        this.restauvt = restauvt;
    }

    @XmlTransient
    public Collection<Declarantes> getDeclarantesCollection() {
        return declarantesCollection;
    }

    public void setDeclarantesCollection(Collection<Declarantes> declarantesCollection) {
        this.declarantesCollection = declarantesCollection;
    }

    public VigenciasRetencionesMinimas getVigenciasretencionminima() {
        if(vigenciaretencionminima == null){
            vigenciaretencionminima = new VigenciasRetencionesMinimas();
        }
        return vigenciaretencionminima;
    }

    public void setVigenciaretencionminima(VigenciasRetencionesMinimas vigenciaretencionminima) {
        this.vigenciaretencionminima = vigenciaretencionminima;
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
        if (!(object instanceof RetencionesMinimas)) {
            return false;
        }
        RetencionesMinimas other = (RetencionesMinimas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RetencionesMinimas[ secuencia=" + secuencia + " ]";
    }
    
}
