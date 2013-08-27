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
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSAUSENTISMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposausentismos.findAll", query = "SELECT t FROM Tiposausentismos t")})
public class Tiposausentismos implements Serializable {
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
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Collection<Soausentismos> soausentismosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Collection<Clasesausentismos> clasesausentismosCollection;

    public Tiposausentismos() {
    }

    public Tiposausentismos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Tiposausentismos(BigDecimal secuencia, short codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
    }

    @XmlTransient
    public Collection<Clasesausentismos> getClasesausentismosCollection() {
        return clasesausentismosCollection;
    }

    public void setClasesausentismosCollection(Collection<Clasesausentismos> clasesausentismosCollection) {
        this.clasesausentismosCollection = clasesausentismosCollection;
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
        if (!(object instanceof Tiposausentismos)) {
            return false;
        }
        Tiposausentismos other = (Tiposausentismos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposausentismos[ secuencia=" + secuencia + " ]";
    }
    
}
