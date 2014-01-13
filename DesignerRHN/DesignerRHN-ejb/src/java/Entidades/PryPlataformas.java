/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "PRY_PLATAFORMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PryPlataformas.findAll", query = "SELECT p FROM PryPlataformas p")})
public class PryPlataformas implements Serializable {

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
    @Column(name = "OBSERVACION")
    private String observacion;
    @OneToMany(mappedBy = "pryPlataforma")
    private Collection<Proyectos> proyectosCollection;

    public PryPlataformas() {
    }

    public PryPlataformas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public PryPlataformas(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
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
        if (descripcion == null) {
            descripcion = (" ");
            return descripcion;
        }

        return descripcion.toUpperCase();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public String getObservacion() {
        if (observacion == null) {
            observacion = (" ");
            return observacion;
        }
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.toUpperCase();
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
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
        if (!(object instanceof PryPlataformas)) {
            return false;
        }
        PryPlataformas other = (PryPlataformas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PryPlataformas[ secuencia=" + secuencia + " ]";
    }

}
