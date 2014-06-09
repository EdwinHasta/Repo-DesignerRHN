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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CLASESAUSENTISMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clasesausentismos.findAll", query = "SELECT c FROM Clasesausentismos c")})
public class Clasesausentismos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clase")
    private Collection<Soausentismos> soausentismosCollection;
    @JoinColumn(name = "TIPO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Tiposausentismos tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clase")
    private Collection<Causasausentismos> causasausentismosCollection;

    public Clasesausentismos() {
    }

    public Clasesausentismos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Clasesausentismos(BigInteger secuencia, Integer codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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
        if (descripcion == null) {
            this.descripcion = descripcion;
        } else {
            this.descripcion = descripcion.toUpperCase();
        }
    }

    @XmlTransient
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
    }

    public Tiposausentismos getTipo() {
        return tipo;
    }

    public void setTipo(Tiposausentismos tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Causasausentismos> getCausasausentismosCollection() {
        return causasausentismosCollection;
    }

    public void setCausasausentismosCollection(Collection<Causasausentismos> causasausentismosCollection) {
        this.causasausentismosCollection = causasausentismosCollection;
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
        if (!(object instanceof Clasesausentismos)) {
            return false;
        }
        Clasesausentismos other = (Clasesausentismos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Clasesausentismos[ secuencia=" + secuencia + " ]";
    }

}
