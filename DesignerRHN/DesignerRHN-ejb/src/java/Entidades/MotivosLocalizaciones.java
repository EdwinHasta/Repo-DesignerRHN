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
 * @author user
 */
@Entity
@Table(name = "MOTIVOSLOCALIZACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivosLocalizaciones.findAll", query = "SELECT m FROM MotivosLocalizaciones m"),
    @NamedQuery(name = "MotivosLocalizaciones.findBySecuencia", query = "SELECT m FROM MotivosLocalizaciones m WHERE m.secuencia = :secuencia"),
    @NamedQuery(name = "MotivosLocalizaciones.findByCodigo", query = "SELECT m FROM MotivosLocalizaciones m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "MotivosLocalizaciones.findByDescripcion", query = "SELECT m FROM MotivosLocalizaciones m WHERE m.descripcion = :descripcion")})
public class MotivosLocalizaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motivo")
    private Collection<VigenciasLocalizaciones> vigenciaslocalizacionesCollection;

    public MotivosLocalizaciones() {
    }

    public MotivosLocalizaciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public MotivosLocalizaciones(BigInteger secuencia, Short codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    @XmlTransient
    public Collection<VigenciasLocalizaciones> getVigenciaslocalizacionesCollection() {
        return vigenciaslocalizacionesCollection;
    }

    public void setVigenciaslocalizacionesCollection(Collection<VigenciasLocalizaciones> vigenciaslocalizacionesCollection) {
        this.vigenciaslocalizacionesCollection = vigenciaslocalizacionesCollection;
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
        if (!(object instanceof MotivosLocalizaciones)) {
            return false;
        }
        MotivosLocalizaciones other = (MotivosLocalizaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivoslocalizaciones[ secuencia=" + secuencia + " ]";
    }

}
