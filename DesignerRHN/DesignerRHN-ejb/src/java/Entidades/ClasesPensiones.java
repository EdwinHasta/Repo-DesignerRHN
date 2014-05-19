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
 * @author user
 */
@Entity
@Table(name = "CLASESPENSIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClasesPensiones.findAll", query = "SELECT c FROM ClasesPensiones c"),
    @NamedQuery(name = "ClasesPensiones.findBySecuencia", query = "SELECT c FROM ClasesPensiones c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "ClasesPensiones.findByCodigo", query = "SELECT c FROM ClasesPensiones c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ClasesPensiones.findByDescripcion", query = "SELECT c FROM ClasesPensiones c WHERE c.descripcion = :descripcion")})
public class ClasesPensiones implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clase")
    private Collection<Pensionados> pensionadosCollection;

    public ClasesPensiones() {
    }

    public ClasesPensiones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ClasesPensiones(BigInteger secuencia, Integer codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        System.out.println("fasasdsddhkasjkasdasdasd");
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
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    @XmlTransient
    public Collection<Pensionados> getPensionadosCollection() {
        return pensionadosCollection;
    }

    public void setPensionadosCollection(Collection<Pensionados> pensionadosCollection) {
        this.pensionadosCollection = pensionadosCollection;
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
        if (!(object instanceof ClasesPensiones)) {
            return false;
        }
        ClasesPensiones other = (ClasesPensiones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Clasespensiones[ secuencia=" + secuencia + " ]";
    }

}
