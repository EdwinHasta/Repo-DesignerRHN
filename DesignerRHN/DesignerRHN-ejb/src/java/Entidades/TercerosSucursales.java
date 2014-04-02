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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TERCEROSSUCURSALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TercerosSucursales.findAll", query = "SELECT t FROM TercerosSucursales t"),
    @NamedQuery(name = "TercerosSucursales.findBySecuencia", query = "SELECT t FROM TercerosSucursales t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TercerosSucursales.findByCodigosucursal", query = "SELECT t FROM TercerosSucursales t WHERE t.codigosucursal = :codigosucursal"),
    @NamedQuery(name = "TercerosSucursales.findByCodigopatronal", query = "SELECT t FROM TercerosSucursales t WHERE t.codigopatronal = :codigopatronal"),
    @NamedQuery(name = "TercerosSucursales.findByDescripcion", query = "SELECT t FROM TercerosSucursales t WHERE t.descripcion = :descripcion")})
public class TercerosSucursales implements Serializable {

    @OneToMany(mappedBy = "tercero")
    private Collection<Direcciones> direccionesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGOSUCURSAL")
    private BigInteger codigosucursal;
    @Column(name = "CODIGOPATRONAL")
    private Long codigopatronal;
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Terceros tercero;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Ciudades ciudad;

    public TercerosSucursales() {
    }

    public TercerosSucursales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TercerosSucursales(BigInteger secuencia, BigInteger codigosucursal) {
        this.secuencia = secuencia;
        this.codigosucursal = codigosucursal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigosucursal() {
        return codigosucursal;
    }

    public void setCodigosucursal(BigInteger codigosucursal) {
        this.codigosucursal = codigosucursal;
    }

    public Long getCodigopatronal() {
        return codigopatronal;
    }

    public void setCodigopatronal(Long codigopatronal) {
        this.codigopatronal = codigopatronal;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Terceros getTercero() {
        if (tercero == null) {
            tercero = new Terceros();
        }
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
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
        if (!(object instanceof TercerosSucursales)) {
            return false;
        }
        TercerosSucursales other = (TercerosSucursales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tercerossucursales[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
    }
}
