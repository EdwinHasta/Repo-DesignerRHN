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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ADIESTRAMIENTOSF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdiestramientosF.findAll", query = "SELECT a FROM AdiestramientosF a")})
public class AdiestramientosF implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 150)
    @Column(name = "OBJETIVO")
    private String objetivo;
    @Size(max = 250)
    @Column(name = "METAS")
    private String metas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COSTOTOTAL")
    private BigDecimal costototal;
    @Size(max = 50)
    @Column(name = "LUGAR")
    private String lugar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahasta;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ESTRUCTURA")
    private BigInteger estructura;
    @JoinColumn(name = "TIPOEDUCACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposEducaciones tipoeducacion;
    @OneToMany(mappedBy = "adiestramientof")
    private Collection<VigenciasFormales> vigenciasFormalesCollection;

    public AdiestramientosF() {
    }

    public AdiestramientosF(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public AdiestramientosF(BigDecimal secuencia, BigDecimal costototal, Date fechadesde, Date fechahasta) {
        this.secuencia = secuencia;
        this.costototal = costototal;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public BigDecimal getCostototal() {
        return costototal;
    }

    public void setCostototal(BigDecimal costototal) {
        this.costototal = costototal;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getEstructura() {
        return estructura;
    }

    public void setEstructura(BigInteger estructura) {
        this.estructura = estructura;
    }

    public TiposEducaciones getTipoeducacion() {
        return tipoeducacion;
    }

    public void setTipoeducacion(TiposEducaciones tipoeducacion) {
        this.tipoeducacion = tipoeducacion;
    }

    @XmlTransient
    public Collection<VigenciasFormales> getVigenciasFormalesCollection() {
        return vigenciasFormalesCollection;
    }

    public void setVigenciasFormalesCollection(Collection<VigenciasFormales> vigenciasFormalesCollection) {
        this.vigenciasFormalesCollection = vigenciasFormalesCollection;
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
        if (!(object instanceof AdiestramientosF)) {
            return false;
        }
        AdiestramientosF other = (AdiestramientosF) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.AdiestramientosF[ secuencia=" + secuencia + " ]";
    }
    
}
