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
@Table(name = "EVALCOMPETENCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalcompetencias.findAll", query = "SELECT e FROM Evalcompetencias e"),
    @NamedQuery(name = "Evalcompetencias.findBySecuencia", query = "SELECT e FROM Evalcompetencias e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "Evalcompetencias.findByCodigo", query = "SELECT e FROM Evalcompetencias e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Evalcompetencias.findByDescripcion", query = "SELECT e FROM Evalcompetencias e WHERE e.descripcion = :descripcion")})
public class Evalcompetencias implements Serializable {
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
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalcompetencia")
    private Collection<Competenciascargos> competenciascargosCollection;

    public Evalcompetencias() {
    }

    public Evalcompetencias(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Evalcompetencias(BigDecimal secuencia, BigInteger codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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

    @XmlTransient
    public Collection<Competenciascargos> getCompetenciascargosCollection() {
        return competenciascargosCollection;
    }

    public void setCompetenciascargosCollection(Collection<Competenciascargos> competenciascargosCollection) {
        this.competenciascargosCollection = competenciascargosCollection;
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
        if (!(object instanceof Evalcompetencias)) {
            return false;
        }
        Evalcompetencias other = (Evalcompetencias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Evalcompetencias[ secuencia=" + secuencia + " ]";
    }
    
}
