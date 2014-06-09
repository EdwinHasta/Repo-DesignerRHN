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
@Table(name = "ELEMENTOSCAUSASACCIDENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementosCausasAccidentes.findAll", query = "SELECT e FROM ElementosCausasAccidentes e")})
public class ElementosCausasAccidentes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causa")
    private Collection<Soaccidentes> soaccidentesCollection;

    public ElementosCausasAccidentes() {
    }

    public ElementosCausasAccidentes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ElementosCausasAccidentes(BigInteger secuencia, Integer codigo, String descripcion) {
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null) {
            this.descripcion = descripcion.toUpperCase();
        } else {
            this.descripcion = descripcion;
        }
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection() {
        return soaccidentesCollection;
    }

    public void setSoaccidentesCollection(Collection<Soaccidentes> soaccidentesCollection) {
        this.soaccidentesCollection = soaccidentesCollection;
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
        if (!(object instanceof ElementosCausasAccidentes)) {
            return false;
        }
        ElementosCausasAccidentes other = (ElementosCausasAccidentes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Elementoscausasaccidentes[ secuencia=" + secuencia + " ]";
    }

}
