/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "EVALENFOQUES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalenfoques.findAll", query = "SELECT e FROM Evalenfoques e"),
    @NamedQuery(name = "Evalenfoques.findBySecuencia", query = "SELECT e FROM Evalenfoques e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "Evalenfoques.findByCodigo", query = "SELECT e FROM Evalenfoques e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Evalenfoques.findByDescripcion", query = "SELECT e FROM Evalenfoques e WHERE e.descripcion = :descripcion")})
public class Evalenfoques implements Serializable {
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
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfoque")
    private Collection<Evalconvocatorias> evalconvocatoriasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalenfoque")
    private Collection<Evalplanillas> evalplanillasCollection;

    public Evalenfoques() {
    }

    public Evalenfoques(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Evalenfoques(BigDecimal secuencia, short codigo, String descripcion) {
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

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Evalconvocatorias> getEvalconvocatoriasCollection() {
        return evalconvocatoriasCollection;
    }

    public void setEvalconvocatoriasCollection(Collection<Evalconvocatorias> evalconvocatoriasCollection) {
        this.evalconvocatoriasCollection = evalconvocatoriasCollection;
    }

    @XmlTransient
    public Collection<Evalplanillas> getEvalplanillasCollection() {
        return evalplanillasCollection;
    }

    public void setEvalplanillasCollection(Collection<Evalplanillas> evalplanillasCollection) {
        this.evalplanillasCollection = evalplanillasCollection;
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
        if (!(object instanceof Evalenfoques)) {
            return false;
        }
        Evalenfoques other = (Evalenfoques) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Evalenfoques[ secuencia=" + secuencia + " ]";
    }
    
}
