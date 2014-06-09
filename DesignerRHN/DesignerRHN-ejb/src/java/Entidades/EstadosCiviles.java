/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ESTADOSCIVILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosCiviles.findAll", query = "SELECT e FROM EstadosCiviles e")})
public class EstadosCiviles implements Serializable {

    @OneToMany(mappedBy = "estadocivil")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadocivil")
    private Collection<VigenciasEstadosCiviles> vigenciasEstadosCivilesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Integer codigo;
    @OneToMany(mappedBy = "estadocivil")
    private Collection<HVHojasDeVida> hVHojasDeVidaCollection;

    public EstadosCiviles() {
    }

    public EstadosCiviles(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EstadosCiviles(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<HVHojasDeVida> getHVHojasDeVidaCollection() {
        return hVHojasDeVidaCollection;
    }

    public void setHVHojasDeVidaCollection(Collection<HVHojasDeVida> hVHojasDeVidaCollection) {
        this.hVHojasDeVidaCollection = hVHojasDeVidaCollection;
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
        if (!(object instanceof EstadosCiviles)) {
            return false;
        }
        EstadosCiviles other = (EstadosCiviles) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EstadosCiviles[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasEstadosCiviles> getVigenciasEstadosCivilesCollection() {
        return vigenciasEstadosCivilesCollection;
    }

    public void setVigenciasEstadosCivilesCollection(Collection<VigenciasEstadosCiviles> vigenciasEstadosCivilesCollection) {
        this.vigenciasEstadosCivilesCollection = vigenciasEstadosCivilesCollection;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }

}
