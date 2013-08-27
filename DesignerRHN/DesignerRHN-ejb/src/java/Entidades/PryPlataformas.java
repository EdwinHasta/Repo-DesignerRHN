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
@Table(name = "PRY_PLATAFORMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PryPlataformas.findAll", query = "SELECT p FROM PryPlataformas p"),
    @NamedQuery(name = "PryPlataformas.findBySecuencia", query = "SELECT p FROM PryPlataformas p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "PryPlataformas.findByCodigo", query = "SELECT p FROM PryPlataformas p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "PryPlataformas.findByDescripcion", query = "SELECT p FROM PryPlataformas p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PryPlataformas.findByObservacion", query = "SELECT p FROM PryPlataformas p WHERE p.observacion = :observacion")})
public class PryPlataformas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "OBSERVACION")
    private String observacion;
    @OneToMany(mappedBy = "pryPlataforma")
    private Collection<Proyectos> proyectosCollection;

    public PryPlataformas() {
    }

    public PryPlataformas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public PryPlataformas(BigDecimal secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
